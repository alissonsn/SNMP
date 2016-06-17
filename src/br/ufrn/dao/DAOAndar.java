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
import br.ufrn.model.Sala;
import br.ufrn.model.Unidade;

public class DAOAndar {
	private Connection conexao;

	public DAOAndar(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarAndar(Andar andar){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO andar (nomeandar, id_andar_pavimento) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, andar.getNome());
			ps.setLong(2, andar.getPavimento().getId());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Andar> listarAndares(){
		List<Andar> andares = new ArrayList<Andar>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento, "
				+ "id_andar, nomeandar from andar andar INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
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
				
				Andar andar = new Andar();
				andar.setNome(rs.getString("nomeandar"));
				int id_andar = Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				andar.setMunicipio(municipio);
				andar.setUnidade(unidade);
				andar.setPredio(predio);
				andar.setPavimento(pavimento);
				andares.add(andar);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return andares;
	}

	public List<Andar> listarAndaresPavimento(String codigo){
		List<Andar> andares = new ArrayList<Andar>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento, "
				+ "id_andar, nomeandar from andar andar INNER JOIN pavimento on id_sala_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio "
				+ " where id_pavimento = '" + codigo + "';";
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
				
				Andar andar = new Andar();
				andar.setNome(rs.getString("nomeandar"));
				int id_andar = Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				andar.setMunicipio(municipio);
				andar.setUnidade(unidade);
				andar.setPredio(predio);
				andar.setPavimento(pavimento);
				andares.add(andar);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return andares;
	}
	
	public Andar listarAndar(String codigo){
		Andar andar = new Andar();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, nomepavimento, "
				+ "id_andar, nomeandar from andar andar INNER JOIN pavimento on id_sala_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio "
				+ " where id_pavimento = '" + codigo + "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				andar.setNome(rs.getString("nomeandar"));
				int id = Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return andar;
	}


	public void atualizarAndar(Andar andar){
		String sql = "UPDATE andar set nomeandar ='"+ andar.getNome() 	+ "' "
				+ "where id_andar = '" + andar.getId()+ "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarAndar(int id){
		String sql = "DELETE FROM andar where id_andar = '" + id+ "';";
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