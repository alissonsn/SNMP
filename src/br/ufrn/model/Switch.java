package br.ufrn.model;




import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Switch {

	Object id_switch;
	Object ip;
	ArrayList<Porta> interfaces;

	public Switch(){
		id_switch =  new Object();
		interfaces = new ArrayList<Porta>();
	}


	public ArrayList<Porta> getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(ArrayList<Porta> interfaces) {
		this.interfaces = interfaces;
	}

	

	public Object getIp() {
		return ip;
	}


	public void setIp(Object ip) {
		this.ip = ip;
	}


	public Object getId_switch() {
		return id_switch;
	}


	public void setId_switch(Object id_switch) {
		this.id_switch = id_switch;
	}


	
}
