DROP DATABASE credibanco;

CREATE DATABASE credibanco;

USE credibanco;

CREATE TABLE bank_card (
  pan VARCHAR(19) PRIMARY KEY,
  owner_card VARCHAR(50),
  id VARCHAR(15),
  number_phone VARCHAR(10),
  status_card VARCHAR(10),
  type_card VARCHAR(10),
  enrollment_number INT
);

CREATE TABLE purchase (
  reference_number VARCHAR(6) PRIMARY KEY,
  pan VARCHAR(19),
  purchase_value BIGINT,
  purchase_address VARCHAR(50),
  status_buy VARCHAR(10),
  time_purchase DATETIME,
  FOREIGN KEY (pan) REFERENCES bank_card(pan)
);

INSERT INTO bank_card (type_card, pan, enrollment_number, id, number_phone, owner_card, status_card) 
VALUES ('debito', '1000000000000001', 77, '0000000000', '0000000000', 'Usuario prueba', 'creada');

INSERT INTO purchase (reference_number, pan, purchase_value, purchase_address,time_purchase, status_buy) 
VALUES ('100000', '1000000000000001', 750000, 'Calle Falsa #123','2023-02-25T15:10:58.629327', 'aprobada');




