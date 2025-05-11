import { tradingApi } from '../api/tradingApi';
import { formatCurrency, formatPercentage, formatDate } from '../utils/helpers';

export function initializeTrading() {
    const tradingPage = document.querySelector('.trading-page');
    if (!tradingPage) return;

    // Initialize components
    initializeSymbolInfo();
    initializePriceChart();
    initializeOrderForm();
    initializeOrderBook();
    initializeTradeHistory();
    initializeMarketDepth();
}

// Symbol Info Component
async function initializeSymbolInfo() {
    const symbolInfo = document.querySelector('.symbol-info');
    if (!symbolInfo) return;

    await updateSymbolInfo();
}

async function updateSymbolInfo() {
    const symbolInfo = document.querySelector('.symbol-info');
    if (!symbolInfo) return;

    try {
        const urlParams = new URLSearchParams(window.location.search);
        const symbol = urlParams.get('symbol') || 'AAPL';
        const info = await tradingApi.getSymbolInfo(symbol);

        symbolInfo.innerHTML = `
            <div class="symbol-header">
                <h2>${info.symbol}</h2>
                <span class="company-name">${info.companyName}</span>
            </div>
            <div class="price-info">
                <div class="current-price">
                    <span class="price">${formatCurrency(info.price)}</span>
                    <span class="change ${info.change >= 0 ? 'positive' : 'negative'}">
                        ${formatPercentage(info.change)}
                    </span>
                </div>
                <div class="price-details">
                    <div class="detail">
                        <span class="label">Apertura</span>
                        <span class="value">${formatCurrency(info.open)}</span>
                    </div>
                    <div class="detail">
                        <span class="label">Máximo</span>
                        <span class="value">${formatCurrency(info.high)}</span>
                    </div>
                    <div class="detail">
                        <span class="label">Mínimo</span>
                        <span class="value">${formatCurrency(info.low)}</span>
                    </div>
                    <div class="detail">
                        <span class="label">Volumen</span>
                        <span class="value">${info.volume.toLocaleString()}</span>
                    </div>
                </div>
            </div>
        `;
    } catch (error) {
        console.error('Error updating symbol info:', error);
    }
}

// Price Chart Component
async function initializePriceChart() {
    const priceChart = document.querySelector('.price-chart');
    if (!priceChart) return;

    await updatePriceChart();
}

async function updatePriceChart() {
    const priceChart = document.querySelector('.price-chart');
    if (!priceChart) return;

    try {
        const urlParams = new URLSearchParams(window.location.search);
        const symbol = urlParams.get('symbol') || 'AAPL';
        const chartData = await tradingApi.getPriceHistory(symbol);

        const ctx = document.getElementById('priceChart');
        if (!ctx) return;

        new Chart(ctx, {
            type: 'candlestick',
            data: {
                datasets: [{
                    label: symbol,
                    data: chartData.map(candle => ({
                        x: new Date(candle.timestamp),
                        o: candle.open,
                        h: candle.high,
                        l: candle.low,
                        c: candle.close
                    }))
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        type: 'time',
                        time: {
                            unit: 'minute'
                        }
                    },
                    y: {
                        ticks: {
                            callback: value => formatCurrency(value)
                        }
                    }
                }
            }
        });
    } catch (error) {
        console.error('Error updating price chart:', error);
    }
}

// Order Form Component
function initializeOrderForm() {
    const orderForm = document.querySelector('.order-form');
    if (!orderForm) return;

    orderForm.addEventListener('submit', handleOrderSubmit);
}

async function handleOrderSubmit(e) {
    e.preventDefault();
    const orderForm = e.currentTarget;
    const formData = new FormData(orderForm);

    try {
        const order = {
            symbol: formData.get('symbol'),
            type: formData.get('orderType'),
            side: formData.get('orderSide'),
            quantity: parseInt(formData.get('quantity')),
            price: parseFloat(formData.get('price')),
            timeInForce: formData.get('timeInForce')
        };

        await tradingApi.placeOrder(order);
        showNotification('Orden ejecutada exitosamente', 'success');
        orderForm.reset();
        updateOrderBook();
        updateTradeHistory();
    } catch (error) {
        console.error('Error placing order:', error);
        showNotification('Error al ejecutar la orden', 'error');
    }
}

// Order Book Component
async function initializeOrderBook() {
    const orderBook = document.querySelector('.order-book');
    if (!orderBook) return;

    await updateOrderBook();
}

async function updateOrderBook() {
    const orderBook = document.querySelector('.order-book');
    if (!orderBook) return;

    try {
        const urlParams = new URLSearchParams(window.location.search);
        const symbol = urlParams.get('symbol') || 'AAPL';
        const book = await tradingApi.getOrderBook(symbol);

        orderBook.innerHTML = `
            <div class="order-book-header">
                <h3>Libro de Órdenes</h3>
                <div class="order-book-controls">
                    <button class="refresh-btn">
                        <span class="material-icons">refresh</span>
                    </button>
                    <select class="depth-select">
                        <option value="10">10 niveles</option>
                        <option value="20">20 niveles</option>
                        <option value="50">50 niveles</option>
                    </select>
                </div>
            </div>
            <div class="order-book-content">
                <div class="asks">
                    ${book.asks.map(ask => `
                        <div class="order-row ask">
                            <div class="price">${formatCurrency(ask.price)}</div>
                            <div class="quantity">${ask.quantity}</div>
                            <div class="total">${formatCurrency(ask.price * ask.quantity)}</div>
                        </div>
                    `).join('')}
                </div>
                <div class="spread">
                    <span class="spread-label">Spread</span>
                    <span class="spread-value">${formatCurrency(book.spread)}</span>
                </div>
                <div class="bids">
                    ${book.bids.map(bid => `
                        <div class="order-row bid">
                            <div class="price">${formatCurrency(bid.price)}</div>
                            <div class="quantity">${bid.quantity}</div>
                            <div class="total">${formatCurrency(bid.price * bid.quantity)}</div>
                        </div>
                    `).join('')}
                </div>
            </div>
        `;

        // Add event listeners
        addOrderBookEventListeners();
    } catch (error) {
        console.error('Error updating order book:', error);
    }
}

// Trade History Component
async function initializeTradeHistory() {
    const tradeHistory = document.querySelector('.trade-history');
    if (!tradeHistory) return;

    await updateTradeHistory();
}

async function updateTradeHistory() {
    const tradeHistory = document.querySelector('.trade-history');
    if (!tradeHistory) return;

    try {
        const urlParams = new URLSearchParams(window.location.search);
        const symbol = urlParams.get('symbol') || 'AAPL';
        const trades = await tradingApi.getTradeHistory(symbol);

        tradeHistory.innerHTML = `
            <div class="trade-history-header">
                <h3>Historial de Operaciones</h3>
                <div class="trade-history-controls">
                    <button class="refresh-btn">
                        <span class="material-icons">refresh</span>
                    </button>
                    <select class="timeframe-select">
                        <option value="1m">1 min</option>
                        <option value="5m">5 min</option>
                        <option value="15m">15 min</option>
                        <option value="1h">1 hora</option>
                    </select>
                </div>
            </div>
            <div class="trade-history-content">
                ${trades.map(trade => `
                    <div class="trade-row">
                        <div class="time">${formatDate(trade.timestamp)}</div>
                        <div class="price ${trade.side === 'buy' ? 'positive' : 'negative'}">
                            ${formatCurrency(trade.price)}
                        </div>
                        <div class="quantity">${trade.quantity}</div>
                        <div class="total">${formatCurrency(trade.price * trade.quantity)}</div>
                    </div>
                `).join('')}
            </div>
        `;

        // Add event listeners
        addTradeHistoryEventListeners();
    } catch (error) {
        console.error('Error updating trade history:', error);
    }
}

// Market Depth Component
async function initializeMarketDepth() {
    const marketDepth = document.querySelector('.market-depth');
    if (!marketDepth) return;

    await updateMarketDepth();
}

async function updateMarketDepth() {
    const marketDepth = document.querySelector('.market-depth');
    if (!marketDepth) return;

    try {
        const urlParams = new URLSearchParams(window.location.search);
        const symbol = urlParams.get('symbol') || 'AAPL';
        const depth = await tradingApi.getMarketDepth(symbol);

        const ctx = document.getElementById('depthChart');
        if (!ctx) return;

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: depth.asks.map(ask => formatCurrency(ask.price)),
                datasets: [
                    {
                        label: 'Ventas',
                        data: depth.asks.map(ask => ask.quantity),
                        borderColor: '#ff4444',
                        fill: false
                    },
                    {
                        label: 'Compras',
                        data: depth.bids.map(bid => bid.quantity),
                        borderColor: '#00C851',
                        fill: false
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    } catch (error) {
        console.error('Error updating market depth:', error);
    }
}

// Event Listeners
function addOrderBookEventListeners() {
    const orderBook = document.querySelector('.order-book');
    if (!orderBook) return;

    // Refresh button
    const refreshBtn = orderBook.querySelector('.refresh-btn');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', updateOrderBook);
    }

    // Depth select
    const depthSelect = orderBook.querySelector('.depth-select');
    if (depthSelect) {
        depthSelect.addEventListener('change', updateOrderBook);
    }
}

function addTradeHistoryEventListeners() {
    const tradeHistory = document.querySelector('.trade-history');
    if (!tradeHistory) return;

    // Refresh button
    const refreshBtn = tradeHistory.querySelector('.refresh-btn');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', updateTradeHistory);
    }

    // Timeframe select
    const timeframeSelect = tradeHistory.querySelector('.timeframe-select');
    if (timeframeSelect) {
        timeframeSelect.addEventListener('change', updateTradeHistory);
    }
}

// Helper Functions
function showNotification(message, type) {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Auto-refresh components
setInterval(() => {
    updateSymbolInfo();
    updatePriceChart();
    updateOrderBook();
    updateTradeHistory();
    updateMarketDepth();
}, 5000); // Refresh every 5 seconds 