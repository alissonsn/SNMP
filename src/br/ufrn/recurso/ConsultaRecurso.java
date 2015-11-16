package br.ufrn.recurso;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import br.ufrn.dao.DAOPortas;
import br.ufrn.dao.DAOVlan;
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
	public void consultarSwitch(
			@FormParam("tombo") String tombo,
			@FormParam("ip") String ip,
			@FormParam("usuario") String usuario,
			@FormParam("senha") String senha
			){
		snmp = credenciais.snmp();
		target = credenciais.target(ip, usuario);
		credenciais.credenciais(usuario, senha);
		int tombo2 = Integer.parseInt(tombo);
		
		daoportas.adicionarswitch(tombo2);
		daoportas.adicionarInterface(snmp, target, consulta, tombo2);
		daovlan.adicionarVlan(snmp, target, consulta, tombo2);
		
		
	}
	
	
	
}
