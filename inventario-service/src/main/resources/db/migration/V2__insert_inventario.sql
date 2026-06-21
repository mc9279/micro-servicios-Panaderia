INSERT INTO inventario
(nombre_ingrediente,stock,unidad_medida,
 stock_minimo,fecha_actualizacion,
 producto_id,panadero_id)
VALUES

    ('Harina',50,'kg',10,CURDATE(),1,1),
    ('Levadura',15,'kg',5,CURDATE(),2,1),
    ('Azúcar',20,'kg',8,CURDATE(),3,2),
    ('Mantequilla',12,'kg',4,CURDATE(),1,2);