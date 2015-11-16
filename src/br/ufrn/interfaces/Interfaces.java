package br.ufrn.interfaces;

import java.util.ArrayList;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;

/** Interface que contém as assinaturas de metodos para capturar configurações de ativos.
 *
 * @author silas
 *
 */

public interface Interfaces {

	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo a descrição das portas, Exemplo: De uma porta GigabitEthernet1/0/7.
	 */
	public ArrayList<Object> Porta(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo o OID da porta, Exemplo: De um OID  1.3.6.1.2.1.31.1.1.1.1.10.
	 */
	public ArrayList<Object> Oid(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Integer>, Contendo o ID SNMP da porta, Exemplo: De um ID 10.
	 */
	public ArrayList<Integer> id(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo o tipo da vlan se ela é access, trunk ou hybrid, Exemplo: De uma porta access.
	 */
	public ArrayList<Object> TypeVlan(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo a velocidade da porta, Exemplo: De uma porta 1000.
	 */
	public ArrayList<Object> Velocidade(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo o tipo de cabo da porta, Exemplo: De uma porta RJ45.
	 */
	public ArrayList<Object> Tipo_conector(Snmp snmp, UserTarget target);
	/**
	 *
	 * @param Snmp, metodo de envio e recebimento de PDUs SNMP.
	 * @param UserTarget, uma instancia com o nivel segurança configurado.
	 * @return ArrayList<Object>, Contendo o estado da porta se ela está up ou down, Exemplo: De uma porta Down.
	 */
	public ArrayList<Object> Estado_porta(Snmp snmp, UserTarget target);
}