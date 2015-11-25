package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.interfaces.Interfaces;
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
		ArrayList<Object> ligada = Ports.Porta_ligada(snmp, target);
		ArrayList<Object> tipo_conector = Ports.Tipo_conector(snmp, target);
		ArrayList<Object> tipoVlan = Ports.TypeVlan(snmp, target);

		//varrendo o arrylist das portas
		for (int i = 0; i < valor.size(); i++) {
			//Fazendo uma string com o comando para inserir os dados na tabela interface
			String sql = "INSERT INTO interface (id_switch, tipo_vlan, velocidade, estado, ligada, tipo_conector, id_interface_snmp, oid_interface_snmp, valor_interface) VALUES (?,?,?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = conexao.prepareStatement(sql);
				ps.setLong(1, tombo);
				if(tipoVlan.isEmpty()){
					ps.setString(2, "");
				}else{
					//ps.setString(2, "");
					ps.setString(2, tipoVlan.get(i).toString());
				}
				if (velocidade.isEmpty()){
					ps.setString(3, "");
				}else{
					//ps.setString(3, "");
					ps.setString(3, velocidade.get(i).toString());
				}
				if (estadoPorta.isEmpty()){
					ps.setString(4, "");
				}else{
					//ps.setString(4, "");
					ps.setString(4, estadoPorta.get(i).toString());
				}
				if (ligada.isEmpty()) {
					ps.setString(5, "");
				}else{
					//ps.setString(5, "");
					ps.setString(5, ligada.get(i).toString());
					
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

	public ArrayList<Switch> pegarTudo(String id_switch){
		ArrayList<Switch> config = new ArrayList<Switch>();
		Object id_interface = new Object();
		Object oid_interface = new Object();
		Object estado = new Object();
		Object conector = new Object();
		Object id_porta = new Object();
		Object velocidade = new Object();
		Object valor_interface = new Object();
		Object tipoVlan = new Object();
		ArrayList<Vlan> vlan = new ArrayList<Vlan>();
		ArrayList<Porta> interfacess = new ArrayList<Porta>();
		Object encaminhador = new Object();
		Switch comutador = new Switch();

		ResultSet rs;
		String sql = "select * from vlan INNER JOIN interface on vlan.id_porta = interface.id_porta and interface.id_switch ='" + id_switch + "';";
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
						conector = (rs.getString("tipo_conector"));
						id_porta = (rs.getString("id_porta"));
						velocidade = (rs.getString("velocidade"));
						valor_interface = (rs.getString("valor_interface"));
						tipoVlan = (rs.getString("tipo_vlan"));
						objVlan.setVlan(rs.getString("vlan"));
						vlan.add(objVlan);
						encaminhador = (rs.getString("id_switch"));

						interfaces.setId(id_interface);
						interfaces.setOid(oid_interface);
						interfaces.setEstadoPorta(estado);
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
		String sql = "select estado, velocidade, ligada from interface where id_switch = " + id_switch + "" + " and id_porta =" + id_porta + ";";
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
		String sql = "select valor_interface from vlan INNER JOIN interface on vlan.id_porta = interface.id_porta and interface.id_switch ='" + id_switch + "'" + " and vlan.vlan='" + vlan + "'" + " and interface.estado ='Down';";
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


}
