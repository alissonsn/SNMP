package operacoes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	/** Interface que contém as assinaturas de metodos para requisição http.
	 *
	 * @author silas
	 *
	 */

public interface Command {

	/**
	 *
	 * @param request, vai fazer o requisição da pagina,
	 * @param response, será a resposta do servidor.
	 * @return String com a pagina a ser redierecionada
	 * @throws ServletException
	 * @throws IOException
	 */
	String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
