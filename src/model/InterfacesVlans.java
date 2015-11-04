package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

/** Interface que contém as assinaturas de metodos para capturar das vlans dos ativos.
*
* @author silas
*
*/

public interface InterfacesVlans {

	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Integer>, Contendo o numero das vlans, Exemplo: De uma vlan 830.
	 */
	public ArrayList<Integer> vlan(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Integer,String>, Contendo o numero das portas mapeados nas vlans, Exemplo: Vlan 830  portas 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,
	 */
	public  HashMap<Integer,String> mapeamento(Snmp snmp, UserTarget target);
}