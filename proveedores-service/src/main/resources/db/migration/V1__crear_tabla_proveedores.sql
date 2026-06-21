CREATE TABLE proveedores (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nombre_empresa VARCHAR(100) NOT NULL,
                             rut VARCHAR(12) NOT NULL UNIQUE,
                             telefono VARCHAR(15) NOT NULL,
                             email VARCHAR(100) NOT NULL UNIQUE,
                             direccion VARCHAR(200) NOT NULL
);