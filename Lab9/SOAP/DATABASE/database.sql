-- Crear base de datos
CREATE DATABASE IF NOT EXISTS EmpresaDB;
USE EmpresaDB;

-- Tabla de Departamentos
CREATE TABLE IF NOT EXISTS Departamentos (
    IDDpto INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL UNIQUE,
    Telefono VARCHAR(15),
    Fax VARCHAR(15)
) ENGINE=InnoDB;

-- Tabla de Ingenieros
CREATE TABLE IF NOT EXISTS Ingenieros (
    IDIng INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Especialidad VARCHAR(100),
    Cargo VARCHAR(50)
) ENGINE=InnoDB;

-- Tabla de Proyectos
CREATE TABLE IF NOT EXISTS Proyectos (
    IDProy INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100) NOT NULL,
    Fec_Inicio DATE NOT NULL,
    Fec_Termino DATE NOT NULL,
    IDIng INT NOT NULL,
    IDDpto INT,

    FOREIGN KEY (IDIng) REFERENCES Ingenieros(IDIng) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (IDDpto) REFERENCES Departamentos(IDDpto) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Índices secundarios
-- No se puede usar IF NOT EXISTS, se recomienda usar TRY/CATCH en procedimientos o verificar previamente en `information_schema`
CREATE INDEX idx_especialidad ON Ingenieros(Especialidad);
CREATE INDEX idx_fec_inicio ON Proyectos(Fec_Inicio);

-- Procedimiento almacenado
DROP PROCEDURE IF EXISTS InsertarProyecto;

DELIMITER //
CREATE PROCEDURE InsertarProyecto (
    IN pNombre VARCHAR(100),
    IN pFecInicio DATE,
    IN pFecTermino DATE,
    IN pIDIng INT,
    IN pIDDpto INT
)
BEGIN
    DECLARE existeIng INT;
    DECLARE existeDpto INT;

    SELECT COUNT(*) INTO existeIng FROM Ingenieros WHERE IDIng = pIDIng;
    SELECT COUNT(*) INTO existeDpto FROM Departamentos WHERE IDDpto = pIDDpto;

    IF existeIng = 1 AND existeDpto = 1 THEN
        INSERT INTO Proyectos (Nombre, Fec_Inicio, Fec_Termino, IDIng, IDDpto)
        VALUES (pNombre, pFecInicio, pFecTermino, pIDIng, pIDDpto);
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ingeniero o Departamento no válido';
    END IF;
END;
//
DELIMITER ;

-- Restricción de fechas
-- MySQL no permite IF NOT EXISTS aquí. Verificar manualmente antes si existe.
ALTER TABLE Proyectos
    ADD CONSTRAINT chk_fechas CHECK (Fec_Termino > Fec_Inicio);

-- Datos de prueba
INSERT INTO Departamentos (Nombre, Telefono, Fax) VALUES
('Ingeniería de Software', '555-1234', '555-5678'),
('Infraestructura', '555-2345', '555-6789'),
('Recursos Humanos', '555-3456', '555-7890');

INSERT INTO Ingenieros (Nombre, Especialidad, Cargo) VALUES
('Ana Torres', 'Backend', 'Líder Técnico'),
('Carlos Mendoza', 'Base de Datos', 'Analista'),
('Lucía Rivas', 'DevOps', 'Administrador de Sistemas');

INSERT INTO Proyectos (Nombre, Fec_Inicio, Fec_Termino, IDIng, IDDpto) VALUES
('Sistema de Gestión de Clientes', '2024-01-10', '2024-06-10', 1, 1),
('Migración de Servidores', '2024-03-15', '2024-09-01', 3, 2),
('Automatización de Nóminas', '2024-02-01', '2024-05-30', 2, 3);
