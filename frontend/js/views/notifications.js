import { tradingApi } from '../api/tradingApi';
import { formatCurrency, formatDate } from '../utils/helpers';

export function initializeNotifications() {
    const notificationsPage = document.querySelector('.notifications-page');
    if (!notificationsPage) return;

    // Initialize components
    initializeNotificationList();
    initializeNotificationSettings();
}

// Notification List Component
async function initializeNotificationList() {
    const notificationList = document.querySelector('.notification-list');
    if (!notificationList) return;

    await updateNotificationList();
}

async function updateNotificationList() {
    const notificationList = document.querySelector('.notification-list');
    if (!notificationList) return;

    try {
        const notifications = await tradingApi.getNotifications();
        notificationList.innerHTML = `
            <div class="notification-list-header">
                <h3>Notificaciones</h3>
                <div class="notification-controls">
                    <button class="mark-all-read-btn">
                        <span class="material-icons">done_all</span>
                        Marcar todo como leído
                    </button>
                    <button class="clear-all-btn">
                        <span class="material-icons">delete</span>
                        Limpiar todo
                    </button>
                </div>
            </div>
            <div class="notification-filters">
                <div class="filter-group">
                    <label>Estado:</label>
                    <select class="status-filter">
                        <option value="all">Todas</option>
                        <option value="unread">No leídas</option>
                        <option value="read">Leídas</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label>Tipo:</label>
                    <select class="type-filter">
                        <option value="all">Todos</option>
                        <option value="trade">Operaciones</option>
                        <option value="order">Órdenes</option>
                        <option value="alert">Alertas</option>
                        <option value="system">Sistema</option>
                    </select>
                </div>
            </div>
            <div class="notification-content">
                ${notifications.map(notification => `
                    <div class="notification-item ${notification.read ? 'read' : 'unread'} ${notification.type}">
                        <div class="notification-icon">
                            <span class="material-icons">${getNotificationIcon(notification.type)}</span>
                        </div>
                        <div class="notification-body">
                            <div class="notification-header">
                                <h4>${notification.title}</h4>
                                <span class="notification-time">${formatDate(notification.timestamp)}</span>
                            </div>
                            <p class="notification-message">${notification.message}</p>
                            ${notification.action ? `
                                <div class="notification-action">
                                    <button class="action-btn" data-action="${notification.action.type}" data-id="${notification.id}">
                                        ${notification.action.label}
                                    </button>
                                </div>
                            ` : ''}
                        </div>
                        <div class="notification-actions">
                            ${!notification.read ? `
                                <button class="mark-read-btn" data-id="${notification.id}">
                                    <span class="material-icons">check</span>
                                </button>
                            ` : ''}
                            <button class="delete-btn" data-id="${notification.id}">
                                <span class="material-icons">delete</span>
                            </button>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;

        // Add event listeners
        addNotificationListEventListeners();
    } catch (error) {
        console.error('Error updating notification list:', error);
    }
}

// Notification Settings Component
function initializeNotificationSettings() {
    const notificationSettings = document.querySelector('.notification-settings');
    if (!notificationSettings) return;

    updateNotificationSettings();
}

async function updateNotificationSettings() {
    const notificationSettings = document.querySelector('.notification-settings');
    if (!notificationSettings) return;

    try {
        const settings = await tradingApi.getNotificationSettings();
        notificationSettings.innerHTML = `
            <div class="settings-header">
                <h3>Configuración de Notificaciones</h3>
                <button class="save-settings-btn">
                    <span class="material-icons">save</span>
                    Guardar Cambios
                </button>
            </div>
            <div class="settings-content">
                <div class="settings-section">
                    <h4>Notificaciones de Operaciones</h4>
                    <div class="setting-item">
                        <label>Ordenes Ejecutadas</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="orderExecuted" ${settings.orderExecuted ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                    <div class="setting-item">
                        <label>Órdenes Canceladas</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="orderCancelled" ${settings.orderCancelled ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                    <div class="setting-item">
                        <label>Órdenes Rechazadas</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="orderRejected" ${settings.orderRejected ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                </div>
                <div class="settings-section">
                    <h4>Alertas de Precio</h4>
                    <div class="setting-item">
                        <label>Precio Alcanzado</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="priceReached" ${settings.priceReached ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                    <div class="setting-item">
                        <label>Cambio de Precio</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="priceChange" ${settings.priceChange ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                </div>
                <div class="settings-section">
                    <h4>Notificaciones del Sistema</h4>
                    <div class="setting-item">
                        <label>Mantenimiento Programado</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="systemMaintenance" ${settings.systemMaintenance ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                    <div class="setting-item">
                        <label>Actualizaciones de la Plataforma</label>
                        <div class="toggle-switch">
                            <input type="checkbox" name="platformUpdates" ${settings.platformUpdates ? 'checked' : ''}>
                            <span class="slider"></span>
                        </div>
                    </div>
                </div>
            </div>
        `;

        // Add event listeners
        addNotificationSettingsEventListeners();
    } catch (error) {
        console.error('Error updating notification settings:', error);
    }
}

// Event Listeners
function addNotificationListEventListeners() {
    const notificationList = document.querySelector('.notification-list');
    if (!notificationList) return;

    // Mark all as read button
    const markAllReadBtn = notificationList.querySelector('.mark-all-read-btn');
    if (markAllReadBtn) {
        markAllReadBtn.addEventListener('click', async () => {
            try {
                await tradingApi.markAllNotificationsAsRead();
                updateNotificationList();
                showNotification('Todas las notificaciones marcadas como leídas', 'success');
            } catch (error) {
                console.error('Error marking all notifications as read:', error);
                showNotification('Error al marcar las notificaciones como leídas', 'error');
            }
        });
    }

    // Clear all button
    const clearAllBtn = notificationList.querySelector('.clear-all-btn');
    if (clearAllBtn) {
        clearAllBtn.addEventListener('click', async () => {
            if (confirm('¿Está seguro de que desea eliminar todas las notificaciones?')) {
                try {
                    await tradingApi.clearAllNotifications();
                    updateNotificationList();
                    showNotification('Todas las notificaciones eliminadas', 'success');
                } catch (error) {
                    console.error('Error clearing all notifications:', error);
                    showNotification('Error al eliminar las notificaciones', 'error');
                }
            }
        });
    }

    // Status filter
    const statusFilter = notificationList.querySelector('.status-filter');
    if (statusFilter) {
        statusFilter.addEventListener('change', updateNotificationList);
    }

    // Type filter
    const typeFilter = notificationList.querySelector('.type-filter');
    if (typeFilter) {
        typeFilter.addEventListener('change', updateNotificationList);
    }

    // Mark as read buttons
    notificationList.querySelectorAll('.mark-read-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const id = e.currentTarget.dataset.id;
            try {
                await tradingApi.markNotificationAsRead(id);
                updateNotificationList();
                showNotification('Notificación marcada como leída', 'success');
            } catch (error) {
                console.error('Error marking notification as read:', error);
                showNotification('Error al marcar la notificación como leída', 'error');
            }
        });
    });

    // Delete buttons
    notificationList.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const id = e.currentTarget.dataset.id;
            try {
                await tradingApi.deleteNotification(id);
                updateNotificationList();
                showNotification('Notificación eliminada', 'success');
            } catch (error) {
                console.error('Error deleting notification:', error);
                showNotification('Error al eliminar la notificación', 'error');
            }
        });
    });

    // Action buttons
    notificationList.querySelectorAll('.action-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const action = e.currentTarget.dataset.action;
            const id = e.currentTarget.dataset.id;
            handleNotificationAction(action, id);
        });
    });
}

function addNotificationSettingsEventListeners() {
    const notificationSettings = document.querySelector('.notification-settings');
    if (!notificationSettings) return;

    // Save settings button
    const saveSettingsBtn = notificationSettings.querySelector('.save-settings-btn');
    if (saveSettingsBtn) {
        saveSettingsBtn.addEventListener('click', async () => {
            const settings = {
                orderExecuted: notificationSettings.querySelector('input[name="orderExecuted"]').checked,
                orderCancelled: notificationSettings.querySelector('input[name="orderCancelled"]').checked,
                orderRejected: notificationSettings.querySelector('input[name="orderRejected"]').checked,
                priceReached: notificationSettings.querySelector('input[name="priceReached"]').checked,
                priceChange: notificationSettings.querySelector('input[name="priceChange"]').checked,
                systemMaintenance: notificationSettings.querySelector('input[name="systemMaintenance"]').checked,
                platformUpdates: notificationSettings.querySelector('input[name="platformUpdates"]').checked
            };

            try {
                await tradingApi.updateNotificationSettings(settings);
                showNotification('Configuración guardada exitosamente', 'success');
            } catch (error) {
                console.error('Error saving notification settings:', error);
                showNotification('Error al guardar la configuración', 'error');
            }
        });
    }
}

// Helper Functions
function getNotificationIcon(type) {
    const icons = {
        'trade': 'swap_horiz',
        'order': 'shopping_cart',
        'alert': 'notifications',
        'system': 'info',
        'default': 'notifications_none'
    };
    return icons[type] || icons.default;
}

async function handleNotificationAction(action, id) {
    switch (action) {
        case 'view_order':
            const order = await tradingApi.getOrder(id);
            window.location.href = `/orders.html?id=${order.id}`;
            break;
        case 'view_trade':
            const trade = await tradingApi.getTrade(id);
            window.location.href = `/trading.html?symbol=${trade.symbol}`;
            break;
        case 'view_alert':
            const alert = await tradingApi.getAlert(id);
            window.location.href = `/alerts.html?id=${alert.id}`;
            break;
        default:
            console.warn('Unknown notification action:', action);
    }
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

// Auto-refresh notifications
setInterval(() => {
    updateNotificationList();
}, 60000); // Refresh every minute 