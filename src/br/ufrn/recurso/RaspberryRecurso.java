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

import br.ufrn.dao.DAORaspberry;
import br.ufrn.model.Raspberry;

@Path("/raspberry")
public class RaspberryRecurso {
	private DAORaspberry daoRaspberry = new DAORaspberry();
	
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarRaspberry(String local){
		Gson gson = new Gson();
		Raspberry raspberry = gson.fromJson(local, Raspberry.class);
		daoRaspberry.adicionarRaspbery(raspberry);
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