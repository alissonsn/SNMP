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

import br.ufrn.dao.DAOData;
import br.ufrn.dao.DAOPortas;
import br.ufrn.dao.DAOVlan;
import br.ufrn.model.Porta;
import br.ufrn.model.Switch;
import br.ufrn.model.Vlan;
import br.ufrn.service.Consulta;
import br.ufrn.service.Credenciais;


@Path("/consulta")
public class ConsultaRecurso {
	private Credenciais credenciais;
	private Consulta consulta;
	private DAOPortas daoportas;
	private DAOVlan daovlan;
	private DAOData daodata;
	private Snmp snmp;
	private UserTarget target;

	public ConsultaRecurso(){
		credenciais = new Credenciais();
		consulta = new Consulta();
		try {
			daoportas = new DAOPortas();
			daovlan = new DAOVlan();
			daodata = new DAOData();
		} catch (ClassNotFoundException e) {
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

		if (daoportas.VerificarSwitch(tombo).isEmpty()) {
			daoportas.adicionarswitch(tombo, ip);
			daoportas.adicionarInterface(snmp, target, consulta, tombo);
			daodata.adicionarData(tombo, "Modificado");
			daovlan.adicionarVlan(snmp, target, consulta, tombo);
			//daodata.adicionarData(tombo);
		}else if(daoportas.VerificarSwitch_h(tombo).isEmpty()){
			daoportas.adicionarswitch_h(tombo,ip);
			daoportas.adicionarInterface_h(snmp, target, consulta, tombo);
			daodata.adicionarData_h(tombo, "Modificado");
			daovlan.adicionarVlan_h(snmp, target, consulta, tombo);
			//daodata.adicionarData_h(tombo);
		}else if(!daoportas.VerificarSwitch(tombo).isEmpty() && !daoportas.VerificarSwitch_h(tombo).isEmpty()){
			daoportas.adicionarInterface_h(snmp, target, consulta, tombo);
			daovlan.adicionarVlan_h(snmp, target, consulta, tombo);
			daodata.adicionarData_h(tombo, "Modificado");
			//daodata.adicionarData_h(tombo);
		}


		return "OK";
	}



	//Todas as vlans da universidade
	@GET
	@Path("/vlans/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Vlan> verVlan(){
		return daovlan.getAll();
	}


	//Pega as vlans de um determinado switch
	@GET
	@Path("/vlans/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Vlan> verTodasVlans(@PathParam("id") String id_switch){
		return daovlan.getVlan(id_switch);
	}

	//Retorna os switches que possuem esta vlan
	@GET
	@Path("/vlan/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> verSwitchVlan(@PathParam("id") String vlan){
		return daovlan.getSwitchVlan(vlan);
	}

	//Retorna OK se o switch está atualizado senão retorna MODIFICADO
	@GET
	@Path("/switchs/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String compara(
			@PathParam("id") String id_switch){

		ArrayList<Switch> config = new ArrayList<Switch>();
		String bool = "";
		config.addAll(daoportas.pegarTudo(id_switch));
		config.addAll(daoportas.pegarTudo_h(id_switch));

		ArrayList<Porta> portas = config.get(0).getInterfaces();
		//ArrayList<Porta> portas2 = config.get(1).getInterfaces();

		//ArrayList<Vlan> vlans = config.get(0).getInterfaces().get(0).getVlan();
		//ArrayList<Vlan> vlans2 = config.get(1).getInterfaces().get(0).getVlan();


		//Varre o vetor de portas atuais do switch
		for (int i = 0; i < portas.size(); i++) {
			ArrayList<Vlan> vlans = config.get(0).getInterfaces().get(i).getVlan();
			ArrayList<Vlan> vlans2 = config.get(1).getInterfaces().get(i).getVlan();
			//Varre o vetor de vlans atuais de cada porta do switch

			//Varre o vetor de portas anterior do switch
			for (int j = 0; j < vlans.size(); j++) {
				//Varre o vetor de vlans anterior de cada porta do switch
				for (int l = 0; l < vlans2.size(); l++) {
					//Compara se o ip da configuração atual é igual da anterior
					if (config.get(0).getIp().equals(config.get(1).getIp()) &&
							//Compara se o id do switch da configuração atual é igual da anterior
							config.get(0).getId_switch().equals(config.get(1).getId_switch()) &&
							//Compara se o id da porta do switch da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getId_porta().equals(config.get(1).getInterfaces().get(0).getId_porta()) &&
							//Compara se o nome da porta da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getValor().equals(config.get(1).getInterfaces().get(0).getValor()) &&
							//Compara se o oid de consulta a esta porta da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getOid().equals(config.get(1).getInterfaces().get(0).getOid()) &&
							//Compara se o id snmp para consulta da porta da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getId().equals(config.get(1).getInterfaces().get(0).getId()) &&
							//Compara se a velocidade da porta da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getVelocidade().equals(config.get(1).getInterfaces().get(0).getVelocidade()) &&
							//Compara se o estado da porta da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getEstadoPorta().equals(config.get(1).getInterfaces().get(0).getEstadoPorta()) &&
							//Compara se a porta ligada da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getLigada().equals(config.get(1).getInterfaces().get(0).getLigada()) &&
							//Compara se o tipo do conector da configuração atual é igual da anterior
							config.get(0).getInterfaces().get(0).getTipo_conector().equals(config.get(1).getInterfaces().get(0).getTipo_conector()) &&
							config.get(0).getInterfaces().get(i).getVlan().size() == config.get(1).getInterfaces().get(i).getVlan().size() && 
							config.get(0).getInterfaces().get(i).getVlan().get(j).getVlan().equals(config.get(1).getInterfaces().get(i).getVlan().get(l).getVlan()))
							
							//Compara se o tipo da vlan da configuração atual é igual da anterior
							//config.get(0).getInterfaces().get(0).getVlan().get(0).equals(config.get(1).getInterfaces().get(0).getVlan().get(0)
							

							//(config.get(0).getInterfaces().get(i).getVlan().size() == config.get(1).getInterfaces().get(i).getVlan().size()
							//config.get(0).getInterfaces().get(i).getVlan().get(k).equals(config.get(1).getInterfaces().get(l)
									{
					
						/*if (config.get(0).getInterfaces().get(0).getVlan().size() != config.get(1).getInterfaces().get(0).getVlan().size() &&
							config.get(0).getInterfaces().get(0).getVlan().get(j).equals(config.get(1).getInterfaces().get(0).getVlan().get(l)))
					{*/
						//Compara se o numero de vlans da configuração atual é igual da anterior
							
							

						//}
						bool = "Atualizado";
						System.out.println("Switch 1: " + config.get(0).getInterfaces().get(i).getValor());
						System.out.println("Switch 2: " + config.get(1).getInterfaces().get(i).getValor());
						System.out.println("Switch 1: vlan: " + config.get(0).getInterfaces().get(i).getVlan().get(0).getVlan());
						System.out.println("Switch 2: vlan: " + config.get(1).getInterfaces().get(i).getVlan().get(0).getVlan());
					}else{
						bool = "Modificado";
						System.out.println("Switch 1: " + config.get(0).getInterfaces().get(i));
						System.out.println("Switch 2: " + config.get(1).getInterfaces().get(i));
						System.out.println("Switch 1: vlan: " + config.get(0).getInterfaces().get(i).getVlan().get(j));
						System.out.println("Switch 2: vlan: " + config.get(1).getInterfaces().get(i).getVlan().get(l));
						break;
					}
					vlans.clear();

				}
			}
			
			
		}

		return  bool;
	}


	//Retorna OK se o switch está atualizado senão retorna MODIFICADO
		@POST
		@Path("/consulta/{id}")
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		public String compa(
				@PathParam("id") String id_switch){

			ArrayList<Switch> config = new ArrayList<Switch>();
			String bool = "";
			config.addAll(daoportas.pegarTudo(id_switch));
			config.addAll(daoportas.pegarTudo_h(id_switch));

			ArrayList<Porta> portas = config.get(0).getInterfaces();
			//ArrayList<Porta> portas2 = config.get(1).getInterfaces();

			//ArrayList<Vlan> vlans = config.get(0).getInterfaces().get(0).getVlan();
			//ArrayList<Vlan> vlans2 = config.get(1).getInterfaces().get(0).getVlan();


			//Varre o vetor de portas atuais do switch
			for (int i = 0; i < portas.size(); i++) {
				ArrayList<Vlan> vlans = config.get(0).getInterfaces().get(i).getVlan();
				ArrayList<Vlan> vlans2 = config.get(1).getInterfaces().get(i).getVlan();
				//Varre o vetor de vlans atuais de cada porta do switch

				//Varre o vetor de portas anterior do switch
				for (int j = 0; j < vlans.size(); j++) {
					//Varre o vetor de vlans anterior de cada porta do switch
					for (int l = 0; l < vlans2.size(); l++) {
						//Compara se o ip da configuração atual é igual da anterior
						if (config.get(0).getIp().equals(config.get(1).getIp()) &&
								//Compara se o id do switch da configuração atual é igual da anterior
								config.get(0).getId_switch().equals(config.get(1).getId_switch()) &&
								//Compara se o id da porta do switch da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getId_porta().equals(config.get(1).getInterfaces().get(0).getId_porta()) &&
								//Compara se o nome da porta da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getValor().equals(config.get(1).getInterfaces().get(0).getValor()) &&
								//Compara se o oid de consulta a esta porta da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getOid().equals(config.get(1).getInterfaces().get(0).getOid()) &&
								//Compara se o id snmp para consulta da porta da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getId().equals(config.get(1).getInterfaces().get(0).getId()) &&
								//Compara se a velocidade da porta da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getVelocidade().equals(config.get(1).getInterfaces().get(0).getVelocidade()) &&
								//Compara se o estado da porta da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getEstadoPorta().equals(config.get(1).getInterfaces().get(0).getEstadoPorta()) &&
								//Compara se a porta ligada da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getLigada().equals(config.get(1).getInterfaces().get(0).getLigada()) &&
								//Compara se o tipo do conector da configuração atual é igual da anterior
								config.get(0).getInterfaces().get(0).getTipo_conector().equals(config.get(1).getInterfaces().get(0).getTipo_conector()) &&
								config.get(0).getInterfaces().get(i).getVlan().size() == config.get(1).getInterfaces().get(i).getVlan().size() && 
								config.get(0).getInterfaces().get(i).getVlan().get(j).getVlan().equals(config.get(1).getInterfaces().get(i).getVlan().get(l).getVlan()))
								
								//Compara se o tipo da vlan da configuração atual é igual da anterior
								//config.get(0).getInterfaces().get(0).getVlan().get(0).equals(config.get(1).getInterfaces().get(0).getVlan().get(0)
								

								//(config.get(0).getInterfaces().get(i).getVlan().size() == config.get(1).getInterfaces().get(i).getVlan().size()
								//config.get(0).getInterfaces().get(i).getVlan().get(k).equals(config.get(1).getInterfaces().get(l)
										{
						
							/*if (config.get(0).getInterfaces().get(0).getVlan().size() != config.get(1).getInterfaces().get(0).getVlan().size() &&
								config.get(0).getInterfaces().get(0).getVlan().get(j).equals(config.get(1).getInterfaces().get(0).getVlan().get(l)))
						{*/
							//Compara se o numero de vlans da configuração atual é igual da anterior
								
								

							//}
							bool = "Atualizado";
							System.out.println("Switch 1: " + config.get(0).getInterfaces().get(i).getValor());
							System.out.println("Switch 2: " + config.get(1).getInterfaces().get(i).getValor());
							System.out.println("Switch 1: vlan: " + config.get(0).getInterfaces().get(i).getVlan().get(0).getVlan());
							System.out.println("Switch 2: vlan: " + config.get(1).getInterfaces().get(i).getVlan().get(0).getVlan());
						}else{
							bool = "Modificado";
							System.out.println("Switch 1: " + config.get(0).getInterfaces().get(i));
							System.out.println("Switch 2: " + config.get(1).getInterfaces().get(i));
							System.out.println("Switch 1: vlan: " + config.get(0).getInterfaces().get(i).getVlan().get(j));
							System.out.println("Switch 2: vlan: " + config.get(1).getInterfaces().get(i).getVlan().get(l));
							break;
						}
						vlans.clear();

					}
				}
				
				
			}

			return  bool;
		}





	//Retorna todas as configura��es do switch da tablea historico
	@GET
	@Path("/switch/di/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> compar(
			@PathParam("id") String id_switch){

		ArrayList<Switch> config = new ArrayList<Switch>();
		config.addAll(daoportas.pegarTudo(id_switch));
		config.addAll(daoportas.pegarTudo_h(id_switch));



		return  config;
	}

	@GET
	@Path("/switch/dif/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Porta compar1(
			@PathParam("id") String id_switch){

		ArrayList<Switch> config = new ArrayList<Switch>();
		config.addAll(daoportas.pegarTudo(id_switch));
		config.addAll(daoportas.pegarTudo_h(id_switch));

		return  config.get(0).getInterfaces().get(1);
	}

	//Retorna a porta especificada do switch
	@GET
	@Path("/switch/diff/{id}/{id_porta}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Porta compar2(
			@PathParam("id") String id_switch,
			@PathParam("id_porta") int id_porta)
	{

		ArrayList<Switch> config = new ArrayList<Switch>();
		config.addAll(daoportas.pegarTudo(id_switch));
		config.addAll(daoportas.pegarTudo_h(id_switch));



		return  config.get(0).getInterfaces().get(id_porta-1);
	}

	//Retorna as vlans da porta primeira porta do switch
	@GET
	@Path("/switch/vlans/atual/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Vlan> compar3(
			@PathParam("id") String id_switch){

		ArrayList<Switch> config = new ArrayList<Switch>();
		config.addAll(daoportas.pegarTudo(id_switch));
		config.addAll(daoportas.pegarTudo_h(id_switch));



		return  config.get(0).getInterfaces().get(0).getVlan();
	}
	
	//Retorna as vlans da porta primeira porta do switch
		@GET
		@Path("/switch/vlans/anterior/{id}")
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		public ArrayList<Vlan> compar7(
				@PathParam("id") String id_switch){

			ArrayList<Switch> config = new ArrayList<Switch>();
			config.addAll(daoportas.pegarTudo(id_switch));
			config.addAll(daoportas.pegarTudo_h(id_switch));


			return  config.get(1).getInterfaces().get(0).getVlan();
		}
	
	
	//Retorna as vlans da porta primeira porta do switch
	@GET
	@Path("/switch/diffss/{id}/{id_vlan}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Vlan compar4(
			@PathParam("id") String id_switch,
			@PathParam("id_vlan") int id_vlan
			){

		ArrayList<Switch> config = new ArrayList<Switch>();
		config.addAll(daoportas.pegarTudo(id_switch));
		config.addAll(daoportas.pegarTudo_h(id_switch));



		return  config.get(0).getInterfaces().get(0).getVlan().get(id_vlan);
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
	public ArrayList<Porta> StatusPorta(
			@PathParam("id_porta") String id_porta,
			@PathParam("id_switch") String id_switch)
			{
		return  daoportas.StatusPorta(id_porta, id_switch);
			}

	//Retorna todas as portas livres de determinada vlan
	@GET
	@Path("/porta/{vlan}/{id_switch}/equiv")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ArrayList<Porta> PortaLivre(
			@PathParam("id_switch") String id_switch,
			@PathParam("vlan") String vlan)
			{
		return  daoportas.PortasLivres(id_switch, vlan);
			}


}
