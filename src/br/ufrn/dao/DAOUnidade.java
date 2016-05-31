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
import br.ufrn.model.Unidade;

public class DAOUnidade {
	private Connection conexao;

	public DAOUnidade(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarUnidade(Unidade unidade){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO unidade (nomeunidade,id_unidade_municipio) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, unidade.getNome());
			ps.setLong(2, unidade.getMunicipio().getId());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}


	public List<Unidade> ListarUnidades(){
		List<Unidade> unidades = new ArrayList<Unidade>();
		ResultSet rs;
		String sql = "select id_unidade,nomeunidade,nomemunicipio,id_unidade_municipio "
				+ "from unidade unidade INNER JOIN municipio municipio "
				+ "on id_municipio = id_unidade_municipio;";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Unidade unidade = new Unidade();
				int id = Integer.parseInt(rs.getString("id_unidade"));
				unidade.setId(id);
				unidade.setNome(rs.getString("nomeunidade"));
				int id_municipio = Integer.parseInt(rs.getString("id_unidade_municipio"));

				Municipio municipio = new Municipio();
				municipio.setId(id_municipio);
				municipio.setNome(rs.getString("nomemunicipio"));
				unidade.setMunicipio(municipio);
				unidades.add(unidade);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unidades;
	}

	public Unidade ListarUnidade(String codigo){
		Unidade unidade = new Unidade();
		ResultSet rs;
		String sql = "select nomeunidade,id_unidade,id_unidade_municipio, nomemunicipio "
				+ "from unidade unidade INNER JOIN municipio municipio "
				+ "on id_municipio = id_unidade_municipio where id_unidade = '"+ codigo+ "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				int id = Integer.parseInt(rs.getString("id_unidade"));
				unidade.setId(id);
				unidade.setNome(rs.getString("nomeunidade"));
				int id_municipio = Integer.parseInt(rs.getString("id_unidade_municipio"));
				Municipio municipio = new Municipio();
				municipio.setId(id_municipio);
				municipio.setNome(rs.getString("nomemunicipio"));
				unidade.setMunicipio(municipio);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unidade;
	}

	public List<Unidade> ListarUnidadeMunicipio(String codigo){
		List<Unidade> unidades = new ArrayList<Unidade>();

		ResultSet rs;
		String sql = "select nomeunidade,id_unidade,id_unidade_municipio, nomemunicipio "
				+ "from unidade unidade INNER JOIN municipio municipio "
				+ "on id_municipio = id_unidade_municipio "
				+ "where id_unidade_municipio = '"+ codigo+ "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Unidade unidade = new Unidade();
				int id = Integer.parseInt(rs.getString("id_unidade"));
				unidade.setId(id);
				unidade.setNome(rs.getString("nomeunidade"));
				int id_municipio = Integer.parseInt(rs.getString("id_unidade_municipio"));
				Municipio municipio = new Municipio();
				municipio.setId(id_municipio);
				municipio.setNome(rs.getString("nomemunicipio"));
				unidade.setMunicipio(municipio);
				unidades.add(unidade);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unidades;
	}

	public void atualizarUnidade(Unidade unidade){
		String sql = "UPDATE unidade set nomeunidade ='"+ unidade.getNome() +
				"' , id_unidade_municipio = '" + unidade.getMunicipio().getId()
				+ "' where id_unidade = '" + unidade.getId()+ "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarUnidade(int id){
		String sql = "DELETE FROM unidade "
				+ "where id_unidade = " + id+ ";";
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