package br.ufrn.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;












import javax.ws.rs.PUT;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Municipio;
import br.ufrn.model.Registro;


/** Esta classe tem por finalidade fazer o registro inicial dos modelos de ativos.
*
* @author silas
*
*/

public class DAORegistro {
	private Connection conexao;

	public DAORegistro() throws ClassNotFoundException{
		this.conexao = new ConnectionFactory().getConnection();
	}

	/** Este Metodo tem por finalidade fazer o registro de modelos
	 *
	 * @param Registro, Objeto do tipo Registro irá criar um registo com os atributos de Registro localizado no model
	 */

	public void adicionarModelo(Registro Registro) {
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO modelo (enterprise, modelo,classeInterface, classeVlan) VALUES (?,?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, Registro.getEnterprise());
			ps.setString(2, Registro.getModelo());
			ps.setString(3, Registro.getClassePorta());
			ps.setString(4, Registro.getClasseVlan());
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);

		}
	}

	public Registro ListarModelo(String codigo){
		Registro modelo = new Registro();
		ResultSet rs;
		String sql = "select id,enterprise,modelo, classeinterface, classevlan from modelo where id = '"+ codigo+ "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				modelo.setModelo(rs.getString("modelo"));
				modelo.setEnterprise(rs.getString("enterprise"));
				modelo.setClassePorta(rs.getString("classeinterface"));
				modelo.setClasseVlan(rs.getString("classevlan"));

				int id = Integer.parseInt(rs.getString("id"));
				modelo.setId(id);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelo;
	}


	public List<Registro> ListarModelos(){
		List<Registro> listModelos = new ArrayList<Registro>();
		ResultSet rs;
		String sql = "select id,enterprise, modelo, classeinterface, classevlan from modelo;";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Registro registro = new Registro();
				registro.setEnterprise(rs.getString("enterprise"));
				registro.setModelo(rs.getString("modelo"));
				registro.setClassePorta(rs.getString("classeinterface"));
				registro.setClasseVlan(rs.getString("classeVlan"));
				int id = Integer.parseInt(rs.getString("id"));
				registro.setId(id);
				listModelos.add(registro);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listModelos;
	}


	public void atualizarModelo(Registro registro){
		String sql = "UPDATE modelo set enterprise ='"+ registro.getEnterprise()+ "', classeinterface='" + registro.getClassePorta()
				+ "', classeVlan='" + registro.getClasseVlan() + "', modelo='" + registro.getModelo()
				+ "' where id = '" + registro.getId()+ "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}


	public void deletarModelo(int id){
		String sql = "DELETE FROM modelo "
				+ "where id = " + id+ ";";
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