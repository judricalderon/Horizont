function toggleAuthModal() {
    const modal = document.getElementById('authModal');
    modal.classList.toggle('active');
}

// Tab switching functionality
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.tab-labels label').forEach(label => {
        label.addEventListener('click', (e) => {
            const tabId = e.target.getAttribute('for');
            document.querySelectorAll('.tab-content').forEach(content => {
                content.style.display = content.id === tabId + '-content' ? 'block' : 'none';
            });
        });
    });
});

// Función para manejar el registro de usuarios
function registerUser(userData) {
    // Obtener usuarios existentes o inicializar array vacío
    let users = JSON.parse(localStorage.getItem('users')) || [];
    
    // Verificar si el usuario o email ya existen
    const userExists = users.some(user => 
        user.username === userData.username || 
        user.email === userData.email
    );
    
    if (userExists) {
        throw new Error('El usuario o correo ya existe');
    }
    
    // Asignar ID y datos adicionales
    userData.id = users.length + 1;
    userData.createdAt = new Date().toISOString();
    userData.status = 'ACTIVE';
    
    // Agregar nuevo usuario
    users.push(userData);
    localStorage.setItem('users', JSON.stringify(users));
    
    return userData;
}

// Función para manejar el inicio de sesión
function loginUser(usernameOrEmail, password) {
    // Obtener usuarios registrados
    const users = JSON.parse(localStorage.getItem('users')) || [];
    
    // Usuarios predefinidos para demostración
    const hardcodedUsers = [
        { 
            username: 'admin', 
            password: '12345', 
            email: 'admin@elbosque.edu.co',
            fullName: 'Administrador Sistema', 
            role: 'ADMIN'
        },
        { 
            username: 'profesor', 
            password: '12345', 
            email: 'profesor@elbosque.edu.co', 
            fullName: 'Profesor Demo',
            role: 'TEACHER'
        },
        { 
            username: 'estudiante', 
            password: '12345', 
            email: 'estudiante@elbosque.edu.co', 
            fullName: 'Estudiante Demo',
            role: 'STUDENT'
        },
        { 
            username: 'monitor', 
            password: '12345', 
            email: 'monitor@elbosque.edu.co', 
            fullName: 'Monitor Demo',
            role: 'MONITOR'
        }
    ];
    
    // Combinar usuarios
    const allUsers = [...hardcodedUsers, ...users];
    
    // Verificar si es email
    const isEmail = usernameOrEmail.includes('@');
    
    // Buscar usuario
    const user = allUsers.find(user => 
        (isEmail ? user.email === usernameOrEmail : user.username === usernameOrEmail) && 
        user.password === password
    );
    
    if (!user) {
        throw new Error('Usuario o contraseña incorrectos');
    }
    
    // Guardar sesión
    const sessionUser = {
        username: user.username,
        fullName: user.fullName || user.username,
        role: user.role,
        id: user.id || 0
    };
    
    localStorage.setItem('currentUser', JSON.stringify(sessionUser));
    return sessionUser;
}

// Función para verificar si hay una sesión activa
function checkSession() {
    const currentUser = localStorage.getItem('currentUser');
    return currentUser ? JSON.parse(currentUser) : null;
}

// Función para cerrar sesión
function logout() {
    localStorage.removeItem('currentUser');
    window.location.href = 'login.html';
}