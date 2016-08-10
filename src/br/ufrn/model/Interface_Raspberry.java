package br.ufrn.model;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Interface_Raspberry implements Serializable{

	
	private String interface_raspberry;
	private Raspberry raspberry = new Raspberry();
	private Switch comutador = new Switch();
	
	public String getInterface_raspberry() {
		return interface_raspberry;
	}
	public void setInterface_raspberry(String interface_raspberry) {
		this.interface_raspberry = interface_raspberry;
	}
	public Raspberry getRaspberry() {
		return raspberry;
	}
	public void setRaspberry(Raspberry raspberry) {
		this.raspberry = raspberry;
	}
	public Switch getComutador() {
		return comutador;
	}
	public void setComutador(Switch comutador) {
		this.comutador = comutador;
	}
}