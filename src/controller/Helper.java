package controller;

import javax.servlet.http.HttpServletRequest;

import operacoes.*;

	/** Classe especializada em detectar qual serviço executar dependendo da mensagem que ela receber.
	 *
	 * @author silas
	 *
	 */

public class Helper {

	private HttpServletRequest request;

	public Helper(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/** Ele instanciará uma classe de acordo com a mensagem recebida
	 *
	 * @return comando = retorna uma instancia de uma classe
	 * comando é uma interface para requisição http
	 */

	public Command getCommand() {
		String cmd = request.getParameter("cmd");
		Command comando = null;


		if (cmd.equals("Registro")) {
			comando = new Registros();
		}
		if (cmd.equals("consulta_snmp")) {
			comando = new ConsultaSnmp();
		}
		if(cmd.equals("")){

		}

		return comando;
	}

}
