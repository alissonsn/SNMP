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

import br.ufrn.dao.DAOPavimento;
import br.ufrn.model.Pavimento;
import br.ufrn.model.Predio;
import br.ufrn.model.Unidade;

@Path("/pavimento")
public class PavimentoRecurso {
	private DAOPavimento daopavimento = new DAOPavimento();

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarpavimento(String local){
		Gson gson = new Gson();
		Pavimento pavimento = gson.fromJson(local, Pavimento.class);
		//municipio.setNome(cidade.getNome());
		daopavimento.adicionarPavimento(pavimento);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Pavimento> verPavimentos(){
		return daopavimento.ListarPavimentos();

	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Pavimento verPavimento(@PathParam("id") String id){
		return daopavimento.ListarPavimento(id);
	}

	@GET
	@Path("/consulta/predio/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Pavimento> verPavimentoPredio(@PathParam("id") String id){
		return daopavimento.ListarPavimentoPredio(id);
	}
	
	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void EditarPavimento(String local){
		Gson gson = new Gson();
		Pavimento pavimento = gson.fromJson(local, Pavimento.class);
		daopavimento.atualizarPavimento(pavimento);
	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void DeletarPavimento(@PathParam("id") int id){
		daopavimento.deletarPavimento(id);
	}
}