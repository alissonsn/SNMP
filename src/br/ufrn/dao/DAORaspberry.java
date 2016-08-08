package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Andar;
import br.ufrn.model.Municipio;
import br.ufrn.model.Pavimento;
import br.ufrn.model.Porta;
import br.ufrn.model.Predio;
import br.ufrn.model.Rack;
import br.ufrn.model.Raspberry;
import br.ufrn.model.Sala;
import br.ufrn.model.Switch;
import br.ufrn.model.Unidade;
import br.ufrn.model.VlanSW;

public class DAORaspberry {
	private Connection conexao;
	private Porta interfaces;
	
	public DAORaspberry(){
		this.conexao = new ConnectionFactory().getConnection();
	}
	
	public void adicionarRaspbery(Raspberry raspberry){
		String sql = "INSERT INTO raspberry (id_raspberry, id_raspberry_switch,interface_raspberry) VALUES (?,?,?)";
		System.out.println("SQL: " + sql);
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			System.out.println("Id raspberry: " + raspberry.getId_raspberry());
			System.out.println("Id switch: " + raspberry.getComutador().getId_switch());
			ps.setLong(1, raspberry.getId_raspberry());
			ps.setLong(2, raspberry.getComutador().getId_switch());
			ps.setString(3, raspberry.getInterface_raspberry());
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Raspberry> listarRaspberries(){
		List<Raspberry> listaRaspberry = new ArrayList<Raspberry>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, "
				+ "nomepavimento, id_andar, nomeandar, id_sala, nomesala, id_rack, nomerack, qtdus, posicao_rack, ip, "
				+ "serialtombo, id_switch, id_raspberry, interface_raspberry from raspberry raspberry "
				+ "INNER JOIN switch on id_raspberry_switch = id_switch "
				+ "INNER JOIN rack on id_switch_rack = id_rack "				
				+ "INNER JOIN sala on id_rack_sala = id_sala "
				+ "INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio;";
		Statement st;
		System.out.println(sql);
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				
				Municipio municipio = new Municipio();
				int id_municipio = Integer.parseInt(rs.getString("id_municipio")); 
				municipio.setId(id_municipio);
				municipio.setNome(rs.getString("nomemunicipio"));
				
				Unidade unidade = new Unidade();
				int id_unidade= Integer.parseInt(rs.getString("id_unidade"));
				unidade.setId(id_unidade);
				unidade.setNome(rs.getString("nomeunidade"));
					
				Predio predio = new Predio();
				int id_predio = Integer.parseInt(rs.getString("id_predio"));
				predio.setId(id_predio);
				predio.setNome(rs.getString("nomepredio"));
			
				Pavimento pavimento = new Pavimento();
				int id_pavimento = Integer.parseInt(rs.getString("id_pavimento"));
				pavimento.setId(id_pavimento);
				pavimento.setNome(rs.getString("nomepavimento"));
				
				Andar andar = new Andar();
				int id_andar = Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				andar.setNome(rs.getString("nomeandar"));
				
				Sala sala = new Sala();
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				sala.setNome(rs.getString("nomesala"));
				
				Rack rack = new Rack();
				int id_rack = Integer.parseInt(rs.getString("id_rack"));
				rack.setId(id_rack);
				rack.setNome(rs.getString("nomerack"));
				rack.setQtdUS(rs.getString("qtdus"));
				
				
				Switch ativo = new Switch();
				int id_switch = Integer.parseInt(rs.getString("id_switch"));
				ativo.setId_switch(id_switch);
				ativo.setPosicaoRack(rs.getString("posicao_rack"));
				ativo.setSerialtombo(rs.getString("serialtombo"));
				ativo.setIp(rs.getString("ip"));
				
				Raspberry raspberry = new Raspberry();
				int id_raspberry = Integer.parseInt(rs.getString("id_raspberry"));
				
				raspberry.setId_raspberry(id_raspberry);
				raspberry.setInterface_raspberry(rs.getString("interface_raspberry"));
				raspberry.setMunicipio(municipio);
				raspberry.setUnidade(unidade);
				raspberry.setPredio(predio);
				raspberry.setPavimento(pavimento);
				raspberry.setAndar(andar);
				raspberry.setSala(sala);
				raspberry.setRack(rack);
				raspberry.setComutador(ativo);
				listaRaspberry.add(raspberry);
				
				
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaRaspberry;
	}
	
	//Listar Todas as configurações dos raspberries
	public Raspberry listarRaspberriesSwitch(String codigo_raspberry){
		Object id_interface = new Object();
		
		ArrayList<VlanSW> vlan = new ArrayList<VlanSW>();
		ArrayList<Porta> interfacess = new ArrayList<Porta>();
		Switch comutador = new Switch();
		Raspberry raspberry = new Raspberry();
		
		ResultSet rs;
		String sql = "select distinct id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento, "
				+ "id_andar, nomeandar, id_sala, nomesala, id_rack, nomerack, qtdus, posicao_rack, ip, serialtombo, id_switch, vlan, id_raspberry, interface_raspberry "
				+ "from raspberry raspberry INNER JOIN switch on id_raspberry_switch = id_switch "
				+ "INNER JOIN rack on id_switch_rack = id_rack "
				+ "INNER JOIN sala on id_rack_sala = id_sala "
				+ "INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio "
				+ "INNER JOIN interface interface on switch.id_switch = interface.id_interface_switch "
				+ "INNER JOIN vlansw vlansw on vlansw.id_porta = interface.id_porta "
				+ "where id_raspberry = '"+ codigo_raspberry  + "';";
		System.out.println("Imprindo sql Atual " +sql);
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			Object id_porta_anterior = -1;
			while(rs.next()){
				Municipio municipio = new Municipio();
				int id_municipio = Integer.parseInt(rs.getString("id_municipio")); 
				municipio.setId(id_municipio);
				municipio.setNome(rs.getString("nomemunicipio"));
				
				Unidade unidade = new Unidade();
				int id_unidade= Integer.parseInt(rs.getString("id_unidade"));
				unidade.setId(id_unidade);
				unidade.setNome(rs.getString("nomeunidade"));
					
				Predio predio = new Predio();
				int id_predio = Integer.parseInt(rs.getString("id_predio"));
				predio.setId(id_predio);
				predio.setNome(rs.getString("nomepredio"));
			
				Pavimento pavimento = new Pavimento();
				int id_pavimento = Integer.parseInt(rs.getString("id_pavimento"));
				pavimento.setId(id_pavimento);
				pavimento.setNome(rs.getString("nomepavimento"));
				
				Andar andar = new Andar();
				int id_andar = Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				andar.setNome(rs.getString("nomeandar"));
				
				Sala sala = new Sala();
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				sala.setNome(rs.getString("nomesala"));
				
				Rack rack = new Rack();
				int id_rack = Integer.parseInt(rs.getString("id_rack"));
				rack.setId(id_rack);
				rack.setNome(rs.getString("nomerack"));
				rack.setQtdUS(rs.getString("qtdus"));				
				
				
				int id_raspberry = Integer.parseInt(rs.getString("id_raspberry"));
				raspberry.setId_raspberry(id_raspberry);
				raspberry.setInterface_raspberry(rs.getString("interface_raspberry"));
				raspberry.setMunicipio(municipio);
				raspberry.setUnidade(unidade);
				raspberry.setPredio(predio);
				raspberry.setPavimento(pavimento);
				raspberry.setAndar(andar);
				raspberry.setSala(sala);
				raspberry.setRack(rack);
				VlanSW objVlan = new VlanSW();
				if(id_interface.equals(id_porta_anterior)){
					objVlan.setVlan(rs.getString("vlan"));
					vlan.add(objVlan);
				}else{
					vlan = new ArrayList<VlanSW>();
					interfaces = new Porta();
					objVlan.setVlan(rs.getString("vlan"));
					vlan.add(objVlan);
					int id_switch = Integer.parseInt(rs.getString("id_switch"));
					interfaces.setVlan(vlan);
					interfacess.add(interfaces);
					comutador.setId_switch(id_switch);
					comutador.setPosicaoRack(rs.getString("posicao_rack"));
					comutador.setSerialtombo(rs.getString("serialtombo"));
					comutador.setIp(rs.getString("ip"));
					comutador.setInterfaces(interfacess);
					id_porta_anterior = id_interface;
				}
				raspberry.setComutador(comutador);
				
			}
			//config.add(comutador);
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return raspberry;
	}	
	
	public void atualizarRaspberry(Raspberry raspberry){
		String sql = "UPDATE raspberry set id_raspberry ='"+ raspberry.getId_raspberry() + "' "
				+ "where id_raspberry = '" + raspberry.getId_raspberry() + "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
	public void deletarRaspberry(int id){
		String sql = "DELETE FROM raspberry where id_raspberry = '" + id+ "';";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
}