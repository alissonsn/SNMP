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
import br.ufrn.dao.DAOMunicipio;
import br.ufrn.model.Municipio;

@Path("/local")
public class MunicipioRecurso {
	private DAOMunicipio daomunicipio = new DAOMunicipio();

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarMunicipio(String local){
		Gson gson = new Gson();
		Municipio cidade = gson.fromJson(local, Municipio.class);
		//municipio.setNome(cidade.getNome());
		daomunicipio.adicionarMunicipio(cidade);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Municipio> verMunicipios(){
		return daomunicipio.ListarMuncipios();
	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Municipio verMunicipio(@PathParam("id") String id){
		return daomunicipio.ListarMuncipio(id);
	}

	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void editarMunicipio(String local){
		Gson gson = new Gson();
		Municipio municipio = gson.fromJson(local, Municipio.class);
		daomunicipio.atualizarMunicipio(municipio);
	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deletarMunicipio(@PathParam("id") int id){
		daomunicipio.deletarMunicipio(id);
	}
}