package br.ufrn.recurso;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.ufrn.dao.DAORaspberry;
import br.ufrn.dao.DAOVlan;
import br.ufrn.model.Interface_Raspberry;
import br.ufrn.model.Raspberry;
import br.ufrn.model.Vlan;
import br.ufrn.model.VlanSW;

@Path("/raspberry")
public class RaspberryRecurso {
	private DAORaspberry daoRaspberry = new DAORaspberry();
	private DAOVlan daovlan = new DAOVlan();
	
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarRaspberry(String local){
		Gson gson = new Gson();
		Raspberry raspberry = gson.fromJson(local, Raspberry.class);
		
		/*
		Interface_Raspberry interface_Raspberry = new Interface_Raspberry();
		interface_Raspberry.setInterface_raspberry(raspberry.getInterface_raspberry());
		interface_Raspberry.setRaspberry(raspberry);
		
		if (daoRaspberry.rapsberryExistente(raspberry.getId_raspberry())) {
			daoRaspberry.adicionarInterfaceRaspbery(interface_Raspberry);
		}else{*/
			daoRaspberry.adicionarRaspbery(raspberry);
			//daoRaspberry.adicionarInterfaceRaspbery(interface_Raspberry);
		//}
	}
	
	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Raspberry> listarTodos(){
		return  daoRaspberry.listarRaspberries();
	}
	
	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Raspberry verRaspberry(@PathParam("id") String id){
		return daoRaspberry.listarRaspberriesSwitch(id);
	}
	
	@GET
	@Path("/consulta/teste/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Raspberry verRaspberr(@PathParam("id") String id){
		return daoRaspberry.listarRaspberriesSwitchh(id);
	}
	
	@GET
	@Path("/consulta/vlan/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Vlan> verRaspberryVlan(@PathParam("id") String id){
		Raspberry raspberry = daoRaspberry.listarRaspberriesSwitch(id);
		System.out.println("Vlans: "+raspberry.getComutador().getInterfaces().get(0).getVlan().get(0).getVlan());
		System.out.println("Vlans: "+raspberry.getComutador().getInterfaces().get(0).getVlan().get(1).getVlan());
		System.out.println("Vlans: "+raspberry.getComutador().getInterfaces().get(0).getVlan().get(2).getVlan());
		int tamanho = raspberry.getComutador().getInterfaces().get(0).getVlan().size();
		ArrayList<Vlan> listaVlan = new ArrayList<Vlan>();
		for (int i = 0; i < tamanho; i++) {
			VlanSW vlan = raspberry.getComutador().getInterfaces().get(0).getVlan().get(i);
			if (daovlan.vlanValida(vlan.getVlan().toString())) {
				System.out.println("Vlan: "+ daovlan.listarVlanNumero(vlan.getVlan().toString()));
				listaVlan.add(daovlan.listarVlanNumero(vlan.getVlan().toString()));
			}else{
				
			}	
			
		}
		
		return listaVlan;
	}
	
	
	
	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void editarRaspberry(String local){
		Gson gson = new Gson();
		Raspberry raspberry = gson.fromJson(local, Raspberry.class);
		daoRaspberry.atualizarRaspberry(raspberry);
	}
	
	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deletarRaspberry(@PathParam("id") int id){
		daoRaspberry.deletarRaspberry(id);
	}
	
}