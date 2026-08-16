-- Creacion de la base de datos
CREATE DATABASE bd_streaming_musical;

-- Activacion de una cierta base de datos
USE bd_streaming_musical;

-- Creacion de las tablas

-- Creacion de la tabla de administradores
CREATE TABLE t_administradores(
    id INT PRIMARY KEY AUTO_INCREMENT,
    correo_electronico VARCHAR(100),
    nombre_usuario VARCHAR(50),
    contrasenia VARCHAR(100)
);


-- Creacion de la tabla de usuarios finales
CREATE TABLE t_usuarios(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(100),
    fecha_nacimiento DATE,
    nacionalidad VARCHAR(50),
    cedula VARCHAR(20),
    avatar VARCHAR(200),
    saldo FLOAT,
    correo_electronico VARCHAR(100),
    nombre_usuario VARCHAR(50),
    contrasenia VARCHAR(100)
);


-- Creacion de la tabla de canciones
CREATE TABLE t_canciones(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    genero VARCHAR(50),
    fecha_lanzamiento DATE,
    precio FLOAT,
    artista VARCHAR(100),
    compositor VARCHAR(100),
    nombre_album VARCHAR(100),
    caratula_album VARCHAR(200)
);


-- Creacion de la tabla de compras
CREATE TABLE t_compras(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_cancion INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES t_usuarios(id),
    FOREIGN KEY (id_cancion) REFERENCES t_canciones(id)
);


-- Creacion de la tabla de calificaciones
CREATE TABLE t_calificaciones(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_cancion INT NOT NULL,
    calificacion FLOAT,
    FOREIGN KEY (id_usuario) REFERENCES t_usuarios(id),
    FOREIGN KEY (id_cancion) REFERENCES t_canciones(id)
);


-- Creacion de la tabla de listas de reproduccion
CREATE TABLE t_listas_reproduccion(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    nombre VARCHAR(100),
    fecha_creacion DATE,
    FOREIGN KEY (id_usuario) REFERENCES t_usuarios(id)
);


-- Creacion de la tabla intermedia entre listas y canciones
CREATE TABLE t_canciones_listas(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_lista INT NOT NULL,
    id_cancion INT NOT NULL,
    FOREIGN KEY (id_lista) REFERENCES t_listas_reproduccion(id),
    FOREIGN KEY (id_cancion) REFERENCES t_canciones(id)
);
