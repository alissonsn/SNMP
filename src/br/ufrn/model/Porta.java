package br.ufrn.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Porta {
	private Object id_porta;
	private Object valor;
	private Object oid;
	private Object id;
	private Object velocidade;
	private Object estadoPorta;
	private Object ligada;
	private Object tipo_conector;
	private Object tipoVlan;
	private ArrayList<Vlan> vlan;


	public Porta(){
		id_porta = null;
		valor = null;
		oid = null;
		id = null;
		velocidade = null;
		estadoPorta = null;
		ligada = null;
		tipo_conector = null;
		tipoVlan = null;
		vlan = null;
	}


	public Object getId_porta() {
		return id_porta;
	}


	public void setId_porta(Object id_porta) {
		this.id_porta = id_porta;
	}


	public Object getValor() {
		return valor;
	}


	public void setValor(Object valor) {
		this.valor = valor;
	}


	public Object getOid() {
		return oid;
	}


	public void setOid(Object oid) {
		this.oid = oid;
	}


	public Object getId() {
		return id;
	}


	public void setId(Object id) {
		this.id = id;
	}


	public Object getVelocidade() {
		return velocidade;
	}


	public void setVelocidade(Object velocidade) {
		this.velocidade = velocidade;
	}


	public Object getEstadoPorta() {
		return estadoPorta;
	}


	public void setEstadoPorta(Object estadoPorta) {
		this.estadoPorta = estadoPorta;
	}


	public Object getLigada() {
		return ligada;
	}


	public void setLigada(Object ligada) {
		this.ligada = ligada;
	}


	public Object getTipo_conector() {
		return tipo_conector;
	}


	public void setTipo_conector(Object tipo_conector) {
		this.tipo_conector = tipo_conector;
	}


	public Object getTipoVlan() {
		return tipoVlan;
	}


	public void setTipoVlan(Object tipoVlan) {
		this.tipoVlan = tipoVlan;
	}


	public ArrayList<Vlan> getVlan() {
		return vlan;
	}


	public void setVlan(ArrayList<Vlan> vlan) {
		this.vlan = vlan;
	}	
	
	
}



