package br.ufrn.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Classe ConnectioFactory é uma fabrica de conexões, ela servirá para que quando precisemos utilizar algum metodo que persista no banco,
 *  não precise implementar novamente o metodo de conexao, basta apenas instancias esta classe.
 *
 * @author Silas Tiago
 *
 */

public class ConnectionFactory {
	//private String url = "jdbc:mysql://localhost/snmp";
	//private String usuario = "root";
	//private String senha = "root";
	//String driver = "org.gjt.mm.mysql.Driver";

	private String url = "jdbc:postgresql://localhost/snmp";
	private String usuario = "postgres";
	private String senha = "postgres";
	String driver = "org.postgresql.Driver";
	Connection conexao;

/** Metodo getConnection retorna uma conexao
 *
 * @return Connection que é uma conexao
 */

	public Connection getConnection(){
		try{
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conexao = DriverManager.getConnection(url, usuario, senha);
			return conexao;
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	/**	Metodo fecharConexao como o proprio nome já diz, ele fecha uma conexao após o termino de uma conexao.
	 *
	 */

	public void fecharConexao(){
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}