const express = require('express');
const cors = require('cors');
const path = require('path');
const db = require('./database/db');

const app = express();
const PORT = process.env.PORT || 5501;

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

// Iniciar servidor
app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
}); 