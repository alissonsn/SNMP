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
import br.ufrn.model.Registro;

public class DAOMunicipio {
	private Connection conexao;

	public DAOMunicipio(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarMunicipio(Municipio municipio){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO municipio (nomemunicipio) VALUES (?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, municipio.getNome());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Municipio> listarMuncipios(){
		List<Municipio> municipios = new ArrayList<Municipio>();
		ResultSet rs;
		String sql = "select nomemunicipio, id_municipio from municipio;";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Municipio municipio = new Municipio();
				municipio.setNome(rs.getString("nomemunicipio"));
				int id = Integer.parseInt(rs.getString("id_municipio"));
				municipio.setId(id);
				municipios.add(municipio);
				//System.out.println(modelo);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return municipios;
	}

	public Municipio listarMuncipio(String codigo){
		Municipio municipio = new Municipio();
		ResultSet rs;
		String sql = "select nomemunicipio,id_municipio from municipio where id_municipio = '"+ codigo+ "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				municipio.setNome(rs.getString("nomemunicipio"));
				int id = Integer.parseInt(rs.getString("id_municipio"));
				municipio.setId(id);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return municipio;
	}


	public void atualizarMunicipio(Municipio municipio){
		String sql = "UPDATE municipio set nomemunicipio ='"+ municipio.getNome() 	+ "' "
				+ "where id_municipio = '" + municipio.getId()+ "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarMunicipio(int id){
		String sql = "DELETE FROM municipio "
				+ "where id_municipio = " + id+ ";";
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