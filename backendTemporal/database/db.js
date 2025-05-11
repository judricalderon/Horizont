const mysql = require('mysql2');
const fs = require('fs');
const path = require('path');

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234', // Configura tu contraseña
 multipleStatements: true, // ✅ esto permite ejecutar varias sentencias
    database: 'acciones_elbosque'
});

// Script para crear la base de datos y tablas
const initDatabase = `
CREATE DATABASE IF NOT EXISTS acciones_elbosque;
USE acciones_elbosque;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'TEACHER', 'STUDENT', 'MONITOR', 'GUEST') NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar usuarios de prueba
INSERT IGNORE INTO users (username, password, email, full_name, role)
VALUES 
    ('admin', '12345', 'admin@elbosque.edu.co', 'Administrador Sistema', 'ADMIN'),
    ('profesor', '12345', 'profesor@elbosque.edu.co', 'Profesor Demo', 'TEACHER'),
    ('estudiante', '12345', 'estudiante@elbosque.edu.co', 'Estudiante Demo', 'STUDENT'),
    ('monitor', '12345', 'monitor@elbosque.edu.co', 'Monitor Demo', 'MONITOR'),
    ('invitado', '12345', 'invitado@elbosque.edu.co', 'Usuario Invitado', 'GUEST');
`;

connection.connect(error => {
    if (error) {
        console.error('Error conectando a MySQL:', error);
        return;
    }
    console.log('Conectado a MySQL correctamente');
    
    // Ejecutar script de inicialización
    connection.query(initDatabase, (err) => {
        if (err) {
            console.error('Error inicializando la base de datos:', err);
            return;
        }
        console.log('Base de datos inicializada correctamente');
        console.log('Usuarios de prueba creados:');
        console.log('- admin/12345 (Administrador)');
        console.log('- profesor/12345 (Profesor)');
        console.log('- estudiante/12345 (Estudiante)');
        console.log('- monitor/12345 (Monitor)');
        console.log('- invitado/12345 (Invitado)');
    });
});

module.exports = connection; 