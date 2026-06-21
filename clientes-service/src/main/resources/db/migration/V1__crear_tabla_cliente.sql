CREATE TABLE cliente(
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        nombre VARCHAR(20) NOT NULL,
                        apellido VARCHAR(20) NOT NULL,
                        rut VARCHAR(20) NOT NULL,
                        email VARCHAR(255),
                        telefono VARCHAR(255),
                        direccion VARCHAR(255)
);