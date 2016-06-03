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
import br.ufrn.model.Predio;
import br.ufrn.model.Unidade;

public class DAOPredio {
	private Connection conexao;

	public DAOPredio(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarPredio(Predio predio){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO predio (nomepredio,id_predio_unidade) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, predio.getNome());
			ps.setLong(2, predio.getUnidade().getId());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Predio> listarPredios(){
		List<Predio> predios = new ArrayList<Predio>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, "
				+ "id_predio, nomepredio from predio predio "
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
				predio.setMunicipio(municipio);
				predio.setUnidade(unidade);
				predios.add(predio);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predios;
	}

	public Predio listarPredio(String codigo){
		Predio predio = new Predio();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, "
				+ "id_predio, nomepredio from predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio "
				+ "where id_predio = '"+ codigo+ "';";
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
				
				
				
				int id_predio = Integer.parseInt(rs.getString("id_predio"));
				predio.setId(id_predio);
				predio.setNome(rs.getString("nomepredio"));
				predio.setMunicipio(municipio);
				predio.setUnidade(unidade);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predio;
	}

	public List<Predio> listarPredioUnidade(String codigo){
		List<Predio> predios = new ArrayList<Predio>();
		ResultSet rs;
		String sql = "select nomepredio,id_predio,id_predio_unidade, "
				+ "nomeunidade,id_unidade from unidade unidade "
				+ "INNER JOIN predio predio on id_unidade = id_predio_unidade "
				+ "where id_predio_unidade = '"+ codigo+ "';";
		java.sql.Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Predio predio = new Predio();
				int id = Integer.parseInt(rs.getString("id_predio"));
				predio.setId(id);
				predio.setNome(rs.getString("nomepredio"));
				int id_unidade = Integer.parseInt(rs.getString("id_predio_unidade"));
				Unidade unidade = new Unidade();
				unidade.setId(id_unidade);
				unidade.setNome(rs.getString("nomeunidade"));
				predio.setUnidade(unidade);
				predios.add(predio);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predios;
	}
	
	public void atualizarPredio(Predio predio){
		String sql = "UPDATE predio set nomepredio ='"+ predio.getNome() +
				"' , id_predio_unidade = '" + predio.getUnidade().getId()
				+ "' where id_predio = '" + predio.getId()+ "'";
		
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarPredio(int id){
		String sql = "DELETE FROM predio where id_predio = " + id + ";";
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