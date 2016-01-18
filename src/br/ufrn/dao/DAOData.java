package br.ufrn.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.ufrn.conexao.ConnectionFactory;

public class DAOData {
	private DAOPortas portas;
	private Connection conexao;
	public DAOData() throws ClassNotFoundException{
		portas = new DAOPortas();
		this.conexao = new ConnectionFactory().getConnection();
	}
	
	public void adicionarData(String id_switch, String estado){
		ArrayList<Integer> id_sw  = portas.VerificarSwitch(id_switch);
		
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		//String data2 = sdf.format(data);
		//String data2 = sdf.format(data);
		String data2 = "12/13/2016";
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO data (id_switch, data, estado) VALUES (?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, id_sw.get(0));
			ps.setString(2, data2);
			ps.setString(3, estado);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void adicionarData_h(String id_switch, String estado){
		ArrayList<Integer> id_sw  = portas.VerificarSwitch_h(id_switch);
		
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		//String data2 = sdf.format(data);
		//String data2 = sdf.format(data);
		String data2 = "12/13/2016";
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO data_h (id_switch, data, estado) VALUES (?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, id_sw.get(0));
			ps.setString(2, data2);
			ps.setString(3, estado);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
	
	
}
