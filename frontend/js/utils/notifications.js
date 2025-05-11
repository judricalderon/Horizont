// Notifications Page JavaScript

document.addEventListener('DOMContentLoaded', () => {
    // Inicializar pestañas
    const tabs = document.querySelectorAll('.tab-button');
    const notificationsList = document.querySelector('.notifications-list');

    // Sample notifications data (in a real app, this would come from an API)
    const notifications = {
        all: [
            {
                type: 'trading',
                title: 'Orden Ejecutada: AAPL',
                time: 'Hace 5 minutos',
                message: 'Tu orden de compra de 10 acciones de AAPL se ha ejecutado exitosamente a $150.25.',
                unread: true
            },
            {
                type: 'alert',
                title: 'Alerta de Precio: GOOGL',
                time: 'Hace 1 hora',
                message: 'GOOGL ha alcanzado tu precio objetivo de $2,750.00.',
                unread: false
            },
            {
                type: 'system',
                title: 'Actualización del Sistema',
                time: 'Hace 2 horas',
                message: 'Nueva actualización disponible con mejoras en el rendimiento y nuevas funcionalidades.',
                unread: true
            }
        ],
        trading: [],
        alerts: [],
        system: []
    };

    // Filter notifications by type
    notifications.trading = notifications.all.filter(n => n.type === 'trading');
    notifications.alerts = notifications.all.filter(n => n.type === 'alert');
    notifications.system = notifications.all.filter(n => n.type === 'system');

    // Tab click handler
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const category = tab.dataset.category;
            renderNotifications(category);
            
            // Actualizar estado activo de las pestañas
            tabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
        });
    });

    // Render notifications
    function renderNotifications(type) {
        notificationsList.innerHTML = '';
        notifications[type].forEach(notification => {
            const notificationElement = createNotificationElement(notification);
            notificationsList.appendChild(notificationElement);
        });
    }

    // Create notification element
    function createNotificationElement(notification) {
        const div = document.createElement('div');
        div.className = `notification-item${notification.unread ? ' unread' : ''}`;
        
        let iconName;
        switch(notification.type) {
            case 'trading':
                iconName = 'trending_up';
                break;
            case 'alert':
                iconName = 'notifications_active';
                break;
            case 'system':
                iconName = 'system_update';
                break;
        }

        div.innerHTML = `
            <span class="material-icons notification-icon ${notification.type}">${iconName}</span>
            <div class="notification-content">
                <div class="notification-header">
                    <h3>${notification.title}</h3>
                    <span class="notification-time">${notification.time}</span>
                </div>
                <p>${notification.message}</p>
            </div>
        `;

        return div;
    }

    // Botón para marcar como leídas
    const markReadBtn = document.querySelector('.btn-mark-read');
    markReadBtn.addEventListener('click', () => {
        const unreadNotifications = document.querySelectorAll('.notification-item.unread');
        unreadNotifications.forEach(notification => {
            notification.classList.remove('unread');
        });
        
        // Actualizar datos de notificaciones
        notifications.all.forEach(notification => notification.unread = false);
        notifications.trading.forEach(notification => notification.unread = false);
        notifications.alerts.forEach(notification => notification.unread = false);
        notifications.system.forEach(notification => notification.unread = false);
    });

    // Manejador del botón de filtro (a implementar con un modal o menú desplegable)
    const filterBtn = document.querySelector('.btn-filter');
    filterBtn.addEventListener('click', () => {
        // TODO: Implementar funcionalidad de filtro
        console.log('Botón de filtro presionado');
    });

    // Renderizado inicial
    renderNotifications('all');
}); 