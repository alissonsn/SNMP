package operacoes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Consulta;
import model.Credenciais;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

import Dao.DAOPortas;
import Dao.DAOVlan;

/** Classe que processará as requisições das views e chamará os models e DAOs.
*
* @author silas
*
*/

public class ConsultaSnmp implements Command{
	int tombo;
	String ip;
	String usuario;
	String senha;
	Snmp snmp;
	UserTarget target;
	Credenciais credencial;
	Consulta consulta;
	DAOPortas portaDAO;
	DAOVlan vlanDAO;

	public ConsultaSnmp(){
		tombo = 0;
		ip = new String();
		usuario = new String();
		senha = new String();
		snmp = new Snmp();
		target = new UserTarget();
		credencial = new Credenciais();
		consulta = new Consulta();

		try {
			portaDAO = new DAOPortas();
			vlanDAO = new DAOVlan();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Metodo pega requisição http servlet e e devolve uma string que a pagina a ser redirecionada
	 *
	 */

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		//Atributos vindos do formulario
		tombo = Integer.parseInt(request.getParameter("tombo"));
		ip = request.getParameter("ip");
		usuario = request.getParameter("usuario");
		senha = request.getParameter("senha");

		//instanciando as credenciais snmp
		snmp = credencial.snmp();
		target = credencial.target(ip, usuario);
		credencial.credenciais(usuario, senha);

		//Persisitindo os dados das portas e vlans
		portaDAO.adicionarswitch(tombo);
		portaDAO.adicionarInterface(snmp, target, consulta, tombo);
		vlanDAO.adicionarVlan(snmp, target, consulta, tombo);


		return "index.jsp";
	}

}
