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
import br.ufrn.model.Raspberry;
import br.ufrn.model.Unidade;

public class DAORaspberry {
	private Connection conexao;
	
	public DAORaspberry(){
		this.conexao = new ConnectionFactory().getConnection();
	}
	
	public void adicionarRaspbery(Raspberry raspberry){
		String sql = "INSERT INTO raspberry (id_rapberry, id_switch) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, raspberry.getId_raspberry());
			ps.setLong(2, raspberry.getComutador().getId_switch());
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
}
