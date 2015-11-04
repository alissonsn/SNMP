DROP TABLE modelo;
DROP TABLE vlan;
DROP TABLE interface;
DROP TABLE switch;

CREATE TABLE switch(
id_switch int UNIQUE,
PRIMARY KEY (id_switch));


CREATE TABLE interface
(
id_porta serial UNIQUE,
id_switch int,
tipo_vlan varchar(50),
velocidade varchar(50),
estado varchar(50),
tipo_conector varchar(50),
id_interface_snmp int,
oid_interface_snmp varchar(50),
valor_interface varchar(50),
PRIMARY KEY (id_porta, id_switch),
FOREIGN KEY (id_switch) REFERENCES switch(id_switch));

CREATE TABLE vlan
(
id_porta int,
vlan varchar(50),
PRIMARY KEY (id_porta,vlan),
FOREIGN KEY (id_porta) REFERENCES interface(id_porta));


CREATE TABLE modelo(
enterprise varchar(50) UNIQUE,
modelo varchar(50),
classeInterface varchar(50),
classeVlan varchar(50),
PRIMARY KEY (enterprise));




