package model;

import java.util.ArrayList;
import java.util.List;


import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

/** Esta classe tem a função de identificar qual enterprise determinado ativo de rede tem.
 *
 * @author silas
 *
 */

public class Consulta {
	ArrayList<Object> strList;
	int inicio;
	String result;
	public Consulta(){
		strList = new ArrayList<Object>();
		inicio = 0;
		result = "";
	}

	/** Este metodo consulta a OID enterprise do ativo de rede com o intuito de buscar as configurações deste por esta OID futuramente.
	 *
	 * @param snmp Ela fornece as funções de enviar e receber SNMP PDUs.
	 * @param target Credenciais para o acesso snmp nos ativos
	 * @return String - numero do OID enterprise, exemplo de saida 25506 para ativos da H3C 43 para ativos da 3com etc...
	 */


	public String Modelo(Snmp snmp, UserTarget target) {
		//OID para saber qual o fabricante
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.1.2")
		};
		//Classe que fornece funções de utilidade para recuperar dados SNMP tabulares.
		TableUtils tUtils = new TableUtils(snmp,new DefaultPDUFactory());
		//Obtém dados SNMP tabulares sincrono de uma ou mais tabelas.
		//Os dados são retornados linha por linha como uma lista de instâncias TableEvent.
		//Cada instância representa uma linha (ou uma condição de erro).
		//Além do agente alvo, os OIDs dos objetos colunar tem que ser especificado para quais instâncias devem ser recuperados.
		//Com um índice de limite inferior e um índice limite superior.
		List<TableEvent> events=tUtils.getTable(target, interfaces ,null,null);
		for (TableEvent event : events) {
			//Varrerá linha por linha da lista e retornará determinandas colunas.
			for (VariableBinding vb : event.getColumns()) {
				//Criacao de expressao regular para retirar a string enterprise do oid
				//Exemplo:
				// Temos o oid 1.3.6.1.4.1.43.1.16.4.3.18  e vamos retirar o 43 que é o enterprise da 3com
				String[] ponto = vb.getVariable().toString().split("1.3.6.1.4.1.");
				//Objeto que recebe o OID enterprise
				Object ponto2 = ponto[1];
				//inicio é uma variavle para pegar o ultimo ponto antes da variavel ponto2
				inicio = ponto2.toString().indexOf(".");
				//result pegará a variavel enterprise
				result = ponto2.toString().substring(0, inicio);
			}
		}
		return result;
	}
}