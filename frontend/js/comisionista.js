document.addEventListener('DOMContentLoaded', () => {
    // Inicializar navegación
    initializeNavigation();
    // Cargar datos iniciales
    loadInitialData();
    // Inicializar gráficos
    initializeCharts();
});

// Navegación entre secciones
function initializeNavigation() {
    const navItems = document.querySelectorAll('.nav-item');
    const sections = document.querySelectorAll('.content-section');

    navItems.forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            const targetId = item.getAttribute('href').substring(1);
            
            // Actualizar navegación
            navItems.forEach(nav => nav.classList.remove('active'));
            item.classList.add('active');
            
            // Mostrar sección correspondiente
            sections.forEach(section => {
                section.classList.remove('active');
                if (section.id === targetId) {
                    section.classList.add('active');
                }
            });
        });
    });
}

// Cargar datos iniciales
async function loadInitialData() {
    try {
        // Cargar datos del comisionista
        const comisionistaData = await fetchComisionistaData();
        updateComisionistaProfile(comisionistaData);
        
        // Cargar estadísticas
        const stats = await fetchStats();
        updateStats(stats);
        
        // Cargar últimas operaciones
        const operaciones = await fetchUltimasOperaciones();
        updateUltimasOperaciones(operaciones);
        
        // Cargar lista de clientes
        const clientes = await fetchClientes();
        updateClientesList(clientes);
        
    } catch (error) {
        console.error('Error al cargar datos iniciales:', error);
        showError('Error al cargar los datos. Por favor, intente nuevamente.');
    }
}

// Funciones de actualización de UI
function updateComisionistaProfile(data) {
    document.getElementById('nombre-comisionista').textContent = `${data.nombre} ${data.apellido}`;
}

function updateStats(stats) {
    document.getElementById('total-clientes').textContent = stats.totalClientes;
    document.getElementById('total-operaciones').textContent = stats.totalOperaciones;
}

function updateUltimasOperaciones(operaciones) {
    const tbody = document.getElementById('ultimas-operaciones');
    tbody.innerHTML = operaciones.map(op => `
        <tr>
            <td>${op.cliente}</td>
            <td>${op.tipo}</td>
            <td>$${op.monto.toFixed(2)}</td>
            <td><span class="status-badge ${op.estado.toLowerCase()}">${op.estado}</span></td>
        </tr>
    `).join('');
}

function updateClientesList(clientes) {
    const tbody = document.getElementById('clientes-lista');
    tbody.innerHTML = clientes.map(cliente => `
        <tr>
            <td>${cliente.nombre} ${cliente.apellido}</td>
            <td>${cliente.email}</td>
            <td><span class="status-badge ${cliente.estado.toLowerCase()}">${cliente.estado}</span></td>
            <td>$${cliente.portafolio.toFixed(2)}</td>
            <td>
                <button class="btn-primary btn-sm" onclick="verDetalleCliente(${cliente.id})">
                    <span class="material-icons">visibility</span>
                </button>
            </td>
        </tr>
    `).join('');
}

// Inicialización de gráficos
function initializeCharts() {
    // Gráfico de rendimiento
    const rendimientoCtx = document.getElementById('rendimientoChart').getContext('2d');
    new Chart(rendimientoCtx, {
        type: 'line',
        data: {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
            datasets: [{
                label: 'Rendimiento',
                data: [0, 10, 5, 15, 10, 20],
                borderColor: '#750014',
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    // Gráfico de clientes
    const clientesCtx = document.getElementById('clientesChart').getContext('2d');
    new Chart(clientesCtx, {
        type: 'doughnut',
        data: {
            labels: ['Activos', 'Inactivos', 'Nuevos'],
            datasets: [{
                data: [65, 20, 15],
                backgroundColor: ['#750014', '#8b959e', '#ff1423']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

// Funciones de búsqueda y filtrado
document.getElementById('searchClient')?.addEventListener('input', debounce(async (e) => {
    const searchTerm = e.target.value;
    try {
        const clientes = await searchClientes(searchTerm);
        updateClientesList(clientes);
    } catch (error) {
        console.error('Error en la búsqueda:', error);
    }
}, 300));

document.getElementById('filterStatus')?.addEventListener('change', async (e) => {
    const status = e.target.value;
    try {
        const operaciones = await filterOperaciones(status);
        updateOperacionesList(operaciones);
    } catch (error) {
        console.error('Error al filtrar operaciones:', error);
    }
});

// Funciones de utilidad
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

function showError(message) {
    // Implementar lógica para mostrar mensajes de error
    console.error(message);
}

// Funciones de API (simuladas)
async function fetchComisionistaData() {
    // Simular llamada a API
    return {
        nombre: 'Juan',
        apellido: 'Pérez',
        email: 'juan.perez@example.com'
    };
}

async function fetchStats() {
    // Simular llamada a API
    return {
        totalClientes: 25,
        totalOperaciones: 150
    };
}

async function fetchUltimasOperaciones() {
    // Simular llamada a API
    return [
        { cliente: 'María García', tipo: 'Compra', monto: 1500, estado: 'COMPLETADA' },
        { cliente: 'Carlos López', tipo: 'Venta', monto: 2300, estado: 'PENDIENTE' }
    ];
}

async function fetchClientes() {
    // Simular llamada a API
    return [
        { id: 1, nombre: 'María', apellido: 'García', email: 'maria@example.com', estado: 'ACTIVO', portafolio: 15000 },
        { id: 2, nombre: 'Carlos', apellido: 'López', email: 'carlos@example.com', estado: 'INACTIVO', portafolio: 8000 }
    ];
}

async function searchClientes(term) {
    // Simular búsqueda
    const clientes = await fetchClientes();
    return clientes.filter(c => 
        c.nombre.toLowerCase().includes(term.toLowerCase()) ||
        c.apellido.toLowerCase().includes(term.toLowerCase())
    );
}

async function filterOperaciones(status) {
    // Simular filtrado
    const operaciones = await fetchUltimasOperaciones();
    return status === 'all' ? operaciones : operaciones.filter(op => op.estado === status);
}

// Función para generar reportes
async function generarReporte() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    if (!startDate || !endDate) {
        showError('Por favor seleccione un rango de fechas');
        return;
    }
    
    try {
        // Aquí iría la lógica para generar y descargar el reporte
        console.log('Generando reporte...', { startDate, endDate });
    } catch (error) {
        console.error('Error al generar reporte:', error);
        showError('Error al generar el reporte');
    }
}

// Función para ver detalle de cliente
function verDetalleCliente(clienteId) {
    // Implementar lógica para mostrar detalles del cliente
    console.log('Ver detalle del cliente:', clienteId);
} 