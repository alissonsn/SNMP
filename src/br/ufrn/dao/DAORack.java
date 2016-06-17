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
import br.ufrn.model.Unidade;

public class DAORack {
	private Connection conexao;

	public DAORack(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarRack(Rack rack){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO rack (nomerack, qtdUS, id_rack_sala) VALUES (?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, rack.getNome());
			ps.setString(2, rack.getQtdUS());
			ps.setLong(3, rack.getSala().getId());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	
	public List<Rack> listarRacks(){
		List<Rack> racks = new ArrayList<Rack>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, "
				+ "nomepredio, id_pavimento, nomepavimento, id_andar, nomeandar, id_sala, "
				+ "nomesala, id_rack, nomerack, qtdus from rack rack "
				+ "INNER JOIN sala on id_rack_sala = id_sala "
				+ "INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio;";
		System.out.println(sql);
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
				rack.setMunicipio(municipio);
				rack.setUnidade(unidade);
				rack.setPredio(predio);
				rack.setPavimento(pavimento);		
				rack.setSala(sala);
				racks.add(rack);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return racks;
	}

	public Rack listarRack(String codigo){
		Rack rack = new Rack();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, "
				+ "nomepredio, id_pavimento, nomepavimento, id_andar, nomeandar, id_sala, "
				+ "nomesala, id_rack, nomerack, qtdus from rack rack "
				+ "INNER JOIN sala on id_rack_sala = id_sala "
				+ "INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio "
				+ "where id_rack = '" + codigo + "';";
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
				
				int id_rack = Integer.parseInt(rs.getString("id_rack"));
				rack.setId(id_rack);
				rack.setNome(rs.getString("nomerack"));
				rack.setQtdUS(rs.getString("qtdus"));
				rack.setMunicipio(municipio);
				rack.setUnidade(unidade);
				rack.setPredio(predio);
				rack.setPavimento(pavimento);		
				rack.setSala(sala);
		
				
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rack;
	}
	
		
	


	public void atualizarRack(Rack rack){
		String sql = "UPDATE rack set nomerack ='"+ rack.getNome() 	+ "' "
				+ "where id_rack = '" + rack.getId()+ "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void deletarRack(int id){
		String sql = "DELETE FROM rack where id_rack = '" + id+ "';";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Rack> listarRacksSala(String codigo) {
		List<Rack> racks = new ArrayList<Rack>();
		ResultSet rs;
		String sql = "select id_municipio, nomemunicipio, id_unidade, nomeunidade, id_predio, "
				+ "nomepredio, id_pavimento, nomepavimento, id_andar, nomeandar, id_sala, "
				+ "nomesala, id_rack, nomerack, qtdus from rack rack "
				+ "INNER JOIN sala on id_rack_sala = id_sala "
				+ "INNER JOIN andar on id_sala_andar = id_andar "
				+ "INNER JOIN pavimento on id_andar_pavimento = id_pavimento "
				+ "INNER JOIN predio on id_predio = id_pavimento_predio "
				+ "INNER JOIN unidade on id_predio_unidade = id_unidade "
				+ "INNER JOIN municipio on id_municipio = id_unidade_municipio "
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
				
				Sala sala = new Sala();
				sala.setNome(rs.getString("nomesala"));
				int id_sala = Integer.parseInt(rs.getString("id_sala"));
				sala.setId(id_sala);
				
				Rack rack = new Rack();
				int id_rack = Integer.parseInt(rs.getString("id_rack"));
				rack.setId(id_rack);
				rack.setNome(rs.getString("nomerack"));
				rack.setQtdUS(rs.getString("qtdus"));
				rack.setMunicipio(municipio);
				rack.setUnidade(unidade);
				rack.setPredio(predio);
				rack.setPavimento(pavimento);		
				rack.setSala(sala);
				racks.add(rack);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return racks;
	}
}