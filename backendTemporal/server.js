const express = require('express');
const cors = require('cors');
const path = require('path');
const db = require('./database/db');
const axios = require('axios');

const app = express();
const PORT = process.env.PORT || 5501;
const API_KEY = 'your-alpaca-api-key';
const SECRET_KEY = 'your-alpaca-secret-key';

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, '../public')));

// Rutas básicas
app.post('/api/auth/login', (req, res) => {
    const { username, password } = req.body;
    
    const query = 'SELECT * FROM users WHERE username = ? AND password = ?';
    db.query(query, [username, password], (error, results) => {
        if (error) {
            return res.status(500).json({ success: false, message: 'Error del servidor' });
        }
        
        if (results.length > 0) {
            const user = results[0];
            res.json({
                success: true,
                user: {
                    id: user.id,
                    username: user.username,
                    role: user.role,
                    email: user.email
                }
            });
        } else {
            res.status(401).json({
                success: false,
                message: 'Credenciales inválidas'
            });
        }
    });
});

// Ruta para registro
app.post('/api/auth/register', (req, res) => {
    const { username, password, email, fullName, role } = req.body;
    
    const query = 'INSERT INTO users (username, password, email, full_name, role) VALUES (?, ?, ?, ?, ?)';
    db.query(query, [username, password, email, fullName, role], (error, results) => {
        if (error) {
            if (error.code === 'ER_DUP_ENTRY') {
                return res.status(400).json({ 
                    success: false, 
                    message: 'El usuario o correo ya existe' 
                });
            }
            return res.status(500).json({ 
                success: false, 
                message: 'Error del servidor' 
            });
        }
        
        res.json({
            success: true,
            message: 'Usuario registrado correctamente',
            userId: results.insertId
        });
    });
});

// Ruta para obtener datos de acciones
app.get('/api/stocks', async (req, res) => {
    try {
        const response = await axios.get('https://paper-api.alpaca.markets/v2/assets', {
            headers: {
                'APCA-API-KEY-ID': CK4VN12P03TCB49HSV0N,
                'APCA-API-SECRET-KEY': fG99qo00XZyd5vDOUceZJXGRYvfrOMnwOZdvNciP ,
            },
        });
        res.json(response.data);
    } catch (error) {
        console.error(error);
        res.status(500).send('Error fetching stock data');
    }
});

// Iniciar servidor
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});