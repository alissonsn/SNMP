package Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import model.Registro;

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
}