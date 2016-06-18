DROP TABLE vlan;
DROP TABLE vlan_h;
DROP TABLE modelo;
DROP TABLE interface;
DROP TABLE interface_h;
DROP TABLE switch;
DROP TABLE switch_h;
DROP TABLE data;
DROP TABLE rack;
DROP TABLE sala;
DROP TABLE andar;
DROP TABLE pavimento;
DROP TABLE predio;
DROP TABLE unidade;
DROP TABLE municipio;

CREATE TABLE data(
id_data bigserial UNIQUE,
data varchar(20),
PRIMARY KEY (id_data, data));


CREATE TABLE municipio(
id_municipio bigserial UNIQUE,
nomemunicipio varchar(50),
PRIMARY KEY (id_municipio));

CREATE TABLE unidade(
id_unidade bigserial UNIQUE,
nomeunidade varchar(50),
id_unidade_municipio int,
PRIMARY KEY(id_unidade),
FOREIGN KEY(id_unidade_municipio) REFERENCES municipio(id_municipio));

CREATE TABLE predio(
id_predio bigserial UNIQUE,
nomepredio varchar(50),
id_predio_unidade int,
PRIMARY KEY (id_predio),
FOREIGN KEY(id_predio_unidade) REFERENCES unidade(id_unidade));

CREATE TABLE pavimento(
id_pavimento bigserial UNIQUE,
nomepavimento varchar(50),
id_pavimento_predio int,
PRIMARY KEY (id_pavimento),
FOREIGN KEY(id_pavimento_predio) REFERENCES predio(id_predio));

CREATE TABLE andar(
id_andar bigserial UNIQUE,
nomeandar varchar(30),
id_andar_pavimento int,
PRIMARY KEY (id_andar),
FOREIGN KEY(id_andar_pavimento) REFERENCES pavimento(id_pavimento));

CREATE TABLE sala(
id_sala bigserial UNIQUE,
nomesala varchar(50),
id_sala_andar int,
PRIMARY KEY (id_sala),
FOREIGN KEY(id_sala_andar) REFERENCES andar(id_andar));

CREATE TABLE rack(
id_rack bigserial UNIQUE,
nomerack varchar(50),
qtdUS varchar(4),
id_rack_sala int,
PRIMARY KEY (id_rack),
FOREIGN KEY(id_rack_sala) REFERENCES sala(id_sala));

CREATE TABLE switch(
id_switch bigserial UNIQUE,
ip varchar(44) UNIQUE,
serialtombo varchar(44) UNIQUE,
id_switch_rack int,
PRIMARY KEY (ip, serialtombo),
FOREIGN KEY(id_switch_rack) REFERENCES rack(id_rack));

CREATE TABLE interface
(
id_porta bigserial UNIQUE,
id_switch int,
id_data int,
tipo_vlan varchar(50),
velocidade varchar(50),
estado varchar(50),
ligada varchar(50),
tipo_conector varchar(50),
id_interface_snmp int,
oid_interface_snmp varchar(50),
valor_interface varchar(50),
modificacao varchar(20),
aprova varchar(20),
PRIMARY KEY (id_porta, id_switch),
FOREIGN KEY (id_switch) REFERENCES switch(id_switch),
FOREIGN KEY (id_data) REFERENCES data(id_data));

CREATE TABLE vlan
(
id_porta bigint,
vlan varchar(50),
PRIMARY KEY (id_porta,vlan),
FOREIGN KEY (id_porta) REFERENCES interface(id_porta));

CREATE TABLE switch_h(
id_switch bigserial UNIQUE,
ip varchar(44),
serialtombo varchar(44),
id_switch_rack int,
PRIMARY KEY (ip, serialtombo),
FOREIGN KEY(id_switch_rack) REFERENCES rack(id_rack));

CREATE TABLE interface_h
(
id_porta bigserial UNIQUE,
id_switch int,
id_data int,
tipo_vlan varchar(50),
velocidade varchar(50),
estado varchar(50),
ligada varchar(50),
tipo_conector varchar(50),
id_interface_snmp int,
oid_interface_snmp varchar(50),
valor_interface varchar(50),
modificacao varchar(20),
aprova varchar(20),
PRIMARY KEY (id_porta, id_switch),
FOREIGN KEY (id_data) REFERENCES data(id_data),
FOREIGN KEY (id_switch) REFERENCES switch_h(id_switch));

CREATE TABLE vlan_h
(
id_porta bigint,
vlan varchar(50),
PRIMARY KEY (id_porta,vlan),
FOREIGN KEY (id_porta) REFERENCES interface_h(id_porta));

CREATE TABLE modelo(
id bigserial UNIQUE,
enterprise varchar(50) UNIQUE,
modelo varchar(50),
classeInterface varchar(50),
classeVlan varchar(50),
PRIMARY KEY (enterprise, modelo));

