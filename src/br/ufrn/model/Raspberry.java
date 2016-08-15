package br.ufrn.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Raspberry implements Serializable{

	private Integer id_raspberry;
	private String posicaoRack;
	private List<Interface_Raspberry> lista_Interface_Raspberry;
	private Municipio municipio = null;
	private Unidade unidade = null;
	private Predio predio = null;
	private Pavimento pavimento = null;
	private Andar andar = null;
	private Sala sala = null;
	private Rack rack = null;
	private Switch comutador = null;
	
	
	public Integer getId_raspberry() {
		return id_raspberry;
	}
	public void setId_raspberry(Integer id_raspberry) {
		this.id_raspberry = id_raspberry;
	}	
	public String getPosicaoRack() {
		return posicaoRack;
	}
	public void setPosicaoRack(String posicaoRack) {
		this.posicaoRack = posicaoRack;
	}
	public List<Interface_Raspberry> getLista_Interface_Raspberry() {
		return lista_Interface_Raspberry;
	}
	public void setLista_Interface_Raspberry(
			List<Interface_Raspberry> lista_Interface_Raspberry) {
		this.lista_Interface_Raspberry = lista_Interface_Raspberry;
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
	public Switch getComutador() {
		return comutador;
	}
	public void setComutador(Switch comutador) {
		this.comutador = comutador;
	}	
}