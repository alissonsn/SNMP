package br.ufrn.recurso;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.ufrn.dao.DAOInterfaceRaspberry;
import br.ufrn.model.Interface_Raspberry;

@Path("/interface_raspberry")
public class InterfaceRaspberryRecurso {
	private DAOInterfaceRaspberry daoInterfaceRaspberry = new DAOInterfaceRaspberry();
	
	
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void adicionarInterfaceRaspberry(String local){
		Gson gson = new Gson();
		Interface_Raspberry interface_Raspberry = gson.fromJson(local, Interface_Raspberry.class);
		daoInterfaceRaspberry.adicionarInterfaceRaspbery(interface_Raspberry);
		
	}
}