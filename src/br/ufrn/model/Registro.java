package br.ufrn.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;



/** Classe para objetos Registro, ele registra um novo modelo de switch que ainda não está cadastrado.
 *
 * @author silas
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Registro{

	private Integer id;
	private String modelo;
	private String classePorta;
	private String classeVlan;
	private String enterprise;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClassePorta() {
		return classePorta;
	}
	public void setClassePorta(String classePorta) {
		this.classePorta = classePorta;
	}
	public String getClasseVlan() {
		return classeVlan;
	}
	public void setClasseVlan(String classeVlan) {
		this.classeVlan = classeVlan;
	}
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}