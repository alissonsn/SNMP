package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;






import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.interfaces.Interfaces;
import br.ufrn.service.Consulta;



/** Esta classe tem por finalidade persistir as configurações das portas.
*
* @author silas
*
*/

public class DAOPortas {
	private Connection conexao;
	String enterprise;
	DAOConsulta ConsultaDAO;


	public DAOPortas() throws ClassNotFoundException{
		this.conexao = new ConnectionFactory().getConnection();
		ConsultaDAO = new DAOConsulta();
		enterprise = "";
	}

	/** Metodo persiste o switch no banco
	 *
	 * @param id_switch, identificador do ativo provavelmente um tombamento ou serial, que servirá para identifica-lo
	 * @throws ClassNotFoundException
	 */

	public void adicionarswitch(Integer id_switch){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO switch (id_switch) VALUES (?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setLong(1, id_switch);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}


	/** Metodo que persiste as configurações das portas do ativo
	 *
	 * @param snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param target, uma instancia com o nivel segurança configurado.
	 * @param consulta, para saber qual modelo do ativo está sendo consultado
	 * @param ip, ip do ativo
	 */

	public void adicionarInterface(Snmp snmp, UserTarget target, Consulta consulta, Integer tombo){
		//Variavel para saber apartir de determinado ativo qual classe a ser instanciada
		String classe = ConsultaDAO.SelecionarClasseInterface(consulta.Modelo(snmp, target));
		Interfaces Ports = null;
		try {
			//Instanciando a classe correta para determinado modelo
			Ports = (Interfaces) Class.forName(classe).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Chamando as configurações para as portas
		ArrayList<Object> valor =  Ports.Porta(snmp, target);
		ArrayList<Object> oid = Ports.Oid(snmp, target);
		ArrayList<Integer> id = Ports.id(snmp, target);
		ArrayList<Object> velocidade = Ports.Velocidade(snmp, target);
		ArrayList<Object> estadoPorta = Ports.Estado_porta(snmp, target);
		ArrayList<Object> tipo_conector = Ports.Tipo_conector(snmp, target);
		ArrayList<Object> tipoVlan = Ports.TypeVlan(snmp, target);

		//varrendo o arrylist das portas
		for (int i = 0; i < valor.size(); i++) {
			//Fazendo uma string com o comando para inserir os dados na tabela interface
			String sql = "INSERT INTO interface (id_switch, tipo_vlan, velocidade, estado, tipo_conector, id_interface_snmp, oid_interface_snmp, valor_interface) VALUES (?,?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setLong(1, tombo);
				ps.setString(2, tipoVlan.get(i).toString());
				ps.setString(3, velocidade.get(i).toString());
				ps.setString(4, estadoPorta.get(i).toString());
				if (tipo_conector.isEmpty()) {
					ps.setString(5, "");	
				}else{
					ps.setString(5, tipo_conector.get(i).toString());	
				}
				ps.setInt(6, id.get(i));
				ps.setString(7, oid.get(i).toString());
				ps.setString(8, valor.get(i).toString());
				ps.execute();
				ps.close();
			}catch(SQLException erro){
				throw new RuntimeException(erro);
			}
		}

	}
}
