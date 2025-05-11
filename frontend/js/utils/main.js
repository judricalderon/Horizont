// Inicialización principal de la aplicación
document.addEventListener('DOMContentLoaded', () => {
    // Inicializar componentes
    initializeNavbar();
    initializeFooter();
    
    // Inicializar funcionalidad específica de la página según la página actual
    const currentPage = window.location.pathname.split('/').pop();
    initializePage(currentPage);
});

// Initialize page-specific functionality
function initializePage(pageName) {
    switch(pageName) {
        case 'login.html':
            initializeLogin();
            break;
        case 'register.html':
            initializeRegister();
            break;
        case 'trading.html':
            initializeTrading();
            break;
        case 'portfolio.html':
            initializePortfolio();
            break;
        case 'notifications.html':
            initializeNotifications();
            break;
        case 'admin.html':
            initializeAdmin();
            break;
        default:
            initializeDashboard();
    }
}

// Import and initialize components
import { initializeNavbar } from './components/navbar.js';
import { initializeFooter } from './components/footer.js';

// Import page-specific modules
import { initializeLogin } from './pages/login.js';
import { initializeRegister } from './pages/register.js';
import { initializeTrading } from './pages/trading.js';
import { initializePortfolio } from '../../../src/pages/portfolio.js';
import { initializeNotifications } from './pages/notifications.js';
import { initializeAdmin } from './pages/admin.js';
import { initializeDashboard } from './pages/dashboard.js'; 