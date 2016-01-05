package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.interfaces.Interfaces;
import br.ufrn.interfaces.InterfacesVlans;
import br.ufrn.model.Switch;
import br.ufrn.model.Vlan;
import br.ufrn.model.Porta;
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
	Porta interfaces;


	public DAOPortas() throws ClassNotFoundException{
		this.conexao = new ConnectionFactory().getConnection();
		ConsultaDAO = new DAOConsulta();
		enterprise = "";
		interfaces = new Porta();
	}

	/** Metodo persiste o switch no banco
	 *
	 * @param id_switch, identificador do ativo provavelmente um tombamento ou serial, que servirá para identifica-lo
	 * @throws ClassNotFoundException
	 */

	public void adicionarswitch(String id_switch, String ip){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO switch (id_switch, ip) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, id_switch);
			ps.setString(2, ip);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	/** Metodo persiste o switch no banco
	 *
	 * @param id_switch, identificador do ativo provavelmente um tombamento ou serial, que servirá para identifica-lo
	 * @throws ClassNotFoundException
	 */

	public void adicionarswitch_h(String id_switch, String ip){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO switch_h (id_switch, ip) VALUES (?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, id_switch);
			ps.setString(2, ip);
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

	public void adicionarInterface(Snmp snmp, UserTarget target, Consulta consulta, String tombo){

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
		ArrayList<Object> ligada = Ports.Porta_ligada(snmp, target);
		ArrayList<Object> tipo_conector = Ports.Tipo_conector(snmp, target);
		ArrayList<Object> tipoVlan = Ports.TypeVlan(snmp, target);

		//varrendo o arrylist das portas
		for (int i = 0; i < valor.size(); i++) {
			//Fazendo uma string com o comando para inserir os dados na tabela interface
			String sql = "INSERT INTO interface (id_switch, tipo_vlan, velocidade, estado, ligada, tipo_conector, id_interface_snmp, oid_interface_snmp, valor_interface) VALUES (?,?,?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setString(1, tombo);
				if(tipoVlan.isEmpty()){
					ps.setString(2, "");
				}else{
					ps.setString(2, "");
					//ps.setString(2, tipoVlan.get(i).toString());
				}
				if (velocidade.isEmpty()){
					ps.setString(3, "");
				}else{
					ps.setString(3, "");
					//ps.setString(3, velocidade.get(i).toString());
				}
				if (estadoPorta.isEmpty()){
					ps.setString(4, "");
				}else{
					ps.setString(4, "");
					//ps.setString(4, estadoPorta.get(i).toString());
				}
				if (ligada.isEmpty()) {
					ps.setString(5, "");
				}else{
					ps.setString(5, "");
					//ps.setString(5, ligada.get(i).toString());

				}
				if (tipo_conector.isEmpty()){
					ps.setString(6, "");
				}else{
					ps.setString(6, tipo_conector.get(i).toString());

					//ps.setString(6, "");
				}
				if (id.isEmpty()){
					ps.setString(7, "");
				}else{
					//ps.setString(7, "");
					ps.setInt(7, id.get(i));

				}
				if (oid.isEmpty()){
					ps.setString(8, "");
				}else{
					//ps.setString(8, "");
					ps.setString(8, oid.get(i).toString());

				}if (valor.isEmpty()){
					ps.setString(9, "");
				}else{
					ps.setString(9, valor.get(i).toString());
				}


				ps.execute();
				ps.close();
			}catch(SQLException erro){
				throw new RuntimeException(erro);
			}
		}
	}

	/** Metodo que persiste as configurações das portas do ativo
	 *
	 * @param snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param target, uma instancia com o nivel segurança configurado.
	 * @param consulta, para saber qual modelo do ativo está sendo consultado
	 * @param ip, ip do ativo
	 */

	public void adicionarInterface_h(Snmp snmp, UserTarget target, Consulta consulta, String tombo){

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
		ArrayList<Object> ligada = Ports.Porta_ligada(snmp, target);
		ArrayList<Object> tipo_conector = Ports.Tipo_conector(snmp, target);
		ArrayList<Object> tipoVlan = Ports.TypeVlan(snmp, target);

		//varrendo o arrylist das portas
		for (int i = 0; i < valor.size(); i++) {
			//Fazendo uma string com o comando para inserir os dados na tabela interface
			String sql = "INSERT INTO interface_h (id_switch, tipo_vlan, velocidade, estado, ligada, tipo_conector, id_interface_snmp, oid_interface_snmp, valor_interface) VALUES (?,?,?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setString(1, tombo);
				if(tipoVlan.isEmpty()){
					ps.setString(2, "");
				}else{
					ps.setString(2, "");
					//ps.setString(2, tipoVlan.get(i).toString());
				}
				if (velocidade.isEmpty()){
					ps.setString(3, "");
				}else{
					ps.setString(3, "");
					//ps.setString(3, velocidade.get(i).toString());
				}
				if (estadoPorta.isEmpty()){
					ps.setString(4, "");
				}else{
					ps.setString(4, "");
					//ps.setString(4, estadoPorta.get(i).toString());
				}
				if (ligada.isEmpty()) {
					ps.setString(5, "");
				}else{
					ps.setString(5, "");
					//ps.setString(5, ligada.get(i).toString());

				}
				if (tipo_conector.isEmpty()){
					ps.setString(6, "");
				}else{
					ps.setString(6, tipo_conector.get(i).toString());

					//ps.setString(6, "");
				}
				if (id.isEmpty()){
					ps.setString(7, "");
				}else{
					//ps.setString(7, "");
					ps.setInt(7, id.get(i));

				}
				if (oid.isEmpty()){
					ps.setString(8, "");
				}else{
					//ps.setString(8, "");
					ps.setString(8, oid.get(i).toString());

				}if (valor.isEmpty()){
					ps.setString(9, "");
				}else{
					ps.setString(9, valor.get(i).toString());
				}


				ps.execute();
				ps.close();
			}catch(SQLException erro){
				throw new RuntimeException(erro);
			}
		}
	}
	
	
	public ArrayList<Switch> comparar(Snmp snmp, UserTarget target, Consulta consulta, String id_switch){

		//Variavel para saber apartir de determinado ativo qual classe a ser instanciada
		String classe = ConsultaDAO.SelecionarClasseInterface(consulta.Modelo(snmp, target));
		Interfaces Ports = null;

		String classevlan = ConsultaDAO.SelecionarClasseVlan(consulta.Modelo(snmp, target));
		InterfacesVlans Vlan = null;



		try {
			//Instanciando a classe correta para determinado modelo
			Ports = (Interfaces) Class.forName(classe).newInstance();
			Vlan = (InterfacesVlans) Class.forName(classevlan).newInstance();
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
		ArrayList<Object> ligada = Ports.Porta_ligada(snmp, target);
		ArrayList<Object> tipo_conector = Ports.Tipo_conector(snmp, target);
		ArrayList<Object> tipoVlan = Ports.TypeVlan(snmp, target);

		ArrayList<Integer> vlan = new ArrayList<Integer>();
		ArrayList<Porta> interfacess = new ArrayList<Porta>();
		ArrayList<Switch> config = new ArrayList<Switch>();
		ArrayList<Object> array = new ArrayList<Object>();
		Switch comutador = new Switch();

		
		//Hashmap que receberá o mapeamento vlan, porta
		HashMap<Integer,String> mape = new HashMap<Integer, String>();
		String portas = "";
		vlan = Vlan.vlan(snmp, target);
		//for (int i = 0; i < vlan.size(); i++) {
		for (int i = 0; i < valor.size(); i++) {
			
		
			portas = mape.get(vlan.get(i));
			String[] valorPorta = portas.split(",");

			interfaces.setId(id.get(i));
			interfaces.setOid(oid.get(i));
			interfaces.setVelocidade(velocidade.get(i));
			interfaces.setEstadoPorta(estadoPorta.get(i));
			interfaces.setLigada(ligada.get(i));
			interfaces.setTipoVlan(tipoVlan.get(i));
			interfaces.setTipo_conector(tipo_conector.get(i));
			//interfaces.setVlan(vlan.get(i));


			//for (int k = 0; k < valorPorta.length; k++) {
				//array.add(valorPorta[k]);

				interfaces.setValor(valor.get(i));
				interfacess.add(interfaces);
				comutador.setId_switch(id_switch);
				comutador.setInterfaces(interfacess);

			//}

		}
		config.add(comutador);
		//config.add(this.pegarTudo(id_switch).get(0));

		return config;

	}


	public ArrayList<Switch> pegarTudo(String id_switch){
		ArrayList<Switch> config = new ArrayList<Switch>();
		Object id_interface = new Object();
		Object oid_interface = new Object();
		Object estado = new Object();
		Object ligada = new Object();
		Object conector = new Object();
		Object id_porta = new Object();
		Object velocidade = new Object();
		Object valor_interface = new Object();
		Object tipoVlan = new Object();
		ArrayList<Vlan> vlan = new ArrayList<Vlan>();
		ArrayList<Porta> interfacess = new ArrayList<Porta>();
		Object encaminhador = new Object();
		Object ip = new Object();
		Switch comutador = new Switch();

		ResultSet rs;
		String sql = "select id_interface_snmp, valor_interface, oid_interface_snmp, estado, ligada, tipo_conector, interface_h.id_porta, velocidade, tipo_vlan, ip, interface_h.id_switch, vlan from interface_h interface_h INNER JOIN vlan_h vlan_h on vlan_h.id_porta = interface_h.id_porta inner join switch_h switch_h on switch_h.id_switch = interface_h.id_switch  and switch_h.id_switch = '" + id_switch + "';";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			Object id_porta_anterior = -1;
			while(rs.next()){
				Vlan objVlan = new Vlan();
				id_interface = (rs.getString("id_interface_snmp"));
				if(id_interface.equals(id_porta_anterior)){
					objVlan.setVlan(rs.getString("vlan"));
					vlan.add(objVlan);
					//interfaces.setVlan(vlan);
					//comutador.setInterfaces(interfaces);
				}else{
					vlan = new ArrayList<Vlan>();
					interfaces = new Porta();
					oid_interface = (rs.getString("oid_interface_snmp"));
					estado = (rs.getString("estado"));
					ligada = (rs.getString("ligada"));
					conector = (rs.getString("tipo_conector"));
					id_porta = (rs.getString("id_porta"));
					velocidade = (rs.getString("velocidade"));
					valor_interface = (rs.getString("valor_interface"));
					tipoVlan = (rs.getString("tipo_vlan"));
					objVlan.setVlan(rs.getString("vlan"));
					vlan.add(objVlan);
					ip = (rs.getString("ip"));
					encaminhador = (rs.getString("id_switch"));

					interfaces.setId(id_interface);
					interfaces.setOid(oid_interface);
					interfaces.setEstadoPorta(estado);
					interfaces.setLigada(ligada);
					interfaces.setTipo_conector(conector);
					interfaces.setId_porta(id_porta);
					interfaces.setVelocidade(velocidade);
					interfaces.setValor(valor_interface);
					interfaces.setTipoVlan(tipoVlan);
					interfaces.setVlan(vlan);
					interfacess.add(interfaces);
					//interfaces.setVlan(vlan);

					comutador.setInterfaces(interfacess);
					comutador.setId_switch(encaminhador);
					comutador.setIp(ip);
					id_porta_anterior = id_interface;
				}

			}

			config.add(comutador);
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return config;
	}

	public ArrayList<Porta> StatusPorta(String id_porta, String id_switch){
		ArrayList<Porta> status = new ArrayList<Porta>();
		Object estado = new Object();
		Object velocidade = new Object();
		Object ligada = new Object();
		ResultSet rs;
		String sql = "select estado, velocidade, ligada from interface_h where id_switch = " + id_switch + "" + " and id_porta =" + id_porta + ";";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Porta porta = new Porta();

				estado = rs.getString("estado");
				velocidade = rs.getString("velocidade");
				ligada = rs.getString("ligada");
				porta.setEstadoPorta(estado);
				porta.setVelocidade(velocidade);
				porta.setLigada(ligada);
				status.add(porta);
			}

			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return status;
	}

	public ArrayList<Porta> PortasLivres(String id_switch, String vlan){
		ArrayList<Porta> list_portasLivres = new ArrayList<Porta>();
		Object Interface = new Object();
		ResultSet rs;
		String sql = "select valor_interface from vlan_h INNER JOIN interface_h on vlan_h.id_porta = interface_h.id_porta and interface_h.id_switch ='" + id_switch + "'" + " and vlan_h.vlan='" + vlan + "'" + " and interface_h.estado ='Down';";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Porta interfaces = new Porta();
				Interface = rs.getString("valor_interface");

				//valorInterface.add(rs.getString("valor_interface"));
				interfaces.setValor(Interface);
				list_portasLivres.add(interfaces);	
				//list_portasLivres.add(comutador);
			}

			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return list_portasLivres;
	}

	public ArrayList<Switch> VerificarSwitch(String id_switch){
		ArrayList<Switch> id_sw = new ArrayList<Switch>();
		ResultSet rs;
		String sql = "select id_switch from switch where id_switch = '" + id_switch + "';";
		String id = "";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Switch comutador = new Switch();
				id = rs.getString("id_switch");

				//valorInterface.add(rs.getString("valor_interface"));
				comutador.setId_switch(id);
				id_sw.add(comutador);	
				//list_portasLivres.add(comutador);
			}

			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id_sw;
		
	}
	
	
}
