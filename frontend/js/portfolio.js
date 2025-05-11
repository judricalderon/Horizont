import { tradingApi } from '../api/tradingApi';
import { formatCurrency, formatPercentage } from '../utils/helpers';

export function initializePortfolio() {
    const portfolioPage = document.querySelector('.portfolio-page');
    if (!portfolioPage) return;

    // Initialize components
    initializePortfolioSummary();
    initializeHoldingsTable();
    initializePerformanceChart();
    initializeTransactionHistory();
}

// Portfolio Summary Component
async function initializePortfolioSummary() {
    const summaryContainer = document.querySelector('.portfolio-summary');
    if (!summaryContainer) return;

    try {
        const portfolio = await tradingApi.getPortfolio();
        updatePortfolioSummary(portfolio);
    } catch (error) {
        console.error('Error fetching portfolio summary:', error);
    }

    // Set up auto-refresh
    setInterval(async () => {
        try {
            const portfolio = await tradingApi.getPortfolio();
            updatePortfolioSummary(portfolio);
        } catch (error) {
            console.error('Error refreshing portfolio summary:', error);
        }
    }, 30000); // Refresh every 30 seconds
}

function updatePortfolioSummary(portfolio) {
    const summaryContainer = document.querySelector('.portfolio-summary');
    if (!summaryContainer) return;

    summaryContainer.innerHTML = `
        <div class="summary-card">
            <h3>Valor Total del Portafolio</h3>
            <p class="total-value">${formatCurrency(portfolio.totalValue)}</p>
            <p class="change ${portfolio.dailyChange >= 0 ? 'positive' : 'negative'}">
                ${formatPercentage(portfolio.dailyChange)} hoy
            </p>
        </div>
        <div class="summary-card">
            <h3>Ganancias/Pérdidas Totales</h3>
            <p class="total-pnl ${portfolio.totalPnL >= 0 ? 'positive' : 'negative'}">
                ${formatCurrency(portfolio.totalPnL)}
            </p>
            <p class="pnl-percentage ${portfolio.totalPnLPercentage >= 0 ? 'positive' : 'negative'}">
                ${formatPercentage(portfolio.totalPnLPercentage)}
            </p>
        </div>
        <div class="summary-card">
            <h3>Efectivo Disponible</h3>
            <p class="cash-balance">${formatCurrency(portfolio.cashBalance)}</p>
            <p class="buying-power">Poder de compra: ${formatCurrency(portfolio.buyingPower)}</p>
        </div>
    `;
}

// Holdings Table Component
async function initializeHoldingsTable() {
    const holdingsTable = document.querySelector('.holdings-table');
    if (!holdingsTable) return;

    await updateHoldingsTable();
}

async function updateHoldingsTable() {
    const holdingsTable = document.querySelector('.holdings-table');
    if (!holdingsTable) return;

    try {
        const portfolio = await tradingApi.getPortfolio();
        holdingsTable.innerHTML = `
            <div class="table-header">
                <div>Símbolo</div>
                <div>Acciones</div>
                <div>Precio Promedio</div>
                <div>Precio Actual</div>
                <div>Valor Total</div>
                <div>P/L</div>
                <div>% P/L</div>
            </div>
            ${portfolio.holdings.map(holding => `
                <div class="table-row">
                    <div>${holding.symbol}</div>
                    <div>${holding.quantity}</div>
                    <div>${formatCurrency(holding.averagePrice)}</div>
                    <div>${formatCurrency(holding.currentPrice)}</div>
                    <div>${formatCurrency(holding.totalValue)}</div>
                    <div class="${holding.pnl >= 0 ? 'positive' : 'negative'}">
                        ${formatCurrency(holding.pnl)}
                    </div>
                    <div class="${holding.pnlPercentage >= 0 ? 'positive' : 'negative'}">
                        ${formatPercentage(holding.pnlPercentage)}
                    </div>
                </div>
            `).join('')}
        `;
    } catch (error) {
        console.error('Error updating holdings table:', error);
    }
}

// Performance Chart Component
function initializePerformanceChart() {
    const chartContainer = document.querySelector('.performance-chart');
    if (!chartContainer) return;

    // Initialize chart with empty data
    const chartData = {
        labels: [],
        datasets: [{
            label: 'Valor del Portafolio',
            data: [],
            borderColor: '#8A1931',
            tension: 0.1
        }]
    };

    // Create chart
    const ctx = chartContainer.getContext('2d');
    const chart = new Chart(ctx, {
        type: 'line',
        data: chartData,
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

    // Update chart data
    updatePerformanceChart(chart);
}

async function updatePerformanceChart(chart) {
    try {
        const portfolio = await tradingApi.getPortfolio();
        const performanceData = portfolio.performanceHistory;

        chart.data.labels = performanceData.map(point => 
            new Date(point.date).toLocaleDateString()
        );
        chart.data.datasets[0].data = performanceData.map(point => point.value);
        chart.update();
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
        const portfolio = await tradingApi.getPortfolio();
        transactionHistory.innerHTML = `
            <div class="table-header">
                <div>Fecha</div>
                <div>Tipo</div>
                <div>Símbolo</div>
                <div>Cantidad</div>
                <div>Precio</div>
                <div>Total</div>
            </div>
            ${portfolio.transactions.map(transaction => `
                <div class="table-row">
                    <div>${new Date(transaction.date).toLocaleString()}</div>
                    <div class="${transaction.type.toLowerCase()}">${transaction.type}</div>
                    <div>${transaction.symbol}</div>
                    <div>${transaction.quantity}</div>
                    <div>${formatCurrency(transaction.price)}</div>
                    <div>${formatCurrency(transaction.total)}</div>
                </div>
            `).join('')}
        `;
    } catch (error) {
        console.error('Error updating transaction history:', error);
    }
}

// Export Chart.js if not already available
if (typeof Chart === 'undefined') {
    const script = document.createElement('script');
    script.src = 'https://cdn.jsdelivr.net/npm/chart.js';
    document.head.appendChild(script);
}