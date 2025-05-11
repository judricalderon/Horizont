import { tradingApi } from '../api/tradingApi';
import { formatCurrency, formatPercentage, formatDate } from '../utils/helpers';

export function initializeDashboard() {
    const dashboardPage = document.querySelector('.dashboard-page');
    if (!dashboardPage) return;

    // Initialize components
    initializeMarketOverview();
    initializePortfolioSummary();
    initializeWatchlist();
    initializeRecentActivity();
    initializeMarketNews();
}

// Market Overview Component
async function initializeMarketOverview() {
    const marketOverview = document.querySelector('.market-overview');
    if (!marketOverview) return;

    await updateMarketOverview();
}

async function updateMarketOverview() {
    const marketOverview = document.querySelector('.market-overview');
    if (!marketOverview) return;

    try {
        const marketData = await tradingApi.getMarketOverview();
        marketOverview.innerHTML = `
            <div class="market-indices">
                ${marketData.indices.map(index => `
                    <div class="index-card">
                        <h3>${index.name}</h3>
                        <p class="value">${formatCurrency(index.value)}</p>
                        <p class="change ${index.change >= 0 ? 'positive' : 'negative'}">
                            ${formatPercentage(index.change)}
                        </p>
                    </div>
                `).join('')}
            </div>
            <div class="market-trends">
                <h3>Tendencias del Mercado</h3>
                <div class="trends-grid">
                    ${marketData.trends.map(trend => `
                        <div class="trend-item">
                            <span class="material-icons">${getTrendIcon(trend.type)}</span>
                            <div class="trend-info">
                                <h4>${trend.title}</h4>
                                <p>${trend.description}</p>
                            </div>
                        </div>
                    `).join('')}
                </div>
            </div>
        `;
    } catch (error) {
        console.error('Error updating market overview:', error);
    }
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
            <div class="portfolio-stats">
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
            </div>
            <div class="portfolio-chart">
                <canvas id="portfolioChart"></canvas>
            </div>
        `;

        // Initialize portfolio chart
        initializePortfolioChart(portfolio.performanceHistory);
    } catch (error) {
        console.error('Error updating portfolio summary:', error);
    }
}

// Watchlist Component
async function initializeWatchlist() {
    const watchlist = document.querySelector('.watchlist');
    if (!watchlist) return;

    await updateWatchlist();
}

async function updateWatchlist() {
    const watchlist = document.querySelector('.watchlist');
    if (!watchlist) return;

    try {
        const watchlistData = await tradingApi.getWatchlist();
        watchlist.innerHTML = `
            <div class="section-header">
                <h3>Lista de Seguimiento</h3>
                <button class="add-symbol-btn">
                    <span class="material-icons">add</span>
                    Agregar Símbolo
                </button>
            </div>
            <div class="watchlist-table">
                <div class="table-header">
                    <div>Símbolo</div>
                    <div>Precio</div>
                    <div>Cambio</div>
                    <div>Volumen</div>
                    <div>Acciones</div>
                </div>
                ${watchlistData.map(item => `
                    <div class="table-row">
                        <div class="symbol-info">
                            <span class="symbol">${item.symbol}</span>
                            <span class="company-name">${item.companyName}</span>
                        </div>
                        <div class="price">${formatCurrency(item.price)}</div>
                        <div class="change ${item.change >= 0 ? 'positive' : 'negative'}">
                            ${formatPercentage(item.change)}
                        </div>
                        <div class="volume">${item.volume.toLocaleString()}</div>
                        <div class="actions">
                            <button class="trade-btn" data-symbol="${item.symbol}">
                                <span class="material-icons">swap_horiz</span>
                            </button>
                            <button class="remove-btn" data-symbol="${item.symbol}">
                                <span class="material-icons">remove_circle</span>
                            </button>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;

        // Add event listeners
        addWatchlistEventListeners();
    } catch (error) {
        console.error('Error updating watchlist:', error);
    }
}

// Recent Activity Component
async function initializeRecentActivity() {
    const recentActivity = document.querySelector('.recent-activity');
    if (!recentActivity) return;

    await updateRecentActivity();
}

async function updateRecentActivity() {
    const recentActivity = document.querySelector('.recent-activity');
    if (!recentActivity) return;

    try {
        const activity = await tradingApi.getRecentActivity();
        recentActivity.innerHTML = `
            <h3>Actividad Reciente</h3>
            <div class="activity-list">
                ${activity.map(item => `
                    <div class="activity-item">
                        <span class="activity-icon material-icons">${getActivityIcon(item.type)}</span>
                        <div class="activity-content">
                            <p>${item.description}</p>
                            <span class="activity-time">${formatDate(item.timestamp)}</span>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Error updating recent activity:', error);
    }
}

// Market News Component
async function initializeMarketNews() {
    const marketNews = document.querySelector('.market-news');
    if (!marketNews) return;

    await updateMarketNews();
}

async function updateMarketNews() {
    const marketNews = document.querySelector('.market-news');
    if (!marketNews) return;

    try {
        const news = await tradingApi.getMarketNews();
        marketNews.innerHTML = `
            <h3>Noticias del Mercado</h3>
            <div class="news-grid">
                ${news.map(item => `
                    <div class="news-card">
                        <div class="news-image" style="background-image: url('${item.image}')"></div>
                        <div class="news-content">
                            <h4>${item.title}</h4>
                            <p>${item.summary}</p>
                            <div class="news-meta">
                                <span class="news-source">${item.source}</span>
                                <span class="news-time">${formatDate(item.timestamp)}</span>
                            </div>
                            <a href="${item.url}" target="_blank" class="read-more">Leer más</a>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Error updating market news:', error);
    }
}

// Helper Functions
function getTrendIcon(type) {
    const icons = {
        'bullish': 'trending_up',
        'bearish': 'trending_down',
        'neutral': 'trending_flat',
        'volatile': 'show_chart',
        'default': 'info'
    };
    return icons[type] || icons.default;
}

function getActivityIcon(type) {
    const icons = {
        'order': 'shopping_cart',
        'trade': 'swap_horiz',
        'portfolio': 'account_balance',
        'watchlist': 'star',
        'news': 'article',
        'default': 'info'
    };
    return icons[type] || icons.default;
}

// Chart Initialization
function initializePortfolioChart(performanceData) {
    const ctx = document.getElementById('portfolioChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: performanceData.map(point => formatDate(point.date)),
            datasets: [{
                label: 'Valor del Portafolio',
                data: performanceData.map(point => point.value),
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
}

// Event Listeners
function addWatchlistEventListeners() {
    const watchlist = document.querySelector('.watchlist');
    if (!watchlist) return;

    // Add symbol button
    const addSymbolBtn = watchlist.querySelector('.add-symbol-btn');
    if (addSymbolBtn) {
        addSymbolBtn.addEventListener('click', showAddSymbolModal);
    }

    // Trade buttons
    watchlist.querySelectorAll('.trade-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const symbol = e.currentTarget.dataset.symbol;
            window.location.href = `/trading.html?symbol=${symbol}`;
        });
    });

    // Remove buttons
    watchlist.querySelectorAll('.remove-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const symbol = e.currentTarget.dataset.symbol;
            try {
                await tradingApi.removeFromWatchlist(symbol);
                updateWatchlist();
            } catch (error) {
                console.error('Error removing symbol from watchlist:', error);
            }
        });
    });
}

// Modal Functions
function showAddSymbolModal() {
    // TODO: Implement add symbol modal
}

// Auto-refresh components
setInterval(() => {
    updateMarketOverview();
    updatePortfolioSummary();
    updateWatchlist();
    updateRecentActivity();
    updateMarketNews();
}, 30000); // Refresh every 30 seconds 