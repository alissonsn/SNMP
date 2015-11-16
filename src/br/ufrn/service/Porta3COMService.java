package br.ufrn.service;

import java.util.ArrayList;
import java.util.List;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import br.ufrn.interfaces.Interfaces;

/** Classe para objetos Porta3Com, ele pegará todas as configurações de ativos do modelo 3com.
*
* @author silas
*
*/
public class Porta3COMService implements Interfaces{


	/** Metodo que consulta as portas do ativo,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo a descrição das portas, Exemplo: GigabitEthernet1/0/7
	 */
	@Override
	public ArrayList<Object> Porta(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Object> strList = new ArrayList<Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.31.1.1.1.1")
		};
		//Classe que fornece funções de utilidade para recuperar dados SNMP tabulares.
		TableUtils tUtils = new TableUtils(snmp,new DefaultPDUFactory());
		//Obtém dados SNMP tabulares sincrono de uma ou mais tabelas.
		//Os dados são retornados linha por linha como uma lista de instâncias TableEvent.
		//Cada instância representa uma linha (ou uma condição de erro).
		//Além do agente alvo, os OIDs dos objetos colunar tem que ser especificado para quais instâncias devem ser recuperados.
		//Com um índice de limite inferior e um índice limite superior.
		List<TableEvent> events = tUtils.getTable(target, interfaces ,null,null);
		for (TableEvent event : events) {
			//Varrerá linha por linha da lista e retornará determinandas colunas.
			for (VariableBinding vb : event.getColumns()) {
				//Adiciona o resultado no arraylist
				strList.add(vb.getVariable().toString());
			}
		}
		return strList;
	}

	/** Metodo que consulta a OID das portas.
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo os OIDs das portas, Exemplo: 1.3.6.1.2.1.31.1.1.1.1.1.
	 */
	@Override
	public ArrayList<Object> Oid(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Object> strList = new ArrayList<Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.31.1.1.1.1")
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
				//Adiciona o o OID no arraylist
				strList.add(vb.getOid().toString());
			}
		}
		return strList;
	}

	/** Metodo que consulta a ID SNMP das portas.
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo os IDs SNMP das portas, Exemplo: 1
	 */
	@Override
	public ArrayList<Integer> id(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Integer> strList = new ArrayList<Integer>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.31.1.1.1.1")
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
				//Adiciona o ID no arraylist
				strList.add(vb.getOid().get(11));
			}
		}
		return strList;
	}

	/** Metodo que consulta a o tipo da porta se access, trunk ou hybrid das portas.
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo os tipos das portas, Exemplo: access
	 */
	@Override
	public ArrayList<Object> TypeVlan(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Object> strList = new ArrayList<Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.4.1.43.45.1.2.23.1.1.1.1.5")
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
				//Compara que tipo de inteiro ele retorna
				if (vb.getVariable().toString().equals("1")) {
					//Adiciona o trunk no arraylist
					strList.add("trunk");
				}else if (vb.getVariable().toString().equals("2")) {
					//Adiciona o access no arraylist
					strList.add("access");
				}else if (vb.getVariable().toString().equals("3")) {
					//Adiciona o hybrid no arraylist
					strList.add("hybrid");
				}else{
					//Adiciona o unknown no arraylist
					strList.add("unknown");
				}
			}
		}
		return strList;
	}

	/** Metodo que consulta a velocidade das portas do ativo,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo a velocidade das portas, Exemplo: 1000
	 */
	@Override
	public ArrayList<Object> Velocidade(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Object> strList = new ArrayList<Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.31.1.1.1.15")
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
				//Adiciona o resultado no arraylist
				strList.add(vb.getVariable().toString());
			}
		}
		return strList;
	}

	/** Metodo que consulta o tipo de conector das portas do ativo,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo a velocidade das portas, Exemplo: O resultado retornará inteiros que correspondem: 1 à Fibra Otica, 2 à RJ45.
	 */
	@Override
	public ArrayList<Object> Tipo_conector(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Object> strList = new ArrayList<Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.26.2.2.1.2")
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
				//Compara que tipo de inteiro ele retorna
				if (vb.getVariable().toString().equals("1")) {
					//Adiciona o Fibra Otica no arraylist
					strList.add("Fibra Otica");
				}else if (vb.getVariable().toString().equals("2")) {
					//Adiciona o RJ45 no arraylist
					strList.add("RJ45");
				}else {
					strList.add("");
				}
			}
		}
		return strList;

	}

	/** Metodo que consulta o estado das portas se elas estão down ou up,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo o estado das portas, Exemplo: O resultado retornará inteiros que correspondem: 1 Porta Up, 2 Porta Down.
	 */
	@Override
	public ArrayList<Object> Estado_porta(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Object> strList = new ArrayList<Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.2.2.1.7")
		};
		//Classe que fornece funções de utilidade para recuperar dados SNMP tabulares.
		TableUtils tUtils = new TableUtils(snmp,new DefaultPDUFactory());
		//Obtém dados SNMP tabulares sincrono de uma ou mais tabelas.
		//Os dados são retornados linha por linha como uma lista de instâncias TableEvent.
		//Cada instância representa uma linha (ou uma condição de erro).
		//Além do agente alvo, os OIDs dos objetos colunar tem que ser especificado para quais instâncias devem ser recuperados.
		//Com um índice de limite inferior e um índice limite superior.
		List<TableEvent> events = tUtils.getTable(target, interfaces ,null,null);
		for (TableEvent event : events) {
			//Varrerá linha por linha da lista e retornará determinandas colunas.
			for (VariableBinding vb : event.getColumns()) {
				//Compara que tipo de inteiro ele retorna
				if (vb.getVariable().toString().equals("1")) {
					//Adiciona o up no arraylist
					strList.add("Up");
				}else if (vb.getVariable().toString().equals("2")) {
					//Adiciona o Down no arraylist
					strList.add("Down");
				}
			}
		}
		return strList;
	}

}

