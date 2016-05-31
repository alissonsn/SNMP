package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Porta;
import br.ufrn.model.Switch;



/** Esta classe tem por finalidade persistir as configurações das portas.
 *
 * @author silas
 *
 */

public class DAOSwitch {
	private Connection conexao;
	boolean teste; //bool;
	boolean[] teste_ind = new boolean[20];
	boolean teste2;



	public DAOSwitch() throws ClassNotFoundException{
		this.conexao = new ConnectionFactory().getConnection();
	}

	public boolean CompararSwitches(Switch switch1, Switch switch2){
		if (switch1.hashCode() == switch2.hashCode() &&
				switch1.getInterfaces().hashCode() == switch2.getInterfaces().hashCode() ) {
			System.out.println("Switch1 hashcode: " + switch1.hashCode());
			System.out.println("Switch2 hashcode: " + switch2.hashCode());
			return true;
		}else{
			return false;
		}

	}


	public String Selecionarid_switch(String tombo){
		ResultSet rs;
		String sql = "select id_switch from switch where serialtombo = '" + tombo + "';";
		String id = "" ;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = rs.getString("id_switch");
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;
	}

	public void removerSwitch(String tombo){
		//String tombo = Selecionarid_switch(id_switch);
		String sql = "DELETE from switch where serialtombo = '" + tombo + "';" ;
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
