package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

/** Classe para objetos Vlans, ele pegará todas as configurações das vlans dos ativos do modelo 3com.
 *
 * @author silas
 *
 */

public class Vlans3com implements InterfacesVlans{


	/** Metodo que consulta as vlans do ativo,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo as vlans do ativo, Exemplo: 830.
	 */

	@Override
	public ArrayList<Integer> vlan(Snmp snmp, UserTarget target) {
		//Arraylist que receberá o resultado da consulta
		ArrayList<Integer> strList = new ArrayList<Integer>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.17.7.1.4.3.1.1")
		};
		//Classe que fornece funções de utilidade para recuperar dados SNMP tabulares.
		TableUtils tUtils = new TableUtils(snmp, new DefaultPDUFactory());
		//Obtém dados SNMP tabulares sincrono de uma ou mais tabelas.
		//Os dados são retornados linha por linha como uma lista de instâncias TableEvent.
		//Cada instância representa uma linha (ou uma condição de erro).
		//Além do agente alvo, os OIDs dos objetos colunar tem que ser especificado para quais instâncias devem ser recuperados.
		//Com um índice de limite inferior e um índice limite superior.
		List<TableEvent> events = tUtils.getTable(target, interfaces ,null,null);
		for (TableEvent event : events) {
			//Varrerá linha por linha da lista e retornará determinandas colunas.
			for (VariableBinding vb : event.getColumns()) {
				//Adiciona a vlan no arraylist
				strList.add(vb.getOid().get(13));
			}
		}
		return strList;
	}

	/** Metodo que consulta o mapeamento de portas nas vlans do ativo,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Integer, String>, Contendo as portas mapeadas nas vlans do ativo, Exemplo: Vlan 830  portas 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
	 */

	@Override
	public HashMap<Integer, String> mapeamento(Snmp snmp, UserTarget target) {
		//HashMap que receberá o resultado da consulta
		HashMap<Integer,String> mape = new HashMap<Integer,String>();
		//HashMap com os mapeamentos dos ID de interface fisica com IDs SNMP
		HashMap<String,Object> id = comparacao(snmp, target);

		HashMap<Integer,String> id2 = new HashMap<Integer, String>();
		//Objeto que recebe os IDs fisicos Exemplo: 23
		Object portas = null;
		//String que receberá o ID SNMP, Exemplo: 4227681
		String IDsnmp = "";
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.47.1.3.1.1.1")
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
				//Exemplo vb.getVariable().toString() = 23 Interface Fisica
				//portas recebe os IDs Fisicos
				portas = id.get(vb.getVariable().toString());
				//Adicionando no hashmap a vlan correspondente e o ID fisico Exemplo: 264.27 Vlan 264 ID 27
				mape.put(vb.getOid().get(12), portas.toString());
				//IDS das interfaces
				//portas recebe os IDs SNMP Exemplo: 4227681
				IDsnmp = mape.get(vb.getOid().get(12));

				////Compara se o conteudo é nulo ou não
				if (id2.get(vb.getOid().get(12)) == null){
					//Se o hashmap for nulo adicione no hash apenas a string inicial
					id2.put(vb.getOid().get(12), IDsnmp);
				}else{
					//Se o hashmap não for nulo adicione no hash a string inicial concatenada com o proprio valor do hash recursivamente
					//Exemplo de Saida Vlan 870 Portas 4227625,4228434
					id2.put(vb.getOid().get(12), id2.get(vb.getOid().get(12)) + "," + IDsnmp);
				}
			}
		}
		return id2;
	}


	/** Metodo que consulta o ID fisico da porta e retornar o ID SNMP da interface,
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return HashMap<String,Object>, Contendo as o ID fisico mapeado em seu ID SNMP, Exemplo: 30 = 4227681.
	 */

	public HashMap<String,Object> comparacao(Snmp snmp, UserTarget target){
		//HashMap que receberá o resultado da consulta
		HashMap<String,Object> mape = new HashMap<String,Object>();
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.2.1.47.1.3.2.1")
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
				//Criando uma expressão regular para retirar 4227681 do OID 1.3.6.1.2.1.2.2.1.1.4227681
				String[] teste = 	vb.getVariable().toString().split("1.3.6.1.2.1.2.2.1.1.");
				//Criando Objeto que receberá apenas os IDs das interfaces Exemplo: 4227681
				Object regex = teste[1];
				////Adiciona O ID fisico mapeado no ID SNMP da interface, Exemplo: mape.put(""+30,4227681);
				mape.put(""+vb.getOid().get(12), regex);
			}
		}
		return mape;
	}


}
