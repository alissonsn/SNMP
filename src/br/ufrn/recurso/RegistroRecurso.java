package br.ufrn.recurso;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.ufrn.dao.DAORegistro;
import br.ufrn.model.Municipio;
import br.ufrn.model.Registro;
import br.ufrn.model.Switch;


@Path("/registro")
public class RegistroRecurso {
	Registro registro;
	DAORegistro daoRegistro;

	public RegistroRecurso(){
		registro = new Registro();
		try {
			daoRegistro = new DAORegistro();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void addSwitch(
			String modelo){
			Gson gson = new Gson();
			Registro model = gson.fromJson(modelo, Registro.class);

			registro.setEnterprise(model.getEnterprise());
			registro.setModelo(model.getModelo());
			registro.setClassePorta("br.ufrn.service." + model.getClassePorta());
			registro.setClasseVlan("br.ufrn.service." + model.getClasseVlan());
			daoRegistro.adicionarModelo(registro);
	}

	@GET
	@Path("/consulta")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Registro> verModelos(){
		return daoRegistro.ListarModelos();
	}

	@GET
	@Path("/consulta/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Registro verModelo(@PathParam("id") String id){
		return daoRegistro.ListarModelo(id);
	}

	@PUT
	@Path("/edit/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void ModificarModelos(String modelo){
		Gson gson = new Gson();
		Registro model = gson.fromJson(modelo, Registro.class);

		
		registro.setEnterprise(model.getEnterprise());
		registro.setModelo(model.getModelo());
		registro.setClassePorta("br.ufrn.service." + model.getClassePorta());
		registro.setClasseVlan("br.ufrn.service." + model.getClasseVlan());
		
		
		/*
		String classePorta = model.getClassePorta();
		String classevlan = model.getClasseVlan();
		String enterprise = model.getEnterprise();
		String mod = model.getModelo();


		model.setModelo(modelo);
		model.setEnterprise(enterprise);
		model.setClassePorta(classePorta);
		model.setClasseVlan(classevlan);*/
		daoRegistro.atualizarModelo(registro);

	}

	@DELETE
	@Path("/remove/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void DeletarModelo(@PathParam("id") int id){
		daoRegistro.deletarModelo(id);
	}
}