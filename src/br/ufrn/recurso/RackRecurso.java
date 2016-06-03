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

import br.ufrn.dao.DAORack;
import br.ufrn.dao.DAOSala;
import br.ufrn.model.Municipio;
import br.ufrn.model.Pavimento;
import br.ufrn.model.Rack;
import br.ufrn.model.Sala;

import com.google.gson.Gson;

@Path("/rack")
public class RackRecurso {
	private DAORack daorack= new DAORack();
	

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarRack(String local){
		Gson gson = new Gson();
		Rack rack = gson.fromJson(local, Rack.class);
		daorack.adicionarRack(rack);
	}

	/*
	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Sala> verSalas(){
		return daosala.ListarSalas();
	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Sala verSala(@PathParam("id") String id){
		return daosala.ListarSala(id);
	}

	@GET
	@Path("/consulta/pavimento/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Sala> verSalaPavimento(@PathParam("id") String id){
		return daosala.ListarSalasPavimento(id);
	}
	
	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void editarSala(String local){
		Gson gson = new Gson();
		Sala sala = gson.fromJson(local, Sala.class);
		daosala.atualizarSala(sala);
	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deletarSala(@PathParam("id") int id){
		daosala.deletarSala(id);
	}
	*/
}