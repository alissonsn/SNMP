package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Interface_Raspberry;

public class DAOInterfaceRaspberry {
	private Connection conexao;
	
	
	public DAOInterfaceRaspberry(){
		this.conexao = new ConnectionFactory().getConnection();
	}
	
	

	public void adicionarInterfaceRaspbery(Interface_Raspberry interface_rapsberry){
		String sql = "INSERT INTO interface_raspberry (interface, id_raspberry, id_switch) VALUES (?,?,?)";
		System.out.println("SQL: " + sql);
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, interface_rapsberry.getInterface_raspberry());
			ps.setLong(2, interface_rapsberry.getRaspberry().getId_raspberry());
			ps.setLong(3, interface_rapsberry.getRaspberry().getComutador().getId_switch());
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
}