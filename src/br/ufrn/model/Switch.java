package br.ufrn.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Switch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6524072503768636037L;
	/**
	 * 
	 */
	
	private Integer id_switch;
	private Object ip;
	private Object serialtombo;
	private Object posicaoRack;
	private Object senha;
	private Object usuario;
	private ArrayList<Porta> interfaces;
	private Municipio municipio;
	private Unidade unidade;
	private Predio predio;
	private Pavimento pavimento;
	private Andar andar;
	private Sala sala;
	private Rack rack;
	

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
	
	public Object getPosicaoRack() {
		return posicaoRack;
	}

	public void setPosicaoRack(Object posicaoRack) {
		this.posicaoRack = posicaoRack;
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

	public Object getSenha() {
		return senha;
	}

	public void setSenha(Object senha) {
		this.senha = senha;
	}

	public Object getUsuario() {
		return usuario;
	}

	public void setUsuario(Object usuario) {
		this.usuario = usuario;
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
	
	public Andar getAndar() {
		return andar;
	}

	public void setAndar(Andar andar) {
		this.andar = andar;
	}

	public Rack getRack() {
		return rack;
	}

	public void setRack(Rack rack) {
		this.rack = rack;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_switch == null) ? 0 : id_switch.hashCode());
		result = prime * result
				+ ((interfaces == null) ? 0 : interfaces.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((serialtombo == null) ? 0 : serialtombo.hashCode());
		return result;
	}
}