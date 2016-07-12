package br.ufrn.model;



import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Vlan implements Serializable{
	
	private Integer id;
	private String nomevlan;
	private String numerovlan;
	private String faixaIP;
	private String mascara;
	private String gateway;
	private String dns;
	private String dhcp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomevlan() {
		return nomevlan;
	}
	public void setNomevlan(String nomevlan) {
		this.nomevlan = nomevlan;
	}
	public String getNumerovlan() {
		return numerovlan;
	}
	public void setNumerovlan(String numerovlan) {
		this.numerovlan = numerovlan;
	}
	public String getFaixaIP() {
		return faixaIP;
	}
	public void setFaixaIP(String faixaIP) {
		this.faixaIP = faixaIP;
	}
	public String getMascara() {
		return mascara;
	}
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getDhcp() {
		return dhcp;
	}
	public void setDhcp(String dhcp) {
		this.dhcp = dhcp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dhcp == null) ? 0 : dhcp.hashCode());
		result = prime * result + ((dns == null) ? 0 : dns.hashCode());
		result = prime * result + ((faixaIP == null) ? 0 : faixaIP.hashCode());
		result = prime * result + ((gateway == null) ? 0 : gateway.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomevlan == null) ? 0 : nomevlan.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vlan other = (Vlan) obj;
		if (dhcp == null) {
			if (other.dhcp != null)
				return false;
		} else if (!dhcp.equals(other.dhcp))
			return false;
		if (dns == null) {
			if (other.dns != null)
				return false;
		} else if (!dns.equals(other.dns))
			return false;
		if (faixaIP == null) {
			if (other.faixaIP != null)
				return false;
		} else if (!faixaIP.equals(other.faixaIP))
			return false;
		if (gateway == null) {
			if (other.gateway != null)
				return false;
		} else if (!gateway.equals(other.gateway))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomevlan == null) {
			if (other.nomevlan != null)
				return false;
		} else if (!nomevlan.equals(other.nomevlan))
			return false;
		return true;
	}
	
	
		
}