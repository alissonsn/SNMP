package br.ufrn.dao;

import javax.ws.rs.PUT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.interfaces.Interfaces;
import br.ufrn.model.Switch;
import br.ufrn.model.VlanSW;
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

	public void adicionarswitch(Switch comutador){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO switch (ip, serialtombo, posicao_rack, id_switch_rack) VALUES (?,?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, comutador.getIp().toString());
			ps.setString(2, comutador.getSerialtombo().toString());
			ps.setString(3, comutador.getPosicaoRack().toString());
			ps.setLong(4, comutador.getRack().getId());
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

	public void adicionarswitch_h(Switch comutador){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO switch_h (ip, serialtombo, posicao_rack, id_switch_rack) VALUES (?,?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, comutador.getIp().toString());
			ps.setString(2, comutador.getSerialtombo().toString());
			ps.setString(3, comutador.getPosicaoRack().toString());
			ps.setLong(4, comutador.getRack().getId());
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

	public void adicionarInterface(Snmp snmp, UserTarget target, Consulta consulta, String tombo, String modificacao, int data, String aprova){
		//inteiro referente ao id do switch
		int id_sw  = VerificarSwitch(tombo);


		//Variavel para saber apartir de determinado ativo qual classe a ser instanciada
		String classe = ConsultaDAO.SelecionarClasseInterface(consulta.Modelo(snmp, target));
		Interfaces Ports = null;
		try {
			//Instanciando a classe correta para determinado modelo
			Ports = (Interfaces) Class.forName(classe).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
			String sql = "INSERT INTO interface (id_interface_switch, id_data, tipo_vlan, velocidade, estado, ligada, "
					+ "tipo_conector, id_interface_snmp, oid_interface_snmp, valor_interface, modificacao, "
					+ "aprova) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setLong(1, id_sw);
				if(tipoVlan.isEmpty()){
					ps.setString(2, "");
				}else{
					ps.setLong(2, data);
				}
				if(tipoVlan.isEmpty()){
					ps.setString(3, "");
				}else{
					//ps.setString(3, "");
					ps.setString(3, tipoVlan.get(i).toString());
				}
				if (velocidade.isEmpty()){
					ps.setString(4, "");
				}else{
					//ps.setString(3, "");
					ps.setString(4, velocidade.get(i).toString());
				}
				if (estadoPorta.isEmpty()){
					ps.setString(5, "");
				}else{
					//ps.setString(4, "");
					ps.setString(5, estadoPorta.get(i).toString());
				}
				if (ligada.isEmpty()) {
					ps.setString(6, "");
				}else{
					//ps.setString(5, "");
					ps.setString(6, ligada.get(i).toString());

				}
				if (tipo_conector.isEmpty()){
					ps.setString(7, "");
				}else{
					ps.setString(7, tipo_conector.get(i).toString());
					//ps.setString(6, "");
				}
				if (id.isEmpty()){
					ps.setString(8, "");
				}else{
					//ps.setString(7, "");
					ps.setInt(8, id.get(i));

				}
				if (oid.isEmpty()){
					ps.setString(9, "");
				}else{
					//ps.setString(8, "");
					ps.setString(9, oid.get(i).toString());

				}if (valor.isEmpty()){
					ps.setString(10, "");
				}else{
					ps.setString(10, valor.get(i).toString());
				}if (valor.isEmpty()){
					ps.setString(11, "");
				}else{
					ps.setString(11, modificacao);
				}if (valor.isEmpty()){
					ps.setString(12, "");
				}else{
					ps.setString(12, aprova);
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

	public void adicionarInterface_h(Snmp snmp, UserTarget target, Consulta consulta, String tombo, String  modificacao, int data, String aprova){
		//Pegar o id do switch com o tombo
		int id_sw  = VerificarSwitch_h(tombo);


		//Variavel para saber apartir de determinado ativo qual classe a ser instanciada
		String classe = ConsultaDAO.SelecionarClasseInterface(consulta.Modelo(snmp, target));
		Interfaces Ports = null;
		try {
			//Instanciando a classe correta para determinado modelo
			Ports = (Interfaces) Class.forName(classe).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
			String sql = "INSERT INTO interface_h (id_interface_switch, id_data, tipo_vlan, velocidade, estado, ligada, "
					+ "tipo_conector, id_interface_snmp, oid_interface_snmp, valor_interface, modificacao, "
					+ "aprova) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setLong(1, id_sw);
				if(tipoVlan.isEmpty()){
					ps.setString(2, "");
				}else{
					ps.setLong(2, data);
				}
				if(tipoVlan.isEmpty()){
					ps.setString(3, "");
				}else{
					//ps.setString(3, "");
					ps.setString(3, tipoVlan.get(i).toString());
				}
				if (velocidade.isEmpty()){
					ps.setString(4, "");
				}else{
					//ps.setString(3, "");
					ps.setString(4, velocidade.get(i).toString());
				}
				if (estadoPorta.isEmpty()){
					ps.setString(5, "");
				}else{
					//ps.setString(4, "");
					ps.setString(5, estadoPorta.get(i).toString());
				}
				if (ligada.isEmpty()) {
					ps.setString(6, "");
				}else{
					//ps.setString(5, "");
					ps.setString(6, ligada.get(i).toString());

				}
				if (tipo_conector.isEmpty()){
					ps.setString(7, "");
				}else{
					ps.setString(7, tipo_conector.get(i).toString());
					//ps.setString(6, "");
				}
				if (id.isEmpty()){
					ps.setString(8, "");
				}else{
					//ps.setString(7, "");
					ps.setInt(8, id.get(i));

				}
				if (oid.isEmpty()){
					ps.setString(9, "");
				}else{
					//ps.setString(8, "");
					ps.setString(9, oid.get(i).toString());

				}if (valor.isEmpty()){
					ps.setString(10, "");
				}else{
					ps.setString(10, valor.get(i).toString());
				}if (valor.isEmpty()){
					ps.setString(11, "");
				}else{
					ps.setString(11, modificacao);
				}if (valor.isEmpty()){
					ps.setString(12, "");
				}else{
					ps.setString(12, aprova);
				}


				ps.execute();
				ps.close();
			}catch(SQLException erro){
				throw new RuntimeException(erro);
			}
		}
	}

	@PUT
	public void atualizarInterface(int id_data, String id_switch, String modificacao, String tipo_vlan,
			Object velocidade, Object estado, Object ligada, Object tipo_connector,
			Object id_interface_snmp, Object oid_interface_snmp, Object valor_interface){
		String sql = "UPDATE interface set interface.id_data = '" + id_data + "', modificacao ='"+ modificacao
				+ "' tipo_vlan ='" + tipo_vlan + "' velocidade ='" + velocidade + "'estado ='" + estado
				+ "' ligada ='" + ligada + "' tipo_conector ='" + tipo_connector + "' id_interface_snmp ='"
				+ id_interface_snmp + "oid_interface_snmp ='" + oid_interface_snmp + "' valor_interface ='"
				+ valor_interface
				+ "' where id_switch = '" + id_switch + "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}



	@PUT
	public void atualizarInterface_h(int id_data, String id_switch, String modificacao, String tipo_vlan,
			Object velocidade, Object estado, Object ligada, Object tipo_connector,
			Object id_interface_snmp, Object oid_interface_snmp, Object valor_interface){
				String sql = "update interface_h set id_data = '" + id_data + "', modificacao ='"+ modificacao
				+ "' tipo_vlan ='" + tipo_vlan + "' velocidade ='" + velocidade + "'estado ='" + estado
				+ "' ligada ='" + ligada + "' tipo_conector ='" + tipo_connector + "' id_interface_snmp ='"
				+ id_interface_snmp + "oid_interface_snmp ='" + oid_interface_snmp + "' valor_interface ='"
				+ valor_interface
				+ "' where id_switch = '" + id_switch + "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}


	public void atualizarStatus(int id_data, Switch comutador, String aprova){
		String sql = "UPDATE interface_h set aprova ='"+ aprova
				+ "' where id_switch = '" + comutador.getId_switch() + "'";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public void removerPortas(String id_switch){
		//String serialtombo = daoSwitch.Selecionarid_switch(tombo);
		String sql = "DELETE from interface where id_switch = '" + id_switch + "';" ;
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}


	public Switch pegarTudo(String id_switch){
		//ArrayList<Switch> config = new ArrayList<Switch>();
		Object id_interface = new Object();
		Object oid_interface = new Object();
		Object estado = new Object();
		Object ligada = new Object();
		Object conector = new Object();
		Object id_porta = new Object();
		Object velocidade = new Object();
		Object valor_interface = new Object();
		Object tipoVlan = new Object();
		ArrayList<VlanSW> vlan = new ArrayList<VlanSW>();
		ArrayList<Porta> interfacess = new ArrayList<Porta>();
		//Integer encaminhador = 0;
		Object ip = new Object();
		Switch comutador = new Switch();

		ResultSet rs;
		String sql = "select id_interface_snmp, valor_interface, oid_interface_snmp, estado, "
				+ "ligada, tipo_conector, interface.id_porta, velocidade, tipo_vlan, ip, "
				+ "id_switch, vlan from interface interface INNER JOIN vlansw "
				+ "vlansw on vlansw.id_porta = interface.id_porta inner join switch switch "
				+ "on switch.id_switch = interface.id_interface_switch and switch.serialtombo= '"
				+ id_switch + "' order by id_porta;";
		System.out.println("Imprindo sql Atual " +sql);
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			Object id_porta_anterior = -1;
			while(rs.next()){
				VlanSW objVlan = new VlanSW();
				id_interface = (rs.getString("id_interface_snmp"));
				if(id_interface.equals(id_porta_anterior)){
					objVlan.setVlan(rs.getString("vlan"));
					vlan.add(objVlan);
				}else{
					vlan = new ArrayList<VlanSW>();
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
					int id_ativo = Integer.parseInt(rs.getString("id_switch"));
					 
							

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
					comutador.setId_switch(id_ativo);
					comutador.setIp(ip);
					id_porta_anterior = id_interface;
				}
			}

			//config.add(comutador);
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return comutador;
	}

	public ArrayList<Porta> pegarConfiguracao(String id_switch){
		//ArrayList<Switch> config = new ArrayList<Switch>();
				Object id_interface = new Object();
				Object oid_interface = new Object();
				Object estado = new Object();
				Object ligada = new Object();
				Object conector = new Object();
				Object id_porta = new Object();
				Object velocidade = new Object();
				Object valor_interface = new Object();
				Object tipoVlan = new Object();
				ArrayList<VlanSW> vlan = new ArrayList<VlanSW>();
				ArrayList<Porta> interfacess = new ArrayList<Porta>();
				Object encaminhador = new Object();
				Object ip = new Object();
				Switch comutador = new Switch();

				ResultSet rs;
				String sql = "select id_interface_snmp, valor_interface, oid_interface_snmp, estado, "
						+ "ligada, tipo_conector, interface.id_porta, velocidade, tipo_vlan, ip, "
						+ "id_switch, vlan from interface interface INNER JOIN vlansw "
						+ "vlansw on vlansw.id_porta = interface.id_porta inner join switch switch "
						+ "on switch.id_switch = interface.id_interface_switch and switch.serialtombo= '"
						+ id_switch + "' order by id_porta;";
				System.out.println("Imprindo sql Atual " +sql);
				try{
					Statement st = conexao.createStatement();
					rs = st.executeQuery(sql);
					Object id_porta_anterior = -1;
					while(rs.next()){
						VlanSW objVlan = new VlanSW();
						id_interface = (rs.getString("id_interface_snmp"));
						if(id_interface.equals(id_porta_anterior)){
							objVlan.setVlan(rs.getString("vlan"));
							vlan.add(objVlan);
						}else{
							vlan = new ArrayList<VlanSW>();
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
							//encaminhador = (rs.getString("id_switch"));

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

							//comutador.setInterfaces(interfacess);
							//comutador.setId_switch(encaminhador);
							//comutador.setIp(ip);
							id_porta_anterior = id_interface;
						}
					}

					//config.add(comutador);
					st.close();
					rs.close();
				}catch(SQLException erro){
					throw new RuntimeException(erro);
				}
				return interfacess;
			}


	public ArrayList<Porta> pegarConfiguracao_h(String id_switch, int id_data){
		//ArrayList<Switch> config = new ArrayList<Switch>();
				Object id_interface = new Object();
				Object oid_interface = new Object();
				Object estado = new Object();
				Object ligada = new Object();
				Object conector = new Object();
				Object id_porta = new Object();
				Object velocidade = new Object();
				Object valor_interface = new Object();
				Object tipoVlan = new Object();
				ArrayList<VlanSW> vlan = new ArrayList<VlanSW>();
				ArrayList<Porta> interfacess = new ArrayList<Porta>();
				Object encaminhador = new Object();
				Object ip = new Object();
				Switch comutador = new Switch();

				ResultSet rs;
				String sql = "select id_interface_snmp, valor_interface, oid_interface_snmp, estado, "
						+ "ligada, tipo_conector, interface_h.id_porta, velocidade, tipo_vlan, ip, "
						+ "id_switch, vlan from interface_h interface_h INNER JOIN vlansw_h "
						+ "vlansw_h on vlansw_h.id_porta = interface_h.id_porta inner join switch_h switch_h "
						+ "on switch_h.id_switch = interface_h.id_interface_switch and switch_h.serialtombo= '"
						+ id_switch + "' order by id_porta;";
				System.out.println("Imprindo sql Atual " +sql);
				try{
					Statement st = conexao.createStatement();
					rs = st.executeQuery(sql);
					Object id_porta_anterior = -1;
					while(rs.next()){
						VlanSW objVlan = new VlanSW();
						id_interface = (rs.getString("id_interface_snmp"));
						if(id_interface.equals(id_porta_anterior)){
							objVlan.setVlan(rs.getString("vlan"));
							vlan.add(objVlan);
						}else{
							vlan = new ArrayList<VlanSW>();
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

							//comutador.setInterfaces(interfacess);
							//comutador.setId_switch(encaminhador);
							//comutador.setIp(ip);
							id_porta_anterior = id_interface;
						}
					}

					//config.add(comutador);
					st.close();
					rs.close();
				}catch(SQLException erro){
					throw new RuntimeException(erro);
				}
				return interfacess;
			}

	public Switch pegarTudo_h(String id_switch, int id_data){
		//ArrayList<Switch> config = new ArrayList<Switch>();
		Object id_interface = new Object();
		Object oid_interface = new Object();
		Object estado = new Object();
		Object ligada = new Object();
		Object conector = new Object();
		Object id_porta = new Object();
		Object velocidade = new Object();
		Object valor_interface = new Object();
		Object tipoVlan = new Object();
		ArrayList<VlanSW> vlan = new ArrayList<VlanSW>();
		ArrayList<Porta> interfacess = new ArrayList<Porta>();
		Object encaminhador = new Object();
		Object ip = new Object();
		Switch comutador = new Switch();

		ResultSet rs;
		String sql = "select id_interface_snmp, valor_interface, oid_interface_snmp, estado, "
				+ "ligada, tipo_conector, interface_h.id_porta, velocidade, tipo_vlan, ip, "
				+ "id_switch, vlan from interface_h interface_h INNER JOIN vlansw_h "
				+ "vlansw_h on vlansw_h.id_porta = interface_h.id_porta inner join switch_h switch_h "
				+ "on switch_h.id_switch = interface_h.id_interface_switch and switch_h.serialtombo= '"
				+ id_switch + "' order by id_porta;";
		System.out.println("Imprindo sql Atual " +sql);
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			Object id_porta_anterior = -1;
			while(rs.next()){
				VlanSW objVlan = new VlanSW();
				id_interface = (rs.getString("id_interface_snmp"));
				if(id_interface.equals(id_porta_anterior)){
					objVlan.setVlan(rs.getString("vlan"));
					vlan.add(objVlan);
				}else{
					vlan = new ArrayList<VlanSW>();
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
					int id_ativo = Integer.parseInt(rs.getString("id_switch"));
					//encaminhador = (rs.getString("id_switch"));

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
					comutador.setId_switch(id_ativo);
					comutador.setIp(ip);
					id_porta_anterior = id_interface;
				}
			}

			//config.add(comutador);
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return comutador;
	}


	public ArrayList<Porta> StatusPorta(String id_porta, String id_switch){
		ArrayList<Porta> status = new ArrayList<Porta>();
		Object estado = new Object();
		Object velocidade = new Object();
		Object ligada = new Object();
		ResultSet rs;
		String sql = "select estado, velocidade, ligada from interface_h where serialtombo = " + id_switch + "" + " and id_porta =" + id_porta + ";";
		try{
			Statement st = conexao.createStatement();
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

			st.close();
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
		String sql = "select valor_interface from vlansw_h "
				+ "INNER JOIN interface_h on vlansw_h.id_porta = interface_h.id_porta and "
				+ "interface_h.id_interface_switch ='" + id_switch + "'" + " and vlansw_h.vlan='" + vlan + "'" + " "
						+ "and interface_h.estado ='Down';";
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Porta interfaces = new Porta();
				Interface = rs.getString("valor_interface");

				//valorInterface.add(rs.getString("valor_interface"));
				interfaces.setValor(Interface);
				list_portasLivres.add(interfaces);
				//list_portasLivres.add(comutador);
			}

			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return list_portasLivres;
	}

	//Metodo para pegar o portas ligadas ou desligadas
	public ArrayList<Porta> PortasEstado(String id_switch, String liga){
		ArrayList<Porta> list_portasLivres = new ArrayList<Porta>();
		Object Interface = new Object();
		ResultSet rs;
		String sql = "select valor_interface from vlansw_h INNER JOIN interface_h on vlansw_h.id_porta = "
				+ "interface_h.id_porta and interface_h.id_interface_switch ='" + id_switch + "'" + " "
						+ "and interface_h.estado ='" +liga +"';";
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				Porta interfaces = new Porta();
				Interface = rs.getString("valor_interface");

				//valorInterface.add(rs.getString("valor_interface"));
				interfaces.setValor(Interface);
				list_portasLivres.add(interfaces);
				//list_portasLivres.add(comutador);
			}

			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return list_portasLivres;
	}

	//Metodo para pegar o id do switch
	public int VerificarSwitch(String id_switch){
		ResultSet rs;
		String sql = "select id_switch from switch where id_switch = '" + id_switch + "';";
		int id = 0;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				//Switch comutador = new Switch();
				id = Integer.parseInt(rs.getString("id_switch"));

				//valorInterface.add(rs.getString("valor_interface"));
				//comutador.setId_switch(id);

				//list_portasLivres.add(comutador);
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;

	}

	public int VerificarSwitch_h(String id_switch){
		ResultSet rs;
		String sql = "select id_switch from switch_h where id_switch = '" + id_switch + "';";
		int id = 0;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				id = Integer.parseInt(rs.getString("id_switch"));
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return id;
	}

	public boolean ExisteSwitch(String id_switch){
		ResultSet rs;
		String sql = "select id_switch from switch where id_switch = '" + id_switch + "';";
		boolean bool = false;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				bool = true;
				//id = Integer.parseInt(rs.getString("id_switch"));
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		System.out.println("Função ExisteSwitch: " + bool);
		return bool;
	}

	public boolean ExisteSwitch_h(String id_switch){
		ResultSet rs;
		String sql = "select id_switch from switch_h where id_switch = '" + id_switch + "';";
		boolean bool = false;
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				bool = true;
				//id = Integer.parseInt(rs.getString("id_switch"));
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		System.out.println("Função ExisteSwitch_h: " + bool);
		return bool;
	}

	public ArrayList<String> selecionarIdPorta(String idSwitch) {
		ResultSet rs;
		String sql = "select id_porta from interface where id_interface_switch = '" + idSwitch + "';";
		ArrayList<String> idPorta = new ArrayList<String>();
		try{
			Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				//Switch comutador = new Switch();
				idPorta.add((rs.getString("id_interface_switch")));

				//valorInterface.add(rs.getString("valor_interface"));
				//comutador.setId_switch(id);

				//list_portasLivres.add(comutador);
			}
			st.close();
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return idPorta;
	}
}