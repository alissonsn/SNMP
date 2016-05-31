
//Selecionar Porta
select id_porta from interface where valor_interface = 'GigabitEthernet1/0/1' and id_switch = 1;

//Selecionar Porta do switch 1
select vlan from interface,vlan where valor_interface = 'GigabitEthernet1/0/1' and id_switch = 1 and vlan.id_porta = interface.id_porta;

//Selecionar id_porta de switch 2
select id_porta from interface where id_switch = 2 and id_interface_snmp =2;

//Selecionar Todas as vlans do switch 1
select vlan from interface,vlan where id_switch = 1 and vlan.id_porta = interface.id_porta;

//Selecionar Todas as configurações de todos os switch
SELECT interface.id_porta, interface.id_switch, interface.tipo_vlan, interface.id_interface_snmp, interface.oid_interface_snmp, interface.valor_interface, vlan.vlan FROM interface INNER JOIN vlan on   interface.id_porta = vlan.id_porta;


//Selecionar Todas as configurações do switch
SELECT interface.id_porta, interface.id_switch, interface.tipo_vlan, interface.id_interface_snmp, interface.oid_interface_snmp, interface.valor_interface, vlan.vlan FROM interface INNER JOIN vlan on   interface.id_porta = vlan.id_porta WHERE id_switch = 2;

select equipe.nome from equipe INNER JOIN responsavel on responsavel.id_equipe = equipe.id_equipe and responsavel.nome = 'Maneo';


 update responsavel set nome = 'Joao Batista' where matricula = '2249504';


select distinct vlan from vlan;


select distinct vlan from vlan INNER JOIN interface on vlan.id_porta = interface.id_porta and interface.id_switch = '1';

select estado from interface where id_switch = '1' and id_porta = '25';


select * from vlan INNER JOIN interface on vlan.id_porta = interface.id_porta and interface.id_switch = '1';

select id_data from data where data = 'data_string';

delete from switch where serialtombo = '1';


delete from vlan 
	where id_porta IN (select id_porta from interface 
	where id_switch IN (select id_switch from switch where serialtombo = '1'));

select id_porta from vlan_h
	where id_porta IN (select id_porta from interface_h 
	where id_switch IN (select id_switch from switch_h where serialtombo = '1'));

//Selecionar ultima ocorrencia de tal switch
 select max(id_data) from interface_h where id_switch = '1';






