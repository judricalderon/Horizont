import { tradingApi } from './tradingApi.js';
import { formatCurrency, formatPercentage, debounce } from './helpers.js';



export function initializeTrading() {
    const tradingPage = document.querySelector('.trading-page');
    if (!tradingPage) return;

    // Initialize components
    initializeMarketData();
    initializeOrderForm();
    initializePositionsTable();
    initializeOrderHistory();
}

// Market Data Component
async function initializeMarketData() {
    console.log("✅ Inicializando mercado");
    const marketDataContainer = document.querySelector('.market-data');
    if (!marketDataContainer) return;

    // Get initial market data
    try {
        const symbols = await tradingApi.getSymbols();
        console.log("📦 Datos recibidos desde el backend:", symbols);
        updateMarketData(symbols);
    } catch (error) {
        console.error('Error fetching market data:', error);
    }

    // Set up auto-refresh
    setInterval(async () => {
        try {
            const symbols = await tradingApi.getSymbols();
            updateMarketData(symbols);
        } catch (error) {
            console.error('Error refreshing market data:', error);
        }
    }, 5000); // Refresh every 5 seconds
}

function updateMarketData(symbols) {
    const marketDataContainer = document.querySelector('.market-data');
    if (!marketDataContainer) return;

    marketDataContainer.innerHTML = symbols.map(symbol => `
        <div class="market-item">
            <h3>${symbol.symbol}</h3>
            <p class="price">${formatCurrency(symbol.price)}</p>
            <p class="change ${symbol.change >= 0 ? 'positive' : 'negative'}">
                ${formatPercentage(symbol.change)}
            </p>
        </div>
    `).join('');
}

// Order Form Component
function initializeOrderForm() {
    const orderForm = document.querySelector('.order-form');
    if (!orderForm) return;

    // Handle form submission
    orderForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const formData = new FormData(orderForm);
        const orderData = {
            symbol: formData.get('symbol'),
            type: formData.get('orderType'),
            quantity: parseInt(formData.get('quantity')),
            price: parseFloat(formData.get('price')),
            side: formData.get('side')
        };

        try {
            const response = await tradingApi.placeOrder(orderData);
            showNotification('Orden colocada exitosamente', 'success');
            orderForm.reset();
            updatePositionsTable();
            updateOrderHistory();
        } catch (error) {
            showNotification(error.message, 'error');
        }
    });

    // Handle symbol search
    const symbolInput = orderForm.querySelector('input[name="symbol"]');
    if (symbolInput) {
        symbolInput.addEventListener('input', debounce(async (e) => {
            const searchTerm = e.target.value.toUpperCase();
            if (searchTerm.length >= 2) {
                try {
                    const symbols = await tradingApi.getSymbols({ search: searchTerm });
                    updateSymbolSuggestions(symbols);
                } catch (error) {
                    console.error('Error searching symbols:', error);
                }
            }
        }, 300));
    }
}

function updateSymbolSuggestions(symbols) {
    const suggestionsContainer = document.querySelector('.symbol-suggestions');
    if (!suggestionsContainer) return;

    suggestionsContainer.innerHTML = symbols.map(symbol => `
        <div class="suggestion-item" data-symbol="${symbol.symbol}">
            ${symbol.symbol} - ${formatCurrency(symbol.price)}
        </div>
    `).join('');

    // Add click handlers
    suggestionsContainer.querySelectorAll('.suggestion-item').forEach(item => {
        item.addEventListener('click', () => {
            const symbolInput = document.querySelector('input[name="symbol"]');
            if (symbolInput) {
                symbolInput.value = item.dataset.symbol;
                suggestionsContainer.innerHTML = '';
            }
        });
    });
}

// Positions Table Component
async function initializePositionsTable() {
    const positionsTable = document.querySelector('.positions-table');
    if (!positionsTable) return;

    await updatePositionsTable();
}

async function updatePositionsTable() {
    const positionsTable = document.querySelector('.positions-table');
    if (!positionsTable) return;

    try {
        const positions = await tradingApi.getPositions();
        positionsTable.innerHTML = `
            <div class="table-header">
                <div>Símbolo</div>
                <div>Cantidad</div>
                <div>Precio Promedio</div>
                <div>Valor Total</div>
                <div>P/L</div>
            </div>
            ${positions.map(position => `
                <div class="table-row">
                    <div>${position.symbol}</div>
                    <div>${position.quantity}</div>
                    <div>${formatCurrency(position.averagePrice)}</div>
                    <div>${formatCurrency(position.totalValue)}</div>
                    <div class="${position.pnl >= 0 ? 'positive' : 'negative'}">
                        ${formatCurrency(position.pnl)}
                    </div>
                </div>
            `).join('')}
        `;
    } catch (error) {
        console.error('Error updating positions:', error);
    }
}

// Order History Component
async function initializeOrderHistory() {
    const orderHistory = document.querySelector('.order-history');
    if (!orderHistory) return;

    await updateOrderHistory();
}

async function updateOrderHistory() {
    const orderHistory = document.querySelector('.order-history');
    if (!orderHistory) return;

    try {
        const orders = await tradingApi.getOrderHistory();
        orderHistory.innerHTML = `
            <div class="table-header">
                <div>Fecha</div>
                <div>Símbolo</div>
                <div>Tipo</div>
                <div>Cantidad</div>
                <div>Precio</div>
                <div>Estado</div>
            </div>
            ${orders.map(order => `
                <div class="table-row">
                    <div>${new Date(order.date).toLocaleString()}</div>
                    <div>${order.symbol}</div>
                    <div>${order.type}</div>
                    <div>${order.quantity}</div>
                    <div>${formatCurrency(order.price)}</div>
                    <div class="status ${order.status.toLowerCase()}">${order.status}</div>
                </div>
            `).join('')}
        `;
    } catch (error) {
        console.error('Error updating order history:', error);
    }
}

// Notification Component
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.classList.add('show');
    }, 100);

    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 3000);
} 