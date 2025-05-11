export function initializeNavbar() {
    const navbar = document.querySelector('.navbar');
    if (!navbar) return;

    // Crear botón de menú móvil si no existe
    let mobileMenuBtn = navbar.querySelector('.mobile-menu-toggle');
    if (!mobileMenuBtn) {
        mobileMenuBtn = document.createElement('button');
        mobileMenuBtn.className = 'mobile-menu-toggle';
        mobileMenuBtn.innerHTML = '<span class="material-icons">menu</span>';
        mobileMenuBtn.setAttribute('aria-label', 'Abrir menú');
        navbar.appendChild(mobileMenuBtn);
    }

    const navLinks = navbar.querySelector('.nav-links');
    if (!navLinks) return;

    // Manejar el toggle del menú móvil
    mobileMenuBtn.addEventListener('click', () => {
        navLinks.classList.toggle('active');
        const isOpen = navLinks.classList.contains('active');
        mobileMenuBtn.setAttribute('aria-expanded', isOpen);
        mobileMenuBtn.innerHTML = isOpen ? 
            '<span class="material-icons">close</span>' : 
            '<span class="material-icons">menu</span>';
    });

    // Resaltar el enlace activo
    const currentPath = window.location.pathname;
    const links = navLinks.querySelectorAll('a');
    links.forEach(link => {
        const linkPath = link.getAttribute('href');
        if (linkPath === currentPath.split('/').pop()) {
            link.classList.add('active');
            link.setAttribute('aria-current', 'page');
        }
    });

    // Manejar el menú de usuario
    const userMenu = navbar.querySelector('.user-menu');
    if (userMenu) {
        const isLoggedIn = localStorage.getItem('userToken');
        if (isLoggedIn) {
            const userName = localStorage.getItem('userName') || 'Usuario';
            userMenu.innerHTML = `
                <div class="user-profile">
                    <span class="material-icons">account_circle</span>
                    <span class="user-name">${userName}</span>
                </div>
                <button class="logout-btn" onclick="logout()">
                    <span class="material-icons">logout</span>
                    Cerrar Sesión
                </button>
            `;
        } else {
            userMenu.innerHTML = `
                <a href="login.html" class="nav-link">Ingresar</a>
                <a href="register.html" class="nav-link btn-primary">Registrarse</a>
            `;
        }
    }

    // Cerrar el menú móvil al hacer clic fuera
    document.addEventListener('click', (e) => {
        if (!navbar.contains(e.target) && navLinks.classList.contains('active')) {
            navLinks.classList.remove('active');
            mobileMenuBtn.setAttribute('aria-expanded', 'false');
            mobileMenuBtn.innerHTML = '<span class="material-icons">menu</span>';
        }
    });

    // Manejar la navegación por teclado
    navLinks.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && navLinks.classList.contains('active')) {
            navLinks.classList.remove('active');
            mobileMenuBtn.setAttribute('aria-expanded', 'false');
            mobileMenuBtn.innerHTML = '<span class="material-icons">menu</span>';
            mobileMenuBtn.focus();
        }
    });
}

// Función para cerrar sesión
export function logout() {
    localStorage.removeItem('userToken');
    localStorage.removeItem('userName');
    window.location.href = 'index.html';
} 