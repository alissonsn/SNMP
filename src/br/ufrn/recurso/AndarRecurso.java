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
import br.ufrn.model.Andar;
import br.ufrn.model.Sala;

@Path("/andar")
public class AndarRecurso {
	private DAOAndar daoAndar= new DAOAndar();
	
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarAndar(String local){
		Gson gson = new Gson();
		Andar andar = gson.fromJson(local, Andar.class);
		daoAndar.adicionarAndar(andar);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Andar> verAndares(){
		return daoAndar.listarAndares();
	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Andar verAndar(@PathParam("id") String id){
		return daoAndar.listarAndar(id);
	}

	@GET
	@Path("/consulta/pavimento/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Andar> verAndarPavimento(@PathParam("id") String id){
		return daoAndar.listarAndaresPavimento(id);
	}
	
	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void editarAndar(String local){
		Gson gson = new Gson();
		Andar andar = gson.fromJson(local, Andar.class);
		daoAndar.atualizarAndar(andar);
	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deletarAndar(@PathParam("id") int id){
		daoAndar.deletarAndar(id);
	}
}