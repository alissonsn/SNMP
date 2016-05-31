


//Listar todos os Predios
select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio 
from predio predio INNER JOIN unidade on id_predio_unidade = id_unidade INNER JOIN municipio on id_municipio = id_unidade_municipio;


//Listar todos os pavimentos 
select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento 
from pavimento pavimento INNER JOIN predio on id_predio = id_pavimento_predio INNER JOIN unidade on id_predio_unidade = id_unidade INNER JOIN municipio on id_municipio = id_unidade_municipio;


//Listar todas as salas
select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento, id_sala, nomesala from sala sala INNER JOIN pavimento on id_sala_pavimento = id_pavimento INNER JOIN predio on id_predio = id_pavimento_predio INNER JOIN unidade on id_predio_unidade = id_unidade INNER JOIN municipio on id_municipio = id_unidade_municipio;



