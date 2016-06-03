package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Municipio;
import br.ufrn.model.Pavimento;
import br.ufrn.model.Predio;
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
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento, "
				+ "id_sala, nomesala, ip, serialtombo, id_switch from switch INNER JOIN  sala on id_switch_sala = id_sala "
				+ "INNER JOIN pavimento on id_sala_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio INNER JOIN unidade on id_predio_unidade = id_unidade "
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
			
				int id_pavimento = Integer.parseInt(rs.getString("id_pavimento"));
				Pavimento pavimento = new Pavimento();
				pavimento.setId(id_pavimento);
				pavimento.setNome(rs.getString("nomepavimento"));
				
				Sala sala = new Sala();
				sala.setNome(rs.getString("nomesala"));
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				
				
				Switch ativo = new Switch();
				int id_switch = Integer.parseInt(rs.getString("id_switch"));
				ativo.setId_switch(id_switch);
				ativo.setSerialtombo(rs.getString("serialtombo"));
				ativo.setIp(rs.getString("ip"));
				ativo.setMunicipio(municipio);
				ativo.setUnidade(unidade);
				ativo.setPredio(predio);
				ativo.setPavimento(pavimento);
				ativo.setSala(sala);
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