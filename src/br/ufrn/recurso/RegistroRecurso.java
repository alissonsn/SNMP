package br.ufrn.recurso;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrn.dao.DAORegistro;
import br.ufrn.service.Registro;


@Path("/registro")
public class RegistroRecurso {
	Registro registro;
	DAORegistro daoRegistro;


	public RegistroRecurso(){
		registro = new Registro();
		try {
			daoRegistro = new DAORegistro();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void addSwitch(
			@FormParam("enterprise") String enterprise,
			@FormParam("modelo") String modelo,
			@FormParam("portas") String portas,
			@FormParam("vlans") String vlans){

			registro.setEnterprise(enterprise);
			registro.setModelo(modelo);
			registro.setClassePorta("br.ufrn.service." + portas);
			registro.setClasseVlan("br.ufrn.service." + vlans);
			daoRegistro.adicionarModelo(registro);
	}

}
