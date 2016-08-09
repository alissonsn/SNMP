package br.ufrn.model;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Interface_Raspberry implements Serializable{

	
	private String id_interface_raspberry;
	private Raspberry raspberry = new Raspberry();
	
	
	public String getId_interface_raspberry() {
		return id_interface_raspberry;
	}
	public void setId_interface_raspberry(String id_interface_raspberry) {
		this.id_interface_raspberry = id_interface_raspberry;
	}
	public Raspberry getRaspberry() {
		return raspberry;
	}
	public void setRaspberry(Raspberry raspberry) {
		this.raspberry = raspberry;
	}	
}