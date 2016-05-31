DROP TABLE vlan;
DROP TABLE interface;
DROP TABLE data;
DROP TABLE switch;
DROP TABLE vlan_h;
DROP TABLE interface_h;
DROP TABLE data_h;
DROP TABLE switch_h;
DROP TABLE modelo;

CREATE TABLE switch(
id_switch bigserial UNIQUE,
ip varchar(44) UNIQUE,
serialtombo varchar(44) UNIQUE,
PRIMARY KEY (ip, serialtombo));

CREATE TABLE data(
id_data bigserial UNIQUE,
data varchar(20),
id_switch int,
estado varchar(20),
PRIMARY KEY (id_data, data),
FOREIGN KEY (id_switch) REFERENCES switch(id_switch));



CREATE TABLE interface
(
id_porta bigserial UNIQUE,
id_switch int,
tipo_vlan varchar(50),
velocidade varchar(50),
estado varchar(50),
ligada varchar(50),
tipo_conector varchar(50),
id_interface_snmp int,
oid_interface_snmp varchar(50),
valor_interface varchar(50),
PRIMARY KEY (id_porta, id_switch),
FOREIGN KEY (id_switch) REFERENCES switch(id_switch));

CREATE TABLE vlan
(
id_porta bigint,
vlan varchar(50),
PRIMARY KEY (id_porta,vlan),
FOREIGN KEY (id_porta) REFERENCES interface(id_porta));

CREATE TABLE modelo(
enterprise varchar(50) UNIQUE,
modelo varchar(50),
classeInterface varchar(50),
classeVlan varchar(50),
PRIMARY KEY (enterprise));

CREATE TABLE switch_h(
id_switch bigserial UNIQUE,
ip varchar(44),
serialtombo varchar(44),
PRIMARY KEY (id_switch,ip, serialtombo));


CREATE TABLE data_h(
id_data bigserial UNIQUE,
data varchar(20),
id_switch int,
estado varchar(20),
PRIMARY KEY (id_data, data),
FOREIGN KEY (id_switch) REFERENCES switch_h(id_switch));

CREATE TABLE interface_h
(
id_porta bigserial UNIQUE,
id_switch int,
tipo_vlan varchar(50),
velocidade varchar(50),
estado varchar(50),
ligada varchar(50),
tipo_conector varchar(50),
id_interface_snmp int,
oid_interface_snmp varchar(50),
valor_interface varchar(50),
PRIMARY KEY (id_porta, id_switch),
FOREIGN KEY (id_switch) REFERENCES switch_h(id_switch));

CREATE TABLE vlan_h
(
id_porta bigint,
vlan varchar(50),
PRIMARY KEY (id_porta,vlan),
FOREIGN KEY (id_porta) REFERENCES interface_h(id_porta));

