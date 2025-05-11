import { tradingApi } from '../api/tradingApi';
import { formatCurrency, formatDate } from '../utils/helpers';

export function initializeAdmin() {
    const adminPage = document.querySelector('.admin-page');
    if (!adminPage) return;

    // Initialize components
    initializeDashboard();
    initializeUserManagement();
    initializeSystemMonitoring();
    initializeReports();
}

// Dashboard Component
async function initializeDashboard() {
    const dashboard = document.querySelector('.admin-dashboard');
    if (!dashboard) return;

    await updateDashboard();
}

async function updateDashboard() {
    const dashboard = document.querySelector('.admin-dashboard');
    if (!dashboard) return;

    try {
        const stats = await tradingApi.getAdminStats();
        dashboard.innerHTML = `
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Usuarios Activos</h3>
                    <p class="stat-value">${stats.activeUsers}</p>
                    <p class="stat-change ${stats.userGrowth >= 0 ? 'positive' : 'negative'}">
                        ${stats.userGrowth >= 0 ? '+' : ''}${stats.userGrowth}% este mes
                    </p>
                </div>
                <div class="stat-card">
                    <h3>Órdenes Pendientes</h3>
                    <p class="stat-value">${stats.pendingOrders}</p>
                    <p class="stat-change">${stats.ordersToday} hoy</p>
                </div>
                <div class="stat-card">
                    <h3>Volumen de Trading</h3>
                    <p class="stat-value">${formatCurrency(stats.tradingVolume)}</p>
                    <p class="stat-change">${formatCurrency(stats.volumeToday)} hoy</p>
                </div>
                <div class="stat-card">
                    <h3>Ingresos</h3>
                    <p class="stat-value">${formatCurrency(stats.revenue)}</p>
                    <p class="stat-change ${stats.revenueGrowth >= 0 ? 'positive' : 'negative'}">
                        ${stats.revenueGrowth >= 0 ? '+' : ''}${stats.revenueGrowth}% este mes
                    </p>
                </div>
            </div>
            <div class="recent-activity">
                <h3>Actividad Reciente</h3>
                <div class="activity-list">
                    ${stats.recentActivity.map(activity => `
                        <div class="activity-item">
                            <span class="activity-icon material-icons">${getActivityIcon(activity.type)}</span>
                            <div class="activity-content">
                                <p>${activity.description}</p>
                                <span class="activity-time">${formatDate(activity.timestamp)}</span>
                            </div>
                        </div>
                    `).join('')}
                </div>
            </div>
        `;
    } catch (error) {
        console.error('Error updating dashboard:', error);
    }
}

// User Management Component
function initializeUserManagement() {
    const userManagement = document.querySelector('.user-management');
    if (!userManagement) return;

    userManagement.innerHTML = `
        <div class="section-header">
            <h2>Gestión de Usuarios</h2>
            <button class="add-user-btn">
                <span class="material-icons">person_add</span>
                Agregar Usuario
            </button>
        </div>
        <div class="user-filters">
            <input type="text" placeholder="Buscar usuario..." class="user-search">
            <select class="user-role-filter">
                <option value="all">Todos los roles</option>
                <option value="admin">Administradores</option>
                <option value="trader">Traders</option>
                <option value="viewer">Visualizadores</option>
            </select>
            <select class="user-status-filter">
                <option value="all">Todos los estados</option>
                <option value="active">Activos</option>
                <option value="inactive">Inactivos</option>
                <option value="suspended">Suspendidos</option>
            </select>
        </div>
        <div class="users-table"></div>
    `;

    // Add event listeners
    addUserManagementEventListeners();
    updateUsersTable();
}

async function updateUsersTable() {
    const usersTable = document.querySelector('.users-table');
    if (!usersTable) return;

    try {
        const users = await tradingApi.getUsers();
        usersTable.innerHTML = `
            <div class="table-header">
                <div>Usuario</div>
                <div>Rol</div>
                <div>Estado</div>
                <div>Último Acceso</div>
                <div>Acciones</div>
            </div>
            ${users.map(user => `
                <div class="table-row">
                    <div class="user-info">
                        <img src="${user.avatar || 'assets/images/default-avatar.png'}" alt="${user.name}" class="user-avatar">
                        <div>
                            <div class="user-name">${user.name}</div>
                            <div class="user-email">${user.email}</div>
                        </div>
                    </div>
                    <div class="user-role">${user.role}</div>
                    <div class="user-status ${user.status.toLowerCase()}">${user.status}</div>
                    <div class="last-access">${formatDate(user.lastAccess)}</div>
                    <div class="user-actions">
                        <button class="edit-user-btn" data-id="${user.id}">
                            <span class="material-icons">edit</span>
                        </button>
                        <button class="toggle-user-btn" data-id="${user.id}" data-status="${user.status}">
                            <span class="material-icons">${user.status === 'active' ? 'block' : 'check_circle'}</span>
                        </button>
                        <button class="delete-user-btn" data-id="${user.id}">
                            <span class="material-icons">delete</span>
                        </button>
                    </div>
                </div>
            `).join('')}
        `;

        // Add event listeners
        addUserActionEventListeners();
    } catch (error) {
        console.error('Error updating users table:', error);
    }
}

// System Monitoring Component
function initializeSystemMonitoring() {
    const systemMonitoring = document.querySelector('.system-monitoring');
    if (!systemMonitoring) return;

    systemMonitoring.innerHTML = `
        <div class="section-header">
            <h2>Monitoreo del Sistema</h2>
            <div class="system-status">
                <span class="status-indicator online"></span>
                Sistema Operativo
            </div>
        </div>
        <div class="monitoring-grid">
            <div class="monitoring-card">
                <h3>CPU</h3>
                <div class="progress-bar">
                    <div class="progress" style="width: 0%"></div>
                </div>
                <p class="value">0%</p>
            </div>
            <div class="monitoring-card">
                <h3>Memoria</h3>
                <div class="progress-bar">
                    <div class="progress" style="width: 0%"></div>
                </div>
                <p class="value">0%</p>
            </div>
            <div class="monitoring-card">
                <h3>Almacenamiento</h3>
                <div class="progress-bar">
                    <div class="progress" style="width: 0%"></div>
                </div>
                <p class="value">0%</p>
            </div>
            <div class="monitoring-card">
                <h3>Red</h3>
                <div class="progress-bar">
                    <div class="progress" style="width: 0%"></div>
                </div>
                <p class="value">0 MB/s</p>
            </div>
        </div>
        <div class="system-logs">
            <h3>Registros del Sistema</h3>
            <div class="logs-container"></div>
        </div>
    `;

    // Start monitoring
    startSystemMonitoring();
}

// Reports Component
function initializeReports() {
    const reports = document.querySelector('.reports');
    if (!reports) return;

    reports.innerHTML = `
        <div class="section-header">
            <h2>Reportes</h2>
            <div class="report-actions">
                <button class="generate-report-btn">
                    <span class="material-icons">assessment</span>
                    Generar Reporte
                </button>
                <button class="export-data-btn">
                    <span class="material-icons">download</span>
                    Exportar Datos
                </button>
            </div>
        </div>
        <div class="reports-grid">
            <div class="report-card">
                <h3>Reporte de Usuarios</h3>
                <p>Estadísticas de usuarios y actividad</p>
                <button class="view-report-btn" data-report="users">Ver Reporte</button>
            </div>
            <div class="report-card">
                <h3>Reporte de Trading</h3>
                <p>Volumen y rendimiento de trading</p>
                <button class="view-report-btn" data-report="trading">Ver Reporte</button>
            </div>
            <div class="report-card">
                <h3>Reporte de Sistema</h3>
                <p>Rendimiento y uso del sistema</p>
                <button class="view-report-btn" data-report="system">Ver Reporte</button>
            </div>
        </div>
    `;

    // Add event listeners
    addReportEventListeners();
}

// Helper Functions
function getActivityIcon(type) {
    const icons = {
        'user': 'person',
        'order': 'shopping_cart',
        'system': 'computer',
        'security': 'security',
        'default': 'info'
    };
    return icons[type] || icons.default;
}

// Event Listeners
function addUserManagementEventListeners() {
    const userManagement = document.querySelector('.user-management');
    if (!userManagement) return;

    // Search
    const searchInput = userManagement.querySelector('.user-search');
    if (searchInput) {
        searchInput.addEventListener('input', debounce(updateUsersTable, 300));
    }

    // Filters
    const roleFilter = userManagement.querySelector('.user-role-filter');
    const statusFilter = userManagement.querySelector('.user-status-filter');
    if (roleFilter) {
        roleFilter.addEventListener('change', updateUsersTable);
    }
    if (statusFilter) {
        statusFilter.addEventListener('change', updateUsersTable);
    }

    // Add user button
    const addUserBtn = userManagement.querySelector('.add-user-btn');
    if (addUserBtn) {
        addUserBtn.addEventListener('click', showAddUserModal);
    }
}

function addUserActionEventListeners() {
    // Edit user
    document.querySelectorAll('.edit-user-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const userId = e.currentTarget.dataset.id;
            showEditUserModal(userId);
        });
    });

    // Toggle user status
    document.querySelectorAll('.toggle-user-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const userId = e.currentTarget.dataset.id;
            const currentStatus = e.currentTarget.dataset.status;
            try {
                await tradingApi.toggleUserStatus(userId, currentStatus);
                updateUsersTable();
            } catch (error) {
                console.error('Error toggling user status:', error);
            }
        });
    });

    // Delete user
    document.querySelectorAll('.delete-user-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const userId = e.currentTarget.dataset.id;
            if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
                try {
                    await tradingApi.deleteUser(userId);
                    updateUsersTable();
                } catch (error) {
                    console.error('Error deleting user:', error);
                }
            }
        });
    });
}

function addReportEventListeners() {
    const reports = document.querySelector('.reports');
    if (!reports) return;

    // Generate report
    const generateBtn = reports.querySelector('.generate-report-btn');
    if (generateBtn) {
        generateBtn.addEventListener('click', showGenerateReportModal);
    }

    // Export data
    const exportBtn = reports.querySelector('.export-data-btn');
    if (exportBtn) {
        exportBtn.addEventListener('click', exportData);
    }

    // View report
    document.querySelectorAll('.view-report-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const reportType = e.currentTarget.dataset.report;
            viewReport(reportType);
        });
    });
}

// System Monitoring
function startSystemMonitoring() {
    const monitoringCards = document.querySelectorAll('.monitoring-card');
    if (!monitoringCards.length) return;

    // Update system metrics every 5 seconds
    setInterval(async () => {
        try {
            const metrics = await tradingApi.getSystemMetrics();
            updateSystemMetrics(metrics);
        } catch (error) {
            console.error('Error updating system metrics:', error);
        }
    }, 5000);
}

function updateSystemMetrics(metrics) {
    // Update CPU
    const cpuCard = document.querySelector('.monitoring-card:nth-child(1)');
    if (cpuCard) {
        const progress = cpuCard.querySelector('.progress');
        const value = cpuCard.querySelector('.value');
        progress.style.width = `${metrics.cpu}%`;
        value.textContent = `${metrics.cpu}%`;
    }

    // Update Memory
    const memoryCard = document.querySelector('.monitoring-card:nth-child(2)');
    if (memoryCard) {
        const progress = memoryCard.querySelector('.progress');
        const value = memoryCard.querySelector('.value');
        progress.style.width = `${metrics.memory}%`;
        value.textContent = `${metrics.memory}%`;
    }

    // Update Storage
    const storageCard = document.querySelector('.monitoring-card:nth-child(3)');
    if (storageCard) {
        const progress = storageCard.querySelector('.progress');
        const value = storageCard.querySelector('.value');
        progress.style.width = `${metrics.storage}%`;
        value.textContent = `${metrics.storage}%`;
    }

    // Update Network
    const networkCard = document.querySelector('.monitoring-card:nth-child(4)');
    if (networkCard) {
        const progress = networkCard.querySelector('.progress');
        const value = networkCard.querySelector('.value');
        progress.style.width = `${metrics.network}%`;
        value.textContent = `${metrics.network} MB/s`;
    }
}

// Modal Functions
function showAddUserModal() {
    // TODO: Implement add user modal
}

function showEditUserModal(userId) {
    // TODO: Implement edit user modal
}

function showGenerateReportModal() {
    // TODO: Implement generate report modal
}

// Report Functions
function viewReport(reportType) {
    // TODO: Implement report viewing
}

async function exportData() {
    try {
        const data = await tradingApi.exportData();
        const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `acciones-el-bosque-${new Date().toISOString().split('T')[0]}.json`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    } catch (error) {
        console.error('Error exporting data:', error);
    }
} 