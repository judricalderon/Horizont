// Initial users with different roles
const users = [
    {
        id: 1,
        username: 'admin',
        email: 'admin@elbosque.edu.co',
        password: '12345', // In production, this should be hashed
        role: 'ADMIN',
        fullName: 'Administrador Sistema',
        active: true,
        permissions: ['ALL']
    },
    
    {
        id: 2,
        username: 'profesor',
        email: 'profesor@elbosque.edu.co',
        password: '12345',
        role: 'TEACHER',
        fullName: 'Profesor Demo',
        active: true,
        permissions: ['VIEW_DASHBOARD', 'VIEW_TRADING', 'MANAGE_STUDENTS']
    },
    {
        id: 3,
        username: 'estudiante',
        email: 'estudiante@elbosque.edu.co',
        password: '12345',
        role: 'STUDENT',
        fullName: 'Estudiante Demo',
        active: true,
        permissions: ['VIEW_DASHBOARD', 'VIEW_TRADING', 'PLACE_ORDERS']
    },
    {
        id: 4,
        username: 'monitor',
        email: 'monitor@elbosque.edu.co',
        password: '12345',
        role: 'MONITOR',
        fullName: 'Monitor Demo',
        active: true,
        permissions: ['VIEW_DASHBOARD', 'VIEW_TRADING', 'HELP_STUDENTS']
    },
    {
        id: 5,
        username: 'invitado',
        email: 'invitado@elbosque.edu.co',
        password: '12345',
        role: 'GUEST',
        fullName: 'Usuario Invitado',
        active: true,
        permissions: ['VIEW_DASHBOARD']
    }
];

// Role definitions
const roles = {
    ADMIN: {
        name: 'Administrador',
        description: 'Acceso total al sistema',
        level: 1
    },
    TEACHER: {
        name: 'Profesor',
        description: 'Gestión de estudiantes y monitoreo',
        level: 2
    },
    MONITOR: {
        name: 'Monitor',
        description: 'Ayuda y soporte a estudiantes',
        level: 3
    },
    STUDENT: {
        name: 'Estudiante',
        description: 'Acceso a trading y dashboard',
        level: 4
    },
    GUEST: {
        name: 'Invitado',
        description: 'Acceso limitado solo a visualización',
        level: 5
    }
};

module.exports = {
    users,
    roles
}; 