package model;

/** Classe para objetos Registro, ele registra um novo modelo de switch que ainda não está cadastrado.
*
* @author silas
*
*/


public class Registro {

	String modelo;
	String classePorta;
	String classeVlan;
	String enterprise;
	

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