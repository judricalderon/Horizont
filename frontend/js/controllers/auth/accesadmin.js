document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('adminRegisterForm');
    const submitBtn = document.getElementById('submitBtn');
    const errorBanner = document.getElementById('error-message');
    const successBanner = document.getElementById('success-message');
    const rolSelect = document.getElementById('rol');

    // Cambia el texto del botón según el rol seleccionado
    function updateButtonText() {
        const rol = rolSelect.value;
        if (rol === 'admin') {
            submitBtn.textContent = 'Registrar Administrador';
        } else if (rol === 'comisionista') {
            submitBtn.textContent = 'Registrar Comisionista';
        }
    }
    rolSelect.addEventListener('change', updateButtonText);
    updateButtonText(); // Inicializa el texto del botón

    function showError(msg) {
        document.getElementById('error-text').textContent = msg;
        errorBanner.classList.add('show');
        submitBtn.disabled = false;
        updateButtonText();
    }

    form.addEventListener('submit', e => {
        e.preventDefault();
        errorBanner.classList.remove('show');
        successBanner.classList.remove('show');

        const nombre = document.getElementById('nombre').value.trim();
        const apellido = document.getElementById('apellido').value.trim();
        const correo = document.getElementById('correo').value.trim();
        const telefono = document.getElementById('telefono').value.trim();
        const pais = document.getElementById('pais').value.trim();
        const direccion = document.getElementById('direccion').value.trim();
        const ciudad = document.getElementById('ciudad').value.trim();
        const estado = document.getElementById('estado').value.trim();
        const codigo_postal = document.getElementById('codigo_postal').value.trim();
        const fecha_nacimiento = document.getElementById('fecha_nacimiento').value;
        const ssn = document.getElementById('ssn').value.trim();
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const rol = rolSelect.value;

        submitBtn.disabled = true;
        submitBtn.textContent = 'Procesando...';

        if (password !== confirmPassword) {
            return showError('Las contraseñas no coinciden');
        }

        // Si el rol es admin, es_premium = 1, si es comisionista, es_premium = 0
        const es_premium = rol === 'admin' ? 1 : 0;

        const userData = {
            nombre, apellido, correo, telefono, pais, direccion, ciudad, estado, codigo_postal, fecha_nacimiento, ssn, password, rol, es_premium
        };

        fetch('http://localhost:8080/usuarios/registrar-admin', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        })
            .then(res => {
                if (!res.ok) return res.text().then(err => { throw new Error(err); });
                return res.json();
            })
            .then(data => {
                successBanner.classList.add('show');
                submitBtn.disabled = true;
                submitBtn.textContent = '¡Registrado!';
                setTimeout(() => {
                    if (rol === 'comisionista') {
                        window.location.href = 'comisionistaDashboard.html';
                    } else {
                        window.location.href = 'login.html';
                    }
                }, 2000);
            })
            .catch(err => showError(err.message || 'Error al registrar'))
            .finally(() => {
                submitBtn.disabled = false;
                updateButtonText();
            });
    });
}); 