import { tradingApi } from '../api/tradingApi';
import { formatCurrency, formatPercentage, formatDate } from '../utils/helpers';

export function initializePortfolio() {
    const portfolioPage = document.querySelector('.portfolio-page');
    if (!portfolioPage) return;

    // Initialize components
    initializePortfolioSummary();
    initializeHoldings();
    initializePerformanceChart();
    initializeTransactionHistory();
    initializeAssetAllocation();
}

// Portfolio Summary Component
async function initializePortfolioSummary() {
    const portfolioSummary = document.querySelector('.portfolio-summary');
    if (!portfolioSummary) return;

    await updatePortfolioSummary();
}

async function updatePortfolioSummary() {
    const portfolioSummary = document.querySelector('.portfolio-summary');
    if (!portfolioSummary) return;

    try {
        const portfolio = await tradingApi.getPortfolio();
        portfolioSummary.innerHTML = `
            <div class="summary-stats">
                <div class="stat-card">
                    <h3>Valor Total</h3>
                    <p class="value">${formatCurrency(portfolio.totalValue)}</p>
                    <p class="change ${portfolio.dailyChange >= 0 ? 'positive' : 'negative'}">
                        ${formatPercentage(portfolio.dailyChange)} hoy
                    </p>
                </div>
                <div class="stat-card">
                    <h3>Ganancias/Pérdidas</h3>
                    <p class="value ${portfolio.totalPnL >= 0 ? 'positive' : 'negative'}">
                        ${formatCurrency(portfolio.totalPnL)}
                    </p>
                    <p class="percentage ${portfolio.totalPnLPercentage >= 0 ? 'positive' : 'negative'}">
                        ${formatPercentage(portfolio.totalPnLPercentage)}
                    </p>
                </div>
                <div class="stat-card">
                    <h3>Efectivo</h3>
                    <p class="value">${formatCurrency(portfolio.cashBalance)}</p>
                    <p class="buying-power">Poder de compra: ${formatCurrency(portfolio.buyingPower)}</p>
                </div>
                <div class="stat-card">
                    <h3>Margen</h3>
                    <p class="value">${formatCurrency(portfolio.marginBalance)}</p>
                    <p class="margin-ratio">Ratio: ${portfolio.marginRatio.toFixed(2)}</p>
                </div>
            </div>
        `;
    } catch (error) {
        console.error('Error updating portfolio summary:', error);
    }
}

// Holdings Component
async function initializeHoldings() {
    const holdings = document.querySelector('.holdings');
    if (!holdings) return;

    await updateHoldings();
}

async function updateHoldings() {
    const holdings = document.querySelector('.holdings');
    if (!holdings) return;

    try {
        const portfolio = await tradingApi.getPortfolio();
        holdings.innerHTML = `
            <div class="holdings-header">
                <h3>Posiciones Actuales</h3>
                <div class="holdings-controls">
                    <button class="refresh-btn">
                        <span class="material-icons">refresh</span>
                    </button>
                    <select class="sort-select">
                        <option value="value">Por Valor</option>
                        <option value="pnl">Por G/P</option>
                        <option value="change">Por Cambio</option>
                    </select>
                </div>
            </div>
            <div class="holdings-table">
                <div class="table-header">
                    <div>Símbolo</div>
                    <div>Posición</div>
                    <div>Precio Promedio</div>
                    <div>Precio Actual</div>
                    <div>Valor</div>
                    <div>G/P</div>
                    <div>Cambio</div>
                    <div>Acciones</div>
                </div>
                ${portfolio.holdings.map(holding => `
                    <div class="table-row">
                        <div class="symbol-info">
                            <span class="symbol">${holding.symbol}</span>
                            <span class="company-name">${holding.companyName}</span>
                        </div>
                        <div class="position">
                            <span class="quantity">${holding.quantity}</span>
                            <span class="type">${holding.type}</span>
                        </div>
                        <div class="avg-price">${formatCurrency(holding.averagePrice)}</div>
                        <div class="current-price">${formatCurrency(holding.currentPrice)}</div>
                        <div class="value">${formatCurrency(holding.marketValue)}</div>
                        <div class="pnl ${holding.pnl >= 0 ? 'positive' : 'negative'}">
                            ${formatCurrency(holding.pnl)}
                        </div>
                        <div class="change ${holding.change >= 0 ? 'positive' : 'negative'}">
                            ${formatPercentage(holding.change)}
                        </div>
                        <div class="actions">
                            <button class="trade-btn" data-symbol="${holding.symbol}">
                                <span class="material-icons">swap_horiz</span>
                            </button>
                            <button class="close-btn" data-symbol="${holding.symbol}">
                                <span class="material-icons">close</span>
                            </button>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;

        // Add event listeners
        addHoldingsEventListeners();
    } catch (error) {
        console.error('Error updating holdings:', error);
    }
}

// Performance Chart Component
async function initializePerformanceChart() {
    const performanceChart = document.querySelector('.performance-chart');
    if (!performanceChart) return;

    await updatePerformanceChart();
}

async function updatePerformanceChart() {
    const performanceChart = document.querySelector('.performance-chart');
    if (!performanceChart) return;

    try {
        const portfolio = await tradingApi.getPortfolio();
        const ctx = document.getElementById('performanceChart');
        if (!ctx) return;

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: portfolio.performanceHistory.map(point => formatDate(point.date)),
                datasets: [{
                    label: 'Valor del Portafolio',
                    data: portfolio.performanceHistory.map(point => point.value),
                    borderColor: '#8A1931',
                    tension: 0.1,
                    fill: false
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: false,
                        ticks: {
                            callback: value => formatCurrency(value)
                        }
                    }
                }
            }
        });
    } catch (error) {
        console.error('Error updating performance chart:', error);
    }
}

// Transaction History Component
async function initializeTransactionHistory() {
    const transactionHistory = document.querySelector('.transaction-history');
    if (!transactionHistory) return;

    await updateTransactionHistory();
}

async function updateTransactionHistory() {
    const transactionHistory = document.querySelector('.transaction-history');
    if (!transactionHistory) return;

    try {
        const transactions = await tradingApi.getTransactionHistory();
        transactionHistory.innerHTML = `
            <div class="transaction-history-header">
                <h3>Historial de Transacciones</h3>
                <div class="transaction-history-controls">
                    <button class="refresh-btn">
                        <span class="material-icons">refresh</span>
                    </button>
                    <select class="timeframe-select">
                        <option value="1d">Último día</option>
                        <option value="1w">Última semana</option>
                        <option value="1m">Último mes</option>
                        <option value="3m">Últimos 3 meses</option>
                        <option value="1y">Último año</option>
                    </select>
                </div>
            </div>
            <div class="transaction-history-content">
                ${transactions.map(transaction => `
                    <div class="transaction-row">
                        <div class="date">${formatDate(transaction.date)}</div>
                        <div class="type ${transaction.type}">
                            <span class="material-icons">${getTransactionIcon(transaction.type)}</span>
                            ${transaction.type}
                        </div>
                        <div class="symbol">${transaction.symbol}</div>
                        <div class="quantity">${transaction.quantity}</div>
                        <div class="price">${formatCurrency(transaction.price)}</div>
                        <div class="total">${formatCurrency(transaction.total)}</div>
                        <div class="status ${transaction.status}">${transaction.status}</div>
                    </div>
                `).join('')}
            </div>
        `;

        // Add event listeners
        addTransactionHistoryEventListeners();
    } catch (error) {
        console.error('Error updating transaction history:', error);
    }
}

// Asset Allocation Component
async function initializeAssetAllocation() {
    const assetAllocation = document.querySelector('.asset-allocation');
    if (!assetAllocation) return;

    await updateAssetAllocation();
}

async function updateAssetAllocation() {
    const assetAllocation = document.querySelector('.asset-allocation');
    if (!assetAllocation) return;

    try {
        const portfolio = await tradingApi.getPortfolio();
        const ctx = document.getElementById('allocationChart');
        if (!ctx) return;

        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: portfolio.assetAllocation.map(asset => asset.sector),
                datasets: [{
                    data: portfolio.assetAllocation.map(asset => asset.value),
                    backgroundColor: [
                        '#8A1931',
                        '#00C851',
                        '#FF8800',
                        '#33B5E5',
                        '#FF4444',
                        '#2BBBAD',
                        '#4285F4',
                        '#AA66CC'
                    ]
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'right'
                    }
                }
            }
        });
    } catch (error) {
        console.error('Error updating asset allocation:', error);
    }
}

// Event Listeners
function addHoldingsEventListeners() {
    const holdings = document.querySelector('.holdings');
    if (!holdings) return;

    // Refresh button
    const refreshBtn = holdings.querySelector('.refresh-btn');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', updateHoldings);
    }

    // Sort select
    const sortSelect = holdings.querySelector('.sort-select');
    if (sortSelect) {
        sortSelect.addEventListener('change', updateHoldings);
    }

    // Trade buttons
    holdings.querySelectorAll('.trade-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const symbol = e.currentTarget.dataset.symbol;
            window.location.href = `/trading.html?symbol=${symbol}`;
        });
    });

    // Close buttons
    holdings.querySelectorAll('.close-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const symbol = e.currentTarget.dataset.symbol;
            try {
                await tradingApi.closePosition(symbol);
                updateHoldings();
                updatePortfolioSummary();
                showNotification('Posición cerrada exitosamente', 'success');
            } catch (error) {
                console.error('Error closing position:', error);
                showNotification('Error al cerrar la posición', 'error');
            }
        });
    });
}

function addTransactionHistoryEventListeners() {
    const transactionHistory = document.querySelector('.transaction-history');
    if (!transactionHistory) return;

    // Refresh button
    const refreshBtn = transactionHistory.querySelector('.refresh-btn');
    if (refreshBtn) {
        refreshBtn.addEventListener('click', updateTransactionHistory);
    }

    // Timeframe select
    const timeframeSelect = transactionHistory.querySelector('.timeframe-select');
    if (timeframeSelect) {
        timeframeSelect.addEventListener('change', updateTransactionHistory);
    }
}

// Helper Functions
function getTransactionIcon(type) {
    const icons = {
        'buy': 'shopping_cart',
        'sell': 'store',
        'dividend': 'account_balance',
        'split': 'call_split',
        'default': 'info'
    };
    return icons[type] || icons.default;
}

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
    updatePortfolioSummary();
    updateHoldings();
    updatePerformanceChart();
    updateTransactionHistory();
    updateAssetAllocation();
}, 30000); // Refresh every 30 seconds 