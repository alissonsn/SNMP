package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Switch;

public class DAOData {
	private DAOPortas portas;
	private Connection conexao;
	public DAOData() throws ClassNotFoundException{
		portas = new DAOPortas();
		this.conexao = new ConnectionFactory().getConnection();
	}

	public String dataHoje(){
		Date data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = sdf.format(data);
		return dataFormatada;
	}
	
	public String adicionarData(){
			String dataFormatada = this.dataHoje();
			//Fazendo uma string com o comando para inserir os dados na tabela interface
			String sql = "INSERT INTO data (data) VALUES (?)";
			System.out.println(sql);
			System.out.println(dataFormatada);
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setString(1, dataFormatada);
				//ps.setArray(i, inte.get(i));
				ps.execute();
				ps.close();
			}catch(SQLException erro){
				throw new RuntimeException(erro);
		}
			return dataFormatada;
	}

	public void atualizarData(String id_switch){
		int id_sw  = portas.VerificarSwitch(id_switch);

		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		//String data2 = sdf.format(data);
		//String data2 = sdf.format(data);
		String data2 = "01/01/2016";
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "update data set data = '" + data2 + "' where id_switch = '" + id_sw + "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void AtualizarDataInterface_h(int id_data, String id_switch, int novaData){
		String sql = "UPDATE interface_h set id_data = '" + novaData +
				"' where id_switch = '" + id_switch + "' and id_data = '" + id_data + "';";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void AtualizarDataInterface(Switch comutador, int novaData){
		String sql = "UPDATE interface set id_data = '" + novaData +
				"' where id_switch = '" + comutador.getId_switch() + "';" ;
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}



	public int SelecionarUltimadata(String id_switch){
		ResultSet rs;
		String sql = "select id_data from interface_h where id_switch = '" + id_switch+ "' order by id_data asc;";
		int id = 0;
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = Integer.parseInt(rs.getString("id_data"));
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;
	}

	public int SelecionaridData(String data){
		ResultSet rs;
		String sql = "select id_data from data where data = '" + data + "';";
		int id = 0;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = Integer.parseInt(rs.getString("id_data"));
				System.out.println(id);
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;
	}


	//Selecionar data da tabela interface
	public int SelecionaridDataInterface(String id_switch){
		ResultSet rs;
		String sql = "select max(id_data) from interface_h where id_switch = '" + id_switch + "';";
		int id = 0;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = Integer.parseInt(rs.getString("max"));
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;
	}
}