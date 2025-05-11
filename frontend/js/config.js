// Configuración de la API
export const API_CONFIG = {
    BASE_URL: process.env.API_BASE_URL || 'http://localhost:3000/api',
    VERSION: 'v1',
    TIMEOUT: 10000,
    HEADERS: {
        'Content-Type': 'application/json'
    }
};

// Puntos finales de la API
export const ENDPOINTS = {
    // Autenticación
    AUTH: {
        LOGIN: '/auth/login',
        REGISTER: '/auth/register',
        LOGOUT: '/auth/logout',
        REFRESH_TOKEN: '/auth/refresh-token',
        FORGOT_PASSWORD: '/auth/forgot-password',
        RESET_PASSWORD: '/auth/reset-password'
    },
    
    // Usuario
    USER: {
        PROFILE: '/user/profile',
        UPDATE_PROFILE: '/user/profile/update',
        CHANGE_PASSWORD: '/user/change-password',
        PREFERENCES: '/user/preferences'
    },
    
    // Trading
    TRADING: {
        ORDERS: '/trading/orders',
        POSITIONS: '/trading/positions',
        PORTFOLIO: '/trading/portfolio',
        MARKET_DATA: '/trading/market-data',
        SYMBOLS: '/trading/symbols'
    },
    
    // Notificaciones
    NOTIFICATIONS: {
        LIST: '/notifications',
        MARK_READ: '/notifications/mark-read',
        MARK_ALL_READ: '/notifications/mark-all-read',
        DELETE: '/notifications/delete'
    },
    
    // Administración
    ADMIN: {
        USERS: '/admin/users',
        ORDERS: '/admin/orders',
        REPORTS: '/admin/reports',
        SETTINGS: '/admin/settings'
    }
};

// Códigos de respuesta de la API
export const API_CODES = {
    SUCCESS: 200,
    CREATED: 201,
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    NOT_FOUND: 404,
    SERVER_ERROR: 500
};

// Mensajes de error de la API
export const API_MESSAGES = {
    [API_CODES.BAD_REQUEST]: 'Solicitud incorrecta',
    [API_CODES.UNAUTHORIZED]: 'No autorizado',
    [API_CODES.FORBIDDEN]: 'Acceso denegado',
    [API_CODES.NOT_FOUND]: 'Recurso no encontrado',
    [API_CODES.SERVER_ERROR]: 'Error del servidor'
};

// Interceptor de solicitudes de la API
export const requestInterceptor = (config) => {
    const token = localStorage.getItem('userToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
};

// Interceptor de respuestas de la API
export const responseInterceptor = (response) => {
    return response.data;
};

// Interceptor de errores de la API
export const errorInterceptor = (error) => {
    if (error.response?.status === API_CODES.UNAUTHORIZED) {
        // Manejar expiración del token
        localStorage.removeItem('userToken');
        window.location.href = '/login.html';
    }
    return Promise.reject(error);
}; 