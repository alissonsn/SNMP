package br.ufrn.model;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Interface_Raspberry implements Serializable{

	
	private String interface_raspberry;
	private Raspberry raspberry = null;
	private Switch comutador = null;
	private Municipio municipio = null;
	private Unidade unidade = null;
	private Predio predio = null;
	private Pavimento pavimento = null;
	private Andar andar = null;
	private Sala sala = null;
	private Rack rack = null;
	
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
	public Andar getAndar() {
		return andar;
	}
	public void setAndar(Andar andar) {
		this.andar = andar;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Rack getRack() {
		return rack;
	}
	public void setRack(Rack rack) {
		this.rack = rack;
	}	
}