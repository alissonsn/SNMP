package br.ufrn.recurso;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import com.google.gson.Gson;

import br.ufrn.dao.DAOConsulta;
import br.ufrn.dao.DAOData;
import br.ufrn.dao.DAOPortas;
import br.ufrn.dao.DAOSwitch;
import br.ufrn.dao.DAOVlan;
import br.ufrn.interfaces.Interfaces;
import br.ufrn.model.Porta;
import br.ufrn.model.Registro;
import br.ufrn.model.Switch;
import br.ufrn.model.Vlan;
import br.ufrn.service.Consulta;
import br.ufrn.service.Credenciais;
import br.ufrn.service.PortaH3CService;


@Path("/consulta")
public class ConsultaRecurso {
	private Credenciais credenciais;
	private Consulta consulta;
	private DAOPortas daoportas;
	private DAOVlan daovlan;
	private DAOData daodata;
	private DAOSwitch daoswitch;
	private Snmp snmp;
	private UserTarget target;
	private String modificacao;
	private String Aprova;
	private Switch sw;
	private Switch sw_h;
	private boolean comparacao;


	public ConsultaRecurso(){
		credenciais = new Credenciais();
		consulta = new Consulta();
		try {
			daoportas = new DAOPortas();
			daovlan = new DAOVlan();
			daodata = new DAOData();
			daoswitch = new DAOSwitch();
			modificacao = "";
			Aprova = "";
			sw = new Switch();
			sw_h = new Switch();

			comparacao = false;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		snmp = new Snmp();
		target = new UserTarget();
	}

	@POST
	@Path("/switch")
	@Produces({ MediaType.TEXT_PLAIN })
	public String consultarSwitch(
			String ativo
			) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Gson gson = new Gson();
		Switch comutador = gson.fromJson(ativo, Switch.class);
		System.out.println("Nome da sala "+ comutador.getSala().getNome());
		
		snmp = credenciais.snmp();
		target = credenciais.target(comutador.getIp().toString(), comutador.getUsuario().toString());
		String data = "";

		data = daodata.dataHoje();
		int SelecionaridData = daodata.SelecionaridData(data);
		System.out.println("DATA " + data);
		System.out.println(SelecionaridData);
		if (SelecionaridData == 0) {
			daodata.adicionarData();
			SelecionaridData = daodata.SelecionaridData(data);
		}else{
			SelecionaridData = daodata.SelecionaridData(data);
		}

		System.out.println("DATA " + data);
		System.out.println(SelecionaridData);

		if (snmp == null || target == null){
			return "ERRO";
		}else{
			credenciais.credenciais(comutador.getUsuario().toString(), comutador.getSenha().toString());

			//Se não tiver registro do switch coloca ele na tabela switch
		    if ( !daoportas.ExisteSwitch(comutador.getSerialtombo().toString()) ) {
		    	
				modificacao = "Atualizado";
				Aprova = "Aprovado";

				daoportas.adicionarswitch(comutador);
				daoportas.adicionarswitch_h(comutador);


				//System.out.println("SelecionaridData " + SelecionaridData);
				daoportas.adicionarInterface(
						snmp, target, consulta, comutador.getSerialtombo().toString(), modificacao , SelecionaridData, Aprova);
				daoportas.adicionarInterface_h(
					snmp, target, consulta, comutador.getSerialtombo().toString(), modificacao , SelecionaridData, Aprova);

				daovlan.adicionarVlan(snmp, target, consulta, comutador.getSerialtombo().toString());
				daovlan.adicionarVlan_h(snmp, target, consulta, comutador.getSerialtombo().toString());

			}else{
				daoportas.adicionarInterface_h(
						snmp, target, consulta, comutador.getSerialtombo().toString(), modificacao , SelecionaridData, Aprova);
				daovlan.adicionarVlan_h(snmp, target, consulta, comutador.getSerialtombo().toString());
				//ArrayList<Object> tipoVlan = Ports.TypeVlan(snmp, target);


				//int SelecionaridData = daodata.SelecionaridData(data);


				System.out.println("SelecionaridData "+ SelecionaridData);

				sw = daoportas.pegarTudo(comutador.getSerialtombo().toString());

				int SelecionarUltimadata = daodata.SelecionarUltimadata(
						daoswitch.Selecionarid_switch(comutador.getSerialtombo().toString()) );
				//swith.setInterfaces(portas);

				sw_h = daoportas.pegarTudo_h(comutador.getSerialtombo().toString(), SelecionarUltimadata);
				System.out.println("SelecionarUltimadata  "+ SelecionarUltimadata );
				System.out.println("sw: " + sw.hashCode());
				System.out.println("sw_h: " + sw_h.hashCode());
				//System.out.println("Size switch atual "+ sw.get(0).getInterfaces().size());
				//System.out.println("Size switch mais atual "+ sw_h.get(0).getInterfaces().size());
				comparacao = daoswitch.CompararSwitches(sw, sw_h);
				if(comparacao){
					System.out.println("Comparacao: " + daoswitch.CompararSwitches(sw, sw_h));
					modificacao = "Atualizado";

					//int id_data2 = daodata.SelecionaridData2(data);

					//daodata.AtualizarDataInterface(daoswitch.Selecionarid_switch(comutador.getSerialtombo().toString()), SelecionaridData);
					//daoportas.atualizarStatus(SelecionaridData, daoswitch.Selecionarid_switch(comutador.getSerialtombo().toString()), modificacao);

				}else if (!comparacao){
					System.out.println("Comparacao: " + daoswitch.CompararSwitches(sw, sw_h));
					modificacao = "Modificado";

					//daodata.AtualizarDataInterface(daoswitch.Selecionarid_switch(comutador.getSerialtombo().toString()), SelecionaridData);
					//daoportas.atualizarStatus(SelecionaridData, daoswitch.Selecionarid_switch(comutador.getSerialtombo().toString()), modificacao);
					//daoportas.adicionarInterface_h(
							//snmp, target, consulta, tombo, modificacao, SelecionaridData);
					//daovlan.adicionarVlan_h(snmp, target, consulta, tombo);



					//int id_data2 = daodata.SelecionaridData2(data);
					//daoportas.atualizarStatus(
						//	SelecionaridData, daoswitch.Selecionarid_switch(tombo), modificacao);


				}

			}

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

	@GET
	@Path("/switch/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> compar(
			@PathParam("id") String id_switch){

		ArrayList<Switch> config = new ArrayList<Switch>();
		config.add(daoportas.pegarTudo(id_switch));
		//System.out.println(daodata.dataHoje());
		//System.out.println(daodata.SelecionaridData(daodata.dataHoje()));
		config.add(daoportas.pegarTudo_h(id_switch, daodata.SelecionaridData(daodata.dataHoje())));

		return  config;
	}

	@GET
	@Path("/switche/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Switch com(
			@PathParam("id") String id_switch){
		return  daoportas.pegarTudo(id_switch);
	}
	
	@GET
	@Path("/todos")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Switch> listarTodos(){
		return  daoswitch.listarSwitch();
	}
	
	
	@PUT
	@Path("/switchs/{msg}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void comp(
			String ativo, @PathParam("msg") String msg){

				Gson gson = new Gson();
				Switch comutador = gson.fromJson(ativo, Switch.class);
				
				
				String data = "";
				data = daodata.dataHoje();
				int SelecionaridData = daodata.SelecionaridData(data);
				//String msg = comutador.getInterfaces().get(0).getAprova().toString();
				
				daodata.AtualizarDataInterface(comutador, SelecionaridData);
				daoportas.atualizarStatus(SelecionaridData,comutador, msg);
	}



	@GET
	@Path("/config/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Porta> configuracao(
			@PathParam("id") String id_switch){

		ArrayList<Porta> config = new ArrayList<Porta>();
		config = (daoportas.pegarConfiguracao(id_switch));
		//config = (daoportas.pegarConfiguracao_h(id_switch, daodata.SelecionaridData(daodata.dataHoje())));
		//config.add(daoportas.pegarTudo_h(id_switch, daodata.SelecionaridData(daodata.dataHoje())));
		return  config;
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

		//Retorna todas as portas livres de determinada vlan
		@GET
		@Path("/porta/{id_switch}/{estado}")
		@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
		public ArrayList<Porta> PortaEstado(
					@PathParam("id_switch") String id_switch,
					@PathParam("estado") String estado)
					{
				return  daoportas.PortasEstado(id_switch, estado);
		}

/*
	//Retorna todas as configuracoes do switch da tablea historico
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



	//Retorna todas as configuracoes do switch
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
*/

}
