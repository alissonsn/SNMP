package br.ufrn.model;




import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Switch {

	private Integer id_switch;
	private Object ip;
	private Object serialtombo;
	private Object usuario;
	private Object senha;
	private Municipio municipio = new Municipio();
	private Unidade unidade = new Unidade();
	private Predio predio = new Predio();
	private Pavimento pavimento = new Pavimento();
	private Sala sala = new Sala();
	
	private ArrayList<Porta> interfaces;

	public Switch(){
		interfaces = new ArrayList<Porta>();
	}



	public ArrayList<Porta> getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(ArrayList<Porta> interfaces) {
		this.interfaces = interfaces;
	}

	public Object getSerialtombo() {
		return serialtombo;
	}



	public void setSerialtombo(Object serialtombo) {
		this.serialtombo = serialtombo;
	}



	public Object getIp() {
		return ip;
	}


	public void setIp(Object ip) {
		this.ip = ip;
	}


	public Integer getId_switch() {
		return id_switch;
	}


	public void setId_switch(Integer id_switch) {
		this.id_switch = id_switch;
	}


	public Object getUsuario() {
		return usuario;
	}



	public void setUsuario(Object usuario) {
		this.usuario = usuario;
	}



	public Object getSenha() {
		return senha;
	}



	public void setSenha(Object senha) {
		this.senha = senha;
	}


	

	public Municipio getMunicipio() {
		return municipio;
	}



	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}



	public Unidade getUnidade() {
		return unidade;
	}



	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}



	public Predio getPredio() {
		return predio;
	}



	public void setPredio(Predio predio) {
		this.predio = predio;
	}



	public Pavimento getPavimento() {
		return pavimento;
	}



	public void setPavimento(Pavimento pavimento) {
		this.pavimento = pavimento;
	}



	public Sala getSala() {
		return sala;
	}



	public void setSala(Sala sala) {
		this.sala = sala;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((interfaces == null) ? 0 : interfaces.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((serialtombo == null) ? 0 : serialtombo.hashCode());
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
		Switch other = (Switch) obj;
		if (interfaces == null) {
			if (other.interfaces != null)
				return false;
		} else if (!interfaces.equals(other.interfaces))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (serialtombo == null) {
			if (other.serialtombo != null)
				return false;
		} else if (!serialtombo.equals(other.serialtombo))
			return false;
		return true;
	}

}