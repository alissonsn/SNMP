package br.ufrn.recurso;

import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.dao.DAOPortas;
import br.ufrn.dao.DAOVlan;
import br.ufrn.model.Switch;
import br.ufrn.service.Consulta;
import br.ufrn.service.Credenciais;


@Path("/consulta")
public class ConsultaRecurso {
	private Credenciais credenciais;
	private Consulta consulta;
	private DAOPortas daoportas;
	private DAOVlan daovlan;
	private Snmp snmp;
	private UserTarget target;

	public ConsultaRecurso(){
		credenciais = new Credenciais();
		consulta = new Consulta();
		try {
			daoportas = new DAOPortas();
			daovlan = new DAOVlan();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		snmp = new Snmp();
		target = new UserTarget();
	}

	@POST
	@Path("/switch")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String consultarSwitch(
			@FormParam("tombo") String tombo,
			@FormParam("ip") String ip,
			@FormParam("usuario") String usuario,
			@FormParam("senha") String senha
			){
		snmp = credenciais.snmp();
		target = credenciais.target(ip, usuario);
		if (snmp == null || target == null){
			return "ERRO";
		}
		credenciais.credenciais(usuario, senha);
		int tombo2 = Integer.parseInt(tombo);

		daoportas.adicionarswitch(tombo2);
		daoportas.adicionarInterface(snmp, target, consulta, tombo2);
		daovlan.adicionarVlan(snmp, target, consulta, tombo2);
		return "OK";
	}

	//Todas as vlans da universidade
	@GET
	@Path("/vlans")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> verVlans(){
		return daovlan.getAll();

	}

	//Pega as vlans de um determinado switch
	@GET
	@Path("/vlans/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> verTodasVlans(@PathParam("id") String id_switch){
		return daovlan.getVlan(id_switch);
	}

	//Retorna os switches que possuem esta vlan
	@GET
	@Path("/vlan/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> verSwitchVlan(@PathParam("id") String vlan){
		return daovlan.getSwitchVlan(vlan);
	}


	//Retorna todas as configura��es do switch
	@GET
	@Path("/switch/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> configSwitch(
			@PathParam("id") String id_switch){
		return  daoportas.pegarTudo(id_switch);
	}

	//Retorna todas as configura��es do switch
	@GET
	@Path("/porta/{id_porta}/{id_switch}/status")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Switch> StatusPorta(
			@PathParam("id_porta") String id_porta,
			@PathParam("id_switch") String id_switch)
			{
		return  daoportas.StatusPorta(id_porta, id_switch);
	}

	//Retorna todas as portas livres de determinada vlan
		@GET
		@Path("/porta/{vlan}/{id_switch}/equiv")
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		public ArrayList<Switch> PortaLivre(
				@PathParam("id_switch") String id_switch,
				@PathParam("vlan") String vlan)
				{
			return  daoportas.PortasLivres(id_switch, vlan);
		}


}
