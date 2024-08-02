-- sin region
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
-- INSERT INTO clientes (nombre, apellido, email, create_at) VALUES('Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');



-- Regiones
INSERT INTO regiones (id,nombre) VALUES (1, 'Sudamerica');
INSERT INTO regiones (id,nombre) VALUES (2, 'Centroamerica');
INSERT INTO regiones (id,nombre) VALUES (3, 'Norteameriva');
INSERT INTO regiones (id,nombre) VALUES (4, 'Europa');
INSERT INTO regiones (id,nombre) VALUES (5, 'Asia');
INSERT INTO regiones (id,nombre) VALUES (6, 'Africa');
INSERT INTO regiones (id,nombre) VALUES (7, 'Oceania');
INSERT INTO regiones (id,nombre) VALUES (8, 'Antartida');



-- Populate tabla clientes
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(1,'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(4,'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(3, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(5, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(6, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (region_id, nombre, apellido, email, create_at) VALUES(7, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');

-- productos
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'Panasonic Pantalla LCD', 259990);
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'sony Camara digital DSC-W320B', 123490);
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'Apple iPod shuffle', 149.00);
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'sony Notebook Z110', 37990);
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'Hewlet Packard Multifuncional F228', 259990);
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'Bianchi Bicicleta Aro 26', 69990);
insert into productos(create_at, nombre, precio) VALUES (NOW(), 'Mica Comoda 5 Cajones', 259990);

-- facturas
insert into facturas(descripcion, observacion, cliente_id, create_at) VALUES ('Factura equipo de oficina', null, 1, NOW());
insert into facturas_items(cantidad, factura_id, producto_id) value (1, 1, 1);
insert into facturas_items(cantidad, factura_id, producto_id) value (2, 1, 4);
insert into facturas_items(cantidad, factura_id, producto_id) value (1, 1, 5);
insert into facturas_items(cantidad, factura_id, producto_id) value (1, 1, 7);

insert into facturas(descripcion, observacion, cliente_id, create_at) VALUES ('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
insert into facturas_items(cantidad, factura_id, producto_id) value (3, 2, 6);
