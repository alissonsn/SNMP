package br.ufrn.model;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Switch {

	ArrayList<Object> id_switch;
	ArrayList<Object> id_porta;
	ArrayList<Object> porta;
	ArrayList<Object> oid;
	ArrayList<Object> id;
	ArrayList<Object> velocidade;
	ArrayList<Object> estadoPorta;
	ArrayList<Object> tipo_conector;
	ArrayList<Object> tipoVlan;
	ArrayList<Object> vlan;

	public Switch(){
		id_switch =  new ArrayList<Object>();
		id_porta = new ArrayList<Object>();
		porta = new ArrayList<Object>();
		oid = new ArrayList<Object>();
		id = new ArrayList<Object>();
		velocidade = new ArrayList<Object>();
		estadoPorta = new ArrayList<Object>();
		tipo_conector = new ArrayList<Object>();
		vlan = new ArrayList<Object>();
	}


	public ArrayList<Object> getId_switch() {
		return id_switch;
	}


	public void setId_switch(ArrayList<Object> id_switch) {
		this.id_switch = id_switch;
	}


	public ArrayList<Object> getId_porta() {
		return id_porta;
	}

	public void setId_porta(ArrayList<Object> id_porta) {
		this.id_porta = id_porta;
	}

	public ArrayList<Object> getPorta() {
		return porta;
	}

	public void setPorta(ArrayList<Object> porta) {
		this.porta = porta;
	}

	public ArrayList<Object> getOid() {
		return oid;
	}

	public void setOid(ArrayList<Object> oid) {
		this.oid = oid;
	}

	public ArrayList<Object> getId() {
		return id;
	}

	public void setId(ArrayList<Object> id) {
		this.id = id;
	}

	public ArrayList<Object> getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(ArrayList<Object> velocidade) {
		this.velocidade = velocidade;
	}

	public ArrayList<Object> getEstadoPorta() {
		return estadoPorta;
	}

	public void setEstadoPorta(ArrayList<Object> estadoPorta) {
		this.estadoPorta = estadoPorta;
	}

	public ArrayList<Object> getTipo_conector() {
		return tipo_conector;
	}

	public void setTipo_conector(ArrayList<Object> tipo_conector) {
		this.tipo_conector = tipo_conector;
	}

	public ArrayList<Object> getTipoVlan() {
		return tipoVlan;
	}

	public void setTipoVlan(ArrayList<Object> tipoVlan) {
		this.tipoVlan = tipoVlan;
	}


	public ArrayList<Object> getVlan() {
		return vlan;
	}


	public void setVlan(ArrayList<Object> vlan) {
		this.vlan = vlan;
	}

}
