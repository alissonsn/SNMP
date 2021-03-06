package br.ufrn.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Registro;

/** Esta classe tem por finalidade verificar qual classes são é de qual modelo.
*
* @author silas
*
*/

public class DAOConsulta {
	private Connection conexao;

	public DAOConsulta() throws ClassNotFoundException{
		this.conexao = new ConnectionFactory().getConnection();
	}

	/** Metodo que retorna a classe adequada para cada modelo de ativo
	 *
	 * @param enterprise, para cada enterprise retorna uma classe que faz a consulta snmp.
	 * @return String nome da classe que o modelo implementa.
	 */

	public String SelecionarClasseInterface(String enterprise) {
		ResultSet rs;
		String modelo = "";
		String sql = "SELECT classeInterface FROM modelo WHERE enterprise='" + enterprise + "';";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				modelo = rs.getString("classeinterface");
				//System.out.println(modelo);
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return modelo;
	}


	/** Metodo que retorna a classeVlan adequada para cada modelo de ativo
	 *
	 * @param enterprise, para cada enterprise retorna uma classeVlan que faz a consulta snmp.
	 * @return String nome da classeVlan que o modelo implementa.
	 */

	public String SelecionarClasseVlan(String enterprise) {
		ResultSet rs;
		String modelo = "";
		String sql = "SELECT classeVlan FROM modelo WHERE enterprise='" + enterprise + "';";
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				modelo = rs.getString("classeVlan");
				//System.out.println(modelo);
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return modelo;
	}

	public List<Registro> listarModelos(Registro registro){
		List<Registro> lista = new ArrayList<Registro>();
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("select enterprise,modelo,classeinterface,classevlan from modelo");
			while (rs.next()) {
				registro.setEnterprise(rs.getString("enterprise"));
				registro.setModelo(rs.getString("modelo"));
				registro.setClassePorta(rs.getString("classeinterface"));
				registro.setClasseVlan(rs.getString("classevlan"));
				lista.add(registro);
			}
			st.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}