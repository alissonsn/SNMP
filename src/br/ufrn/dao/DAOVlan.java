package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.interfaces.InterfacesVlans;
import br.ufrn.model.Porta;
import br.ufrn.model.Switch;
import br.ufrn.model.Vlan;
import br.ufrn.service.Consulta;



public class DAOVlan {

	/** Esta classe tem por finalidade persistir as configurações das vlans.
	 *
	 * @author silas
	 *
	 */

	private Connection conexao;
	//Vlan vlan;
	DAOConsulta ConsultaDAO;
	Porta interfaces;
	public DAOVlan(){
		this.conexao = new ConnectionFactory().getConnection();
		try {
			ConsultaDAO = new DAOConsulta();
			interfaces = new Porta();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** Este metodo tem por finalidade persistir as vlans
	 *
	 * @param snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param target, uma instancia com o nivel segurança configurado.
	 * @param consulta, para saber qual modelo do ativo está sendo consultado
	 * @param tombo, identificador unico para cada ativo
	 */

	public void adicionarVlan(Snmp snmp, UserTarget target, Consulta consulta, String tombo) {
		//Variavel para saber apartir de determinado ativo qual classe a ser instanciada
		String classe = ConsultaDAO.SelecionarClasseVlan(consulta.Modelo(snmp, target));
		InterfacesVlans Vlan = null;
		try {
			//Instanciando a classe correta para determinado modelo
			Vlan = (InterfacesVlans) Class.forName(classe).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//ID da porta
		Integer id_porta = 0;
		//Hashmap que receberá o mapeamento vlan, porta
		HashMap<Integer,String> mape = new HashMap<Integer, String>();
		//Recebendo o mapeamento vlan,porta
		mape = Vlan.mapeamento(snmp, target);

		//Arraylist das vlans
		ArrayList<Integer> vlans = new ArrayList<Integer>();

		ArrayList<String> array = new ArrayList<String>();


		//Recebendo as vlans
		vlans = Vlan.vlan(snmp, target);

		//Receberá as portas
		String portas = "";

		/*Object key = vlans.get(1);
		numero = auxiliar.converter_string_int(key.toString());
		test = "" + numero;
		//0255
		System.out.println(key.toString());
		//28,
		System.out.println(mape.get(test));
		//255
		System.out.println(numero);*/

		for (int i = 0; i < vlans.size(); i++) {

			//Vlans
			//System.out.println("Numero vlan: " + vlans.get(i));
			//Recebedo as ID SNMP portas
			portas =	mape.get(vlans.get(i));
			//Criando expressão regular para retirar as virgular das strings, Pois as strings estavam vindo do jeito a seguir 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
			String[] valor = portas.split(",");
			for (int k = 0; k < valor.length; k++) {
				//Adicionando portas ao array sem as virgulas cada posição do array é uma porta
				array.add(valor[k]);

				//ID da porta recuperado pelo tombo e a interface
				id_porta = buscarPorta(tombo, array.get(k));
				//Fazendo uma string com o comando para inserir os dados na tabela interface
				String sql = "INSERT INTO vlan (id_porta, vlan) VALUES (?,?)";
				try{
					PreparedStatement ps = conexao.prepareStatement(sql);
					ps.setInt(1, id_porta);
					ps.setString(2,vlans.get(i).toString());
					ps.execute();
					ps.close();

				}catch(SQLException erro){
					throw new RuntimeException(erro);
				}
			}
			array.clear();
		}

	}


	/** Este metodo tem por finalidade persistir as vlans
	 *
	 * @param snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param target, uma instancia com o nivel segurança configurado.
	 * @param consulta, para saber qual modelo do ativo está sendo consultado
	 * @param tombo, identificador unico para cada ativo
	 */

	public void adicionarVlan_h(Snmp snmp, UserTarget target, Consulta consulta, String tombo) {
		//Variavel para saber apartir de determinado ativo qual classe a ser instanciada
		String classe = ConsultaDAO.SelecionarClasseVlan(consulta.Modelo(snmp, target));
		InterfacesVlans Vlan = null;
		try {
			//Instanciando a classe correta para determinado modelo
			Vlan = (InterfacesVlans) Class.forName(classe).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//ID da porta
		Integer id_porta = 0;
		//Hashmap que receberá o mapeamento vlan, porta
		HashMap<Integer,String> mape = new HashMap<Integer, String>();
		//Recebendo o mapeamento vlan,porta
		mape = Vlan.mapeamento(snmp, target);

		//Arraylist das vlans
		ArrayList<Integer> vlans = new ArrayList<Integer>();

		ArrayList<String> array = new ArrayList<String>();


		//Recebendo as vlans
		vlans = Vlan.vlan(snmp, target);

		//Receberá as portas
		String portas = "";

		/*Object key = vlans.get(1);
		numero = auxiliar.converter_string_int(key.toString());
		test = "" + numero;
		//0255
		System.out.println(key.toString());
		//28,
		System.out.println(mape.get(test));
		//255
		System.out.println(numero);*/

		for (int i = 0; i < vlans.size(); i++) {

			//Vlans
			//System.out.println("Numero vlan: " + vlans.get(i));
			//Recebedo as ID SNMP portas
			portas =	mape.get(vlans.get(i));
			//Criando expressão regular para retirar as virgular das strings, Pois as strings estavam vindo do jeito a seguir 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
			String[] valor = portas.split(",");
			for (int k = 0; k < valor.length; k++) {
				//Adicionando portas ao array sem as virgulas cada posição do array é uma porta
				array.add(valor[k]);

				//ID da porta recuperado pelo tombo e a interface
				id_porta = buscarPorta_h(tombo, array.get(k));
				//Fazendo uma string com o comando para inserir os dados na tabela interface
				String sql = "INSERT INTO vlan_h (id_porta, vlan) VALUES (?,?)";
				try{
					PreparedStatement ps = conexao.prepareStatement(sql);
					ps.setInt(1, id_porta);
					ps.setString(2,vlans.get(i).toString());
					ps.execute();
					ps.close();

				}catch(SQLException erro){
					throw new RuntimeException(erro);
				}
			}
			array.clear();
		}
	}


	/** Este metodo tem por finalidade procurar o id_switch para ser utilizado em outro metodo
	 *
	 * @param id_switch, este atributo será provavelmente o tombo ou serial do ativo
	 * @param id_interface_snmp, o id correspondente a determinada interface
	 * @return Integer que será o id_switch
	 */

	private Integer buscarPorta_h(String id_switch, String id_interface_snmp) {
		ResultSet rs;
		Integer id_porta = 0;
		String sql = "select id_porta from interface_h where id_switch = '" + id_switch+ "' AND id_interface_snmp = " + id_interface_snmp + ";";
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id_porta = rs.getInt("id_porta");
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id_porta;
	}

	private Integer buscarPorta(String id_switch, String id_interface_snmp) {
		ResultSet rs;
		Integer id_porta = 0;
		String sql = "select id_porta from interface where id_switch = '" + id_switch+ "' AND id_interface_snmp = " + id_interface_snmp + ";";
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id_porta = rs.getInt("id_porta");
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id_porta;
	}

	public void removerVlan(String idSwitch) throws ClassNotFoundException{
		DAOPortas daoporta = new DAOPortas();
		ArrayList<String> idPorta = daoporta.selecionarIdPorta(idSwitch);
		for (int i = 0; i < idPorta.size(); i++) {
			String sql = "DELETE from vlan where id_porta = '" + idPorta.get(i) + "';" ;
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

	public List<Vlan> getAll(){
		ArrayList<Vlan> vlan = new ArrayList<Vlan>();
		ResultSet rs;
		String sql = "select distinct vlan from vlan;";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Vlan objVlan = new Vlan();
				//interfaces = new Interfaces();
				objVlan.setVlan(rs.getString("vlan"));
				vlan.add(objVlan);
				//interfaces.setVlan(vlan);
				//listVlan.add(comutador);
				//System.out.println(modelo);
			}

			//listVlan.add(interfaces);
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vlan;
	}

	public List<Vlan> getVlan(String id_switch){
		ArrayList<Vlan> vlan = new ArrayList<Vlan>();
		ResultSet rs;
		String sql = "select distinct vlan from vlan "
				+ "INNER JOIN interface on vlan.id_porta = interface.id_porta "
				+ "and interface.id_switch = '" + id_switch + "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Vlan objVlan = new Vlan();
				//interfaces = new Interfaces();
				objVlan.setVlan(rs.getString("vlan"));
				vlan.add(objVlan);
				//interfaces.setVlan(vlan);
				//listVlan.add(comutador);
				//System.out.println(modelo);
			}

			//listVlan.add(interfaces);
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vlan;
	}



	public List<Switch> getSwitchVlan(String vlan){
		List<Switch> listSwitches= new ArrayList<Switch>();
		//ArrayList<Object> id_switch = new ArrayList<Object>();

		ResultSet rs;
		String sql = "select distinct id_switch from vlan "
				+ "INNER JOIN interface on vlan.id_porta = interface.id_porta "
				+ "and vlan.vlan = '" + vlan + "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Switch comutador = new Switch();
				//id_switch.add(Integer.parseInt(rs.getString("id_switch")));
				comutador.setId_switch(rs.getString("id_switch"));
				listSwitches.add(comutador);
				//listVlan.add(comutador);

				//System.out.println(modelo);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSwitches;
	}
}