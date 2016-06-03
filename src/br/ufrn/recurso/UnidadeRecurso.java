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

import br.ufrn.dao.DAOUnidade;
import br.ufrn.model.Unidade;

import com.google.gson.Gson;

@Path("/unidade")
public class UnidadeRecurso {
	private DAOUnidade daounidade= new DAOUnidade();

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarUnidade(String local){
		Gson gson = new Gson();
		Unidade unidade = gson.fromJson(local, Unidade.class);
		daounidade.adicionarUnidade(unidade);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Unidade> verUnidades(){
		return daounidade.ListarUnidades();
	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Unidade verUnidade(@PathParam("id") String id){
		return daounidade.ListarUnidade(id);
	}

	@GET
	@Path("/consulta/municipio/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Unidade> verUnidadeMunicipio(@PathParam("id") String id){
		return daounidade.ListarUnidadeMunicipio(id);
	}

	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void EditarUnidade(String local){
		Gson gson = new Gson();
		Unidade unidade = gson.fromJson(local, Unidade.class);
		daounidade.atualizarUnidade(unidade);
	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void DeletarUnidade(@PathParam("id") int id){
		daounidade.deletarUnidade(id);
	}
}