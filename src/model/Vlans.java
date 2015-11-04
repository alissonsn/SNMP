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

/** Classe para objetos Vlans, ele pegará todas as configurações das vlans dos ativos do modelo H3C.
 *
 * @author silas
 *
 */

public class Vlans implements InterfacesVlans{

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
		//Vetor de OIDs que será feito o metodo snmpwalk para consulta.
		OID[] interfaces = new OID[] {
				new OID(".1.3.6.1.4.1.25506.8.35.2.1.1.1.19")
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
				//Adiciona a vlan mapeada com as respectivas portas no arraylist
				mape.put(vb.getOid().get(14) , vb.getVariable().toString());
			}
		}
		return mape;
	}
}