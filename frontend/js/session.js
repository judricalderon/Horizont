const ONE_HOUR_MS = 60 * 60 * 1000;

/**
 * Valida si hay un usuario autenticado, su sesión no ha caducado y permite configurar el comportamiento.
 * @param {Object} options
 * @param {boolean} options.redirigir - Si debe redirigir si no hay sesión (default: true)
 * @param {boolean} options.permitirPremium - Si debe permitir usuarios premium (default: true)
 * @returns {object|null} usuario si está autenticado, null si no lo está
 */
function obtenerUsuarioAutenticado(options = { redirigir: true, permitirPremium: true }) {
    let user = JSON.parse(localStorage.getItem("usuario"));
    const now = Date.now();

    // Validar expiración de sesión
    if (user && user.loginTime && now - user.loginTime > ONE_HOUR_MS) {
        localStorage.removeItem("usuario");
        user = null;
    }

    if (user && !user.loginTime) {
        user.loginTime = now;
        localStorage.setItem("usuario", JSON.stringify(user));
    }

    if (!user && options.redirigir) {
        window.location.href = "login.html";
        return null;
    }

    if (user?.esPremium && options.permitirPremium === false) {
        alert("Ya tienes una suscripción activa. ¡Gracias por ser premium!");
        return null;
    }

    return user;
}

document.addEventListener("DOMContentLoaded", () => {
    const user = obtenerUsuarioAutenticado({ redirigir: false });

    const userNameEl = document.getElementById("userName");
    const userGreetingEl = document.getElementById("userGreeting");
    const loginLink = document.getElementById("loginLink");
    const userInfo = document.getElementById("userInfo");
    const logoutBtn = document.getElementById("logoutBtn");

    if (!user) {
        // Redirigir si está en una página protegida
        const protectedPages = [
            "portfolio.html",
            "notifications.html",
            "admin.html",
            "trading.html"
        ];
        if (protectedPages.some(p => location.pathname.includes(p))) {
            window.location.href = "login.html";
            return;
        }
    } else {
        // Mostrar información en UI
        if (userNameEl) userNameEl.textContent = user.nombre;
        if (userGreetingEl) userGreetingEl.textContent = `Hola, ${user.nombre}`;
        if (loginLink) loginLink.style.display = "none";
        if (userInfo) userInfo.style.display = "flex";

        if (user.rol !== "admin") {
            const adminLink = document.querySelector('a[href="admin.html"]');
            if (adminLink) adminLink.style.display = "none";
        }
    }

    // Logout
    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            localStorage.removeItem("usuario");
            window.location.href = "login.html";
        });
    }

    // Lógica del botón premium (si existe)
    const startBtn = document.getElementById("startPremiumBtn");
    if (startBtn) {
        startBtn.addEventListener("click", evt => {
            evt.preventDefault();
            const usuario = obtenerUsuarioAutenticado({ redirigir: true, permitirPremium: false });
            if (usuario) {
                window.location.href = "hazte-premium.html";
            }
        });
    }
});
export { obtenerUsuarioAutenticado };

