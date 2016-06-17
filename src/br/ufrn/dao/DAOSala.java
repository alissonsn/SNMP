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

public class DAOSala {
	private Connection conexao;

	public DAOSala(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarSala(Sala sala){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO sala (nomesala, id_sala_andar) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, sala.getNome());
			ps.setLong(2, sala.getAndar().getId());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Sala> listarSalas(){
		List<Sala> salas = new ArrayList<Sala>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, "
				+ "nomepavimento, id_andar, nomeandar, id_sala, nomesala "
				+ "from sala sala INNER JOIN andar on id_sala_andar = id_andar "
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
			
				int id_pavimento = Integer.parseInt(rs.getString("id_pavimento"));
				Pavimento pavimento = new Pavimento();
				pavimento.setId(id_pavimento);
				pavimento.setNome(rs.getString("nomepavimento"));
				
				Andar andar = new Andar();
				andar.setNome(rs.getString("nomeandar"));
				int id_andar= Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				
				Sala sala = new Sala();
				sala.setNome(rs.getString("nomesala"));
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				sala.setMunicipio(municipio);
				sala.setUnidade(unidade);
				sala.setPredio(predio);
				sala.setPavimento(pavimento);
				sala.setAndar(andar);
				salas.add(sala);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salas;
	}

	public List<Sala> listarSalasAndar(String codigo){
		List<Sala> salas = new ArrayList<Sala>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, "
				+ "nomepavimento, id_andar, nomeandar id_sala, nomesala "
				+ "from sala sala INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio;"
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
				int id_andar= Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				
				Sala sala = new Sala();
				sala.setNome(rs.getString("nomesala"));
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				sala.setMunicipio(municipio);
				sala.setUnidade(unidade);
				sala.setPredio(predio);
				sala.setPavimento(pavimento);
				sala.setAndar(andar);
				salas.add(sala);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salas;
	}
	
	public Sala listarSala(String codigo){
		Sala sala = new Sala();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, nomepredio, id_pavimento, "
				+ "nomepavimento, id_andar, nomeandar id_sala, nomesala "
				+ "from sala sala INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio;"
				+ "where id_sala = '"+ codigo+ "';";
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
				int id_andar= Integer.parseInt(rs.getString("id_andar"));
				andar.setId(id_andar);
				
				sala.setNome(rs.getString("nomesala"));
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				sala.setMunicipio(municipio);
				sala.setUnidade(unidade);
				sala.setPredio(predio);
				sala.setPavimento(pavimento);
				sala.setAndar(andar);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sala;
	}


	public void atualizarSala(Sala sala){
		String sql = "UPDATE sala set nomesala ='"+ sala.getNome() 	+ "' "
				+ "where id_sala = '" + sala.getId()+ "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarSala(int id){
		String sql = "DELETE FROM sala where id_sala = '" + id+ "';";
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