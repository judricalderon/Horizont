// js/utils/session.js
document.addEventListener("DOMContentLoaded", () => {
    const ONE_HOUR_MS = 60 * 60 * 1000;
    let user = JSON.parse(localStorage.getItem("usuario"));

    // Si hay usuario, compruebo expiración
    if (user) {
        const now = Date.now();

        // Si ya existe loginTime, revisa expiración
        if (user.loginTime) {
            if (now - user.loginTime > ONE_HOUR_MS) {
                // Sesión caducada
                localStorage.removeItem("usuario");
                window.location.href = "login.html";
                return;
            }
        } else {
            // Primera vez que cargo este script tras login, guardo loginTime
            user.loginTime = now;
            localStorage.setItem("usuario", JSON.stringify(user));
        }
    }

    const userNameEl = document.getElementById("userName");
    const userGreetingEl = document.getElementById("userGreeting");
    const loginLink = document.getElementById("loginLink");
    const userInfo = document.getElementById("userInfo");
    const logoutBtn = document.getElementById("logoutBtn");

    if (!user) {
        // Sin login: redirijo en páginas protegidas
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
        // Mostrar datos de usuario en UI
        if (userNameEl)      userNameEl.textContent = user.nombre;
        if (userGreetingEl)  userGreetingEl.textContent = `Hola, ${user.nombre}`;
        if (loginLink)       loginLink.style.display = "none";
        if (userInfo)        userInfo.style.display = "flex";

        // Ocultar link admin si no es admin
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
});

document.addEventListener("DOMContentLoaded", () => {
    const startBtn = document.getElementById("startPremiumBtn");
    if (!startBtn) return;

    startBtn.addEventListener("click", evt => {
        evt.preventDefault();
        const user = JSON.parse(localStorage.getItem("usuario"));
        // Si hay sesión y es premium
        if (user && user.esPremium) {
            window.location.href = "pasarela-pagos.html";
        } else {
            // Si no está logueado o no es premium
            window.location.href = "register.html";
        }
    });
});

