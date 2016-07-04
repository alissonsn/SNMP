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
import br.ufrn.model.Predio;
import br.ufrn.model.Rack;
import br.ufrn.model.Sala;
import br.ufrn.model.Switch;
import br.ufrn.model.Unidade;


/** Esta classe tem por finalidade persistir as configurações das portas.
 *
 * @author silas
 *
 */

public class DAOSwitch {
	private Connection conexao;
	boolean teste; //bool;
	boolean[] teste_ind = new boolean[20];
	boolean teste2;


	public DAOSwitch() throws ClassNotFoundException{
		this.conexao = new ConnectionFactory().getConnection();
	}

	public boolean CompararSwitches(Switch switch1, Switch switch2){
		if (switch1.hashCode() == switch2.hashCode() &&
				switch1.getInterfaces().hashCode() == switch2.getInterfaces().hashCode() ) {
			System.out.println("Switch1 hashcode: " + switch1.hashCode());
			System.out.println("Switch2 hashcode: " + switch2.hashCode());
			return true;
		}else{
			return false;
		}

	}


	public String Selecionarid_switch(String tombo){
		ResultSet rs;
		String sql = "select id_switch from switch where serialtombo = '" + tombo + "';";
		String id = "" ;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = rs.getString("id_switch");
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;
	}

	public List<Switch> listarSwitch(){
		List<Switch> listaSwitchs = new ArrayList<Switch>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, "
				+ "nomepavimento, id_andar, nomeandar, id_sala, nomesala, id_rack, nomerack, qtdus, ip, "
				+ "serialtombo, id_switch from switch switch "
				+ "INNER JOIN rack on id_switch_rack = id_rack "				
				+ "INNER JOIN sala on id_rack_sala = id_sala "
				+ "INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio;";
		Statement st;
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
				ativo.setSerialtombo(rs.getString("serialtombo"));
				ativo.setIp(rs.getString("ip"));
				ativo.setMunicipio(municipio);
				ativo.setUnidade(unidade);
				ativo.setPredio(predio);
				ativo.setPavimento(pavimento);
				ativo.setAndar(andar);
				ativo.setSala(sala);
				ativo.setRack(rack);
				listaSwitchs.add(ativo);		
				
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaSwitchs;
	}
	
	public void removerSwitch(String tombo){
		//String tombo = Selecionarid_switch(id_switch);
		String sql = "DELETE from switch where serialtombo = '" + tombo + "';" ;
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