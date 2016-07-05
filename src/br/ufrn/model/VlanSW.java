package br.ufrn.model;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class VlanSW {
	private Object vlan;
	
	public VlanSW(){
		vlan = new Object();
	}

	public Object getVlan() {
		return vlan;
	}

	public void setVlan(Object vlan) {
		this.vlan = vlan;
	}	
}