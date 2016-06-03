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

import br.ufrn.dao.DAOPredio;
import br.ufrn.model.Predio;
import br.ufrn.model.Unidade;

import com.google.gson.Gson;

@Path("/predio")
public class PredioRecurso {
	private DAOPredio daopredio = new DAOPredio();

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarPredio(String local) {
		Gson gson = new Gson();
		Predio predio = gson.fromJson(local, Predio.class);
		daopredio.adicionarPredio(predio);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Predio> verPredios() {
		return daopredio.listarPredios();

	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Predio verPredio(@PathParam("id") String id) {
		return daopredio.listarPredio(id);
	}

	@GET
	@Path("/consulta/unidade/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Predio> verPredioUnidade(@PathParam("id") String id){
		return daopredio.listarPredioUnidade(id);
	}
	
	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void EditarPredio(String local) {
		Gson gson = new Gson();
		Predio predio = gson.fromJson(local, Predio.class);
		daopredio.atualizarPredio(predio);
	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void DeletarPredio(@PathParam("id") int id) {
		daopredio.deletarPredio(id);
	}
}