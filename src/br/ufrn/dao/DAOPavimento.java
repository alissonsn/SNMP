package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Municipio;
import br.ufrn.model.Pavimento;
import br.ufrn.model.Predio;
import br.ufrn.model.Unidade;

public class DAOPavimento {
	private Connection conexao;

	public DAOPavimento(){
		this.conexao = new ConnectionFactory().getConnection();
	}
	
	public void adicionarPavimento(Pavimento pavimento){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO pavimento (nomepavimento,id_pavimento_predio) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, pavimento.getNome());
			ps.setLong(2, pavimento.getPredio().getId());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
	public List<Pavimento> listarPavimentos(){
		
		List<Pavimento> pavimentos = new ArrayList<Pavimento>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, "
				+ "nomepredio, id_pavimento, nomepavimento from pavimento pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio;";
		java.sql.Statement st;
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
				pavimento.setMunicipio(municipio);
				pavimento.setUnidade(unidade);
				pavimento.setPredio(predio);
				pavimentos.add(pavimento);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pavimentos;
	}

	public Pavimento listarPavimento(String codigo){
		Pavimento pavimento = new Pavimento();
		ResultSet rs;
		String sql = "select id_pavimento,nomepavimento,nomepredio,id_pavimento_predio "
				+ "from pavimento pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio;"
				+ "where id_pavimento = '"+ codigo+ "';";
		java.sql.Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				int id = Integer.parseInt(rs.getString("id_pavimento"));
				
				pavimento.setId(id);
				pavimento.setNome(rs.getString("nomepavimento"));
				
				
				Predio predio = new Predio();
				predio.setNome(rs.getString("nomepredio"));
				int id_pavimento_predio = Integer.parseInt(rs.getString("id_pavimento_predio"));
				predio.setId(id_pavimento_predio);
				pavimento.setPredio(predio);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pavimento;
	}

	public List<Pavimento> listarPavimentoPredio(String codigo){
		List<Pavimento> pavimentos = new ArrayList<Pavimento>();
		ResultSet rs;
		String sql = "select id_predio,nomepredio,id_pavimento,nomepavimento "
				+ "from predio predio INNER JOIN pavimento pavimento "
				+ "on id_predio = id_pavimento_predio "
				+ "where id_predio = '"+ codigo+ "';";
		java.sql.Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Pavimento pavimento = new Pavimento();
				int id = Integer.parseInt(rs.getString("id_pavimento"));
				
				pavimento.setId(id);
				pavimento.setNome(rs.getString("nomepavimento"));
				
				
				Predio predio = new Predio();
				predio.setNome(rs.getString("nomepredio"));
				int id_pavimento_predio = Integer.parseInt(rs.getString("id_predio"));
				predio.setId(id_pavimento_predio);
				pavimento.setPredio(predio);
				pavimentos.add(pavimento);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pavimentos;
	}
	
	
	public void atualizarPavimento(Pavimento pavimento){
		String sql = "UPDATE pavimento set nomepavimento ='"+ pavimento.getNome() +
				"' , id_pavimento_predio = '" + pavimento.getPredio().getId()
				+ "' where id_pavimento = '" + pavimento.getId()+ "'";
		
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarPavimento(int id){
		String sql = "DELETE FROM pavimento "
				+ "where id_pavimento = " + id+ ";";
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