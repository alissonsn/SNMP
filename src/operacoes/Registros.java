package operacoes;


import model.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.DAORegistro;

/** Classe que processará as requisições das views e chamará os models e DAOs.
*
* @author silas
*
*/

public class Registros implements Command {

	String enterprise;
	String modelo;
	String classePorta;
	String classeVlan;


	public Registros(){
		enterprise = new String();
		modelo = new String();
		classePorta = new String();
		classeVlan = new String();
	}

	/** Metodo pega requisição http servlet e e devolve uma string que a pagina a ser redirecionada
	 *
	 */
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//Atributos vindos do formulario
		enterprise = request.getParameter("enterprise");
		modelo = request.getParameter("modelo");
		classePorta = request.getParameter("portas");
		classeVlan = request.getParameter("vlans");

		try{

			//Objeto Registro
			Registro registro = new Registro();
			registro.setEnterprise(enterprise);
			registro.setModelo(modelo);
			registro.setClassePorta("model." + classePorta);
			registro.setClasseVlan("model." + classeVlan);

			//Persistindo o objeto registro
			DAORegistro registrar = new DAORegistro();
			registrar.adicionarModelo(registro);

		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		return "index.jsp";
	}

}

