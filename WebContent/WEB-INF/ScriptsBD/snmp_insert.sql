INSERT INTO municipio (nomemunicipio) VALUES ('Natal');
INSERT INTO unidade (nomeunidade,id_unidade_municipio) VALUES ('Central', 1);
INSERT INTO predio (nomepredio,id_predio_unidade) VALUES ('SINFO', 1);
INSERT INTO pavimento (nomepavimento,id_pavimento_predio) VALUES ('SEDE', 1);
INSERT INTO andar (nomeandar, id_andar_pavimento) VALUES ('Terreo',1):
INSERT INTO sala (nomesala, id_sala_andar) VALUES ('DATACENTER', 1);
INSERT INTO rack (nomerack, qtdUS, id_rack_sala) VALUES ('DIMAP',20, 1);

INSERT INTO modelo (enterprise, modelo,classeInterface, classeVlan) VALUES ('25506', '5500 H3C', 'PortaH3CService', 'VlanH3CService');



