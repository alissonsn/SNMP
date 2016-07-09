package br.ufrn.recurso;

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

import br.ufrn.dao.DAOAndar;
import br.ufrn.dao.DAOVlan;
import br.ufrn.model.Andar;
import br.ufrn.model.Unidade;
import br.ufrn.model.Vlan;

@Path("/vlan")
public class VlanRecurso {
	private DAOVlan daoVlan = new DAOVlan();
	
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarVlan(String local){
		Gson gson = new Gson();
		Vlan vlan = gson.fromJson(local, Vlan.class);
		daoVlan.adicionarVlan(vlan);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Vlan> verVlans(){
		return daoVlan.listarVlans();
	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Vlan verVlan(@PathParam("id") String id){
		return daoVlan.listarVlan(id);
	}

	//Retorna true se existe a vlan e false caso contrario
	@GET
	@Path("/nome/{nome}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public boolean vlanValida(@PathParam("nome") String vlan){
		return daoVlan.vlanValida(vlan);
		}
	
	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void editarVlan(String local){
		Gson gson = new Gson();
		Vlan vlan = gson.fromJson(local, Vlan.class);
		daoVlan.editarVlan(vlan);
	}
	
	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deletarVlan(@PathParam("id") int id){
		daoVlan.deletarVlan(id);
	}
}