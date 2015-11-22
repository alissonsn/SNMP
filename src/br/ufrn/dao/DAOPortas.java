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
				if (tipo_conector.isEmpty()) {
					ps.setString(5, "");
				}else{
					ps.setString(5, "");
					//ps.setString(5, tipo_conector.get(i).toString());
				}
				if (id.isEmpty()){
					ps.setString(6, "");
				}else{
					ps.setInt(6, id.get(i));
					//ps.setString(6, "");
				}
				if (oid.isEmpty()){
					ps.setString(7, "");
				}else{
					//ps.setString(7, "");
					ps.setString(7, oid.get(i).toString());
				}
				if (valor.isEmpty()){
					ps.setString(8, "");
				}else{
					ps.setString(8, "");
					//ps.setString(8, valor.get(i).toString());
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
		ArrayList<Object> id_interface = new ArrayList<Object>();
		ArrayList<Object> oid_interface = new ArrayList<Object>();
		ArrayList<Object> estado = new ArrayList<Object>();
		ArrayList<Object> conector = new ArrayList<Object>();
		ArrayList<Object> id_porta = new ArrayList<Object>();
		ArrayList<Object> velocidade = new ArrayList<Object>();
		ArrayList<Object> valor_interface = new ArrayList<Object>();
		ArrayList<Object> tipoVlan = new ArrayList<Object>();
		ArrayList<Object> vlan = new ArrayList<Object>();
		ArrayList<Object> list_id_switch = new ArrayList<Object>();
		Switch comutador = new Switch();

		ResultSet rs;
		String sql = "select * from vlan INNER JOIN interface on vlan.id_porta = interface.id_porta and interface.id_switch ='" + id_switch + "';";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){

				id_interface.add(rs.getString("id_interface_snmp"));
				oid_interface.add(rs.getString("oid_interface_snmp"));
				estado.add(rs.getString("estado"));
				conector.add(rs.getString("tipo_conector"));
				id_porta.add(rs.getString("id_porta"));
				velocidade.add(rs.getString("velocidade"));
				valor_interface.add(rs.getString("valor_interface"));
				tipoVlan.add(rs.getString("tipo_vlan"));
				vlan.add(rs.getString("vlan"));


				list_id_switch.add(Integer.parseInt(rs.getString("id_switch")));


				comutador.setId(id_interface);
				comutador.setOid(oid_interface);
				comutador.setEstadoPorta(estado);
				comutador.setTipo_conector(conector);
				comutador.setId_porta(id_porta);
				comutador.setVelocidade(velocidade);
				comutador.setPorta(valor_interface);
				comutador.setId_switch(list_id_switch);
				comutador.setTipoVlan(tipoVlan);
				comutador.setVlan(vlan);
				config.add(comutador);
				//vlans = rs.getString("vlan");
			}
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return config;
	}

	public ArrayList<Switch> StatusPorta(String id_porta, String id_switch){
		ArrayList<Switch> status = new ArrayList<Switch>();
		ArrayList<Object> velocidade = new ArrayList<Object>();
		ArrayList<Object> estado = new ArrayList<Object>();
		Switch comutador = new Switch();
		ResultSet rs;
		String sql = "select estado, velocidade from interface where id_switch = " + id_switch + "" + " and id_porta =" + id_porta + ";";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				estado.add(rs.getString("estado"));
				velocidade.add(rs.getString("velocidade"));
				comutador.setEstadoPorta(estado);
				comutador.setVelocidade(velocidade);
				status.add(comutador);
			}
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return status;
	}

	public ArrayList<Switch> PortasLivres(String id_switch, String vlan){
		ArrayList<Switch> list_portasLivres = new ArrayList<Switch>();
		ArrayList<Object> valorInterface = new ArrayList<Object>();
		Switch comutador = new Switch();
		ResultSet rs;
		String sql = "select valor_interface from vlan INNER JOIN interface on vlan.id_porta = interface.id_porta and interface.id_switch ='" + id_switch + "'" + " and vlan.vlan='" + vlan + "'" + " and interface.estado ='Down';";
		try{
			java.sql.Statement st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				valorInterface.add(rs.getString("valor_interface"));
				comutador.setPorta(valorInterface);
				list_portasLivres.add(comutador);
			}
			rs.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return list_portasLivres;

	}


}
