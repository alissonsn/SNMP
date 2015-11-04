package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import operacoes.Command;

	/** Classe responsavel manipular o model e view
	 *
	 * @author silas
	 *
	 */

public class Controller extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public Controller(){
		super();
	}

	/** Metodo que processa a requisição http
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	private void processarRequisicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Instancia a classe que vai dizer qual classe a ser instanciada
		Helper helper = new Helper(request);
		Command command = helper.getCommand();
		//Retorna uma view
		String pagina =  command.execute(request, response);
		//Retorna um response para para o cliente
		request.getRequestDispatcher(pagina).forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processarRequisicao(request, response);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processarRequisicao(request, response);
	}

}
