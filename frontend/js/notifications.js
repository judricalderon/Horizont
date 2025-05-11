import { tradingApi } from '../api/tradingApi';
import { formatDate } from '../utils/helpers';

export function initializeNotifications() {
    const notificationsPage = document.querySelector('.notifications-page');
    if (!notificationsPage) return;

    // Initialize components
    initializeNotificationList();
    initializeNotificationFilters();
    setupWebSocket();
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
        notificationList.innerHTML = notifications.map(notification => `
            <div class="notification-item ${notification.read ? '' : 'unread'}">
                <div class="notification-icon">
                    <span class="material-icons">${getNotificationIcon(notification.type)}</span>
                </div>
                <div class="notification-content">
                    <h4>${notification.title}</h4>
                    <p>${notification.message}</p>
                    <span class="notification-time">${formatDate(notification.timestamp)}</span>
                </div>
                <div class="notification-actions">
                    ${!notification.read ? `
                        <button class="mark-read-btn" data-id="${notification.id}">
                            <span class="material-icons">check_circle</span>
                        </button>
                    ` : ''}
                    <button class="delete-btn" data-id="${notification.id}">
                        <span class="material-icons">delete</span>
                    </button>
                </div>
            </div>
        `).join('');

        // Add event listeners
        addNotificationEventListeners();
    } catch (error) {
        console.error('Error updating notifications:', error);
    }
}

function getNotificationIcon(type) {
    const icons = {
        'order': 'shopping_cart',
        'price': 'trending_up',
        'portfolio': 'account_balance',
        'system': 'info',
        'alert': 'warning',
        'default': 'notifications'
    };
    return icons[type] || icons.default;
}

// Notification Filters Component
function initializeNotificationFilters() {
    const filterContainer = document.querySelector('.notification-filters');
    if (!filterContainer) return;

    filterContainer.innerHTML = `
        <div class="filter-group">
            <button class="filter-btn active" data-filter="all">Todas</button>
            <button class="filter-btn" data-filter="unread">No leídas</button>
            <button class="filter-btn" data-filter="order">Órdenes</button>
            <button class="filter-btn" data-filter="price">Precios</button>
            <button class="filter-btn" data-filter="portfolio">Portafolio</button>
        </div>
        <div class="filter-actions">
            <button class="mark-all-read-btn">
                <span class="material-icons">done_all</span>
                Marcar todas como leídas
            </button>
            <button class="clear-all-btn">
                <span class="material-icons">delete_sweep</span>
                Limpiar todas
            </button>
        </div>
    `;

    // Add event listeners
    addFilterEventListeners();
}

// WebSocket Setup
function setupWebSocket() {
    const ws = new WebSocket('ws://localhost:3000/ws/notifications');
    
    ws.onopen = () => {
        console.log('WebSocket connection established');
    };

    ws.onmessage = (event) => {
        const notification = JSON.parse(event.data);
        addNewNotification(notification);
    };

    ws.onerror = (error) => {
        console.error('WebSocket error:', error);
    };

    ws.onclose = () => {
        console.log('WebSocket connection closed');
        // Attempt to reconnect after 5 seconds
        setTimeout(setupWebSocket, 5000);
    };
}

// Event Listeners
function addNotificationEventListeners() {
    // Mark as read
    document.querySelectorAll('.mark-read-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const notificationId = e.currentTarget.dataset.id;
            try {
                await tradingApi.markNotificationAsRead(notificationId);
                e.currentTarget.closest('.notification-item').classList.remove('unread');
                e.currentTarget.remove();
            } catch (error) {
                console.error('Error marking notification as read:', error);
            }
        });
    });

    // Delete notification
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', async (e) => {
            const notificationId = e.currentTarget.dataset.id;
            try {
                await tradingApi.deleteNotification(notificationId);
                e.currentTarget.closest('.notification-item').remove();
            } catch (error) {
                console.error('Error deleting notification:', error);
            }
        });
    });
}

function addFilterEventListeners() {
    const filterContainer = document.querySelector('.notification-filters');
    if (!filterContainer) return;

    // Filter buttons
    filterContainer.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const filter = e.currentTarget.dataset.filter;
            filterContainer.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
            e.currentTarget.classList.add('active');
            filterNotifications(filter);
        });
    });

    // Mark all as read
    const markAllReadBtn = filterContainer.querySelector('.mark-all-read-btn');
    if (markAllReadBtn) {
        markAllReadBtn.addEventListener('click', async () => {
            try {
                await tradingApi.markAllNotificationsAsRead();
                document.querySelectorAll('.notification-item').forEach(item => {
                    item.classList.remove('unread');
                    const markReadBtn = item.querySelector('.mark-read-btn');
                    if (markReadBtn) markReadBtn.remove();
                });
            } catch (error) {
                console.error('Error marking all notifications as read:', error);
            }
        });
    }

    // Clear all
    const clearAllBtn = filterContainer.querySelector('.clear-all-btn');
    if (clearAllBtn) {
        clearAllBtn.addEventListener('click', async () => {
            if (confirm('¿Estás seguro de que deseas eliminar todas las notificaciones?')) {
                try {
                    await tradingApi.deleteAllNotifications();
                    document.querySelector('.notification-list').innerHTML = '';
                } catch (error) {
                    console.error('Error clearing all notifications:', error);
                }
            }
        });
    }
}

// Helper Functions
function filterNotifications(filter) {
    const notifications = document.querySelectorAll('.notification-item');
    notifications.forEach(notification => {
        if (filter === 'all') {
            notification.style.display = 'flex';
        } else if (filter === 'unread') {
            notification.style.display = notification.classList.contains('unread') ? 'flex' : 'none';
        } else {
            const type = notification.querySelector('.notification-icon .material-icons').textContent;
            notification.style.display = type === getNotificationIcon(filter) ? 'flex' : 'none';
        }
    });
}

function addNewNotification(notification) {
    const notificationList = document.querySelector('.notification-list');
    if (!notificationList) return;

    const notificationElement = document.createElement('div');
    notificationElement.className = 'notification-item unread';
    notificationElement.innerHTML = `
        <div class="notification-icon">
            <span class="material-icons">${getNotificationIcon(notification.type)}</span>
        </div>
        <div class="notification-content">
            <h4>${notification.title}</h4>
            <p>${notification.message}</p>
            <span class="notification-time">${formatDate(notification.timestamp)}</span>
        </div>
        <div class="notification-actions">
            <button class="mark-read-btn" data-id="${notification.id}">
                <span class="material-icons">check_circle</span>
            </button>
            <button class="delete-btn" data-id="${notification.id}">
                <span class="material-icons">delete</span>
            </button>
        </div>
    `;

    notificationList.insertBefore(notificationElement, notificationList.firstChild);
    addNotificationEventListeners();
}