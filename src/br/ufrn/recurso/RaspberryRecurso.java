package br.ufrn.recurso;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}