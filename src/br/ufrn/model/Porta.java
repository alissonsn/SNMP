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
	private Object data;
	private Object modificacao;
	private Object aprova;
	private ArrayList<VlanSW> vlan;
	private ArrayList<Vlan> listaVlan;

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
		modificacao = null;
		aprova = null;
		vlan = null;
		listaVlan = null;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getModificacao() {
		return modificacao;
	}

	public void setModificacao(Object modificacao) {
		this.modificacao = modificacao;
	}

	public Object getAprova() {
		return aprova;
	}

	public void setAprova(Object aprova) {
		this.aprova = aprova;
	}

	public ArrayList<VlanSW> getVlan() {
		return vlan;
	}

	public void setVlan(ArrayList<VlanSW> vlan) {
		this.vlan = vlan;
	}
	

	public ArrayList<Vlan> getListaVlan() {
		return listaVlan;
	}

	public void setListaVlan(ArrayList<Vlan> listaVlan) {
		this.listaVlan = listaVlan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((estadoPorta == null) ? 0 : estadoPorta.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ligada == null) ? 0 : ligada.hashCode());
		result = prime * result
				+ ((modificacao == null) ? 0 : modificacao.hashCode());
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		result = prime * result
				+ ((tipoVlan == null) ? 0 : tipoVlan.hashCode());
		result = prime * result
				+ ((tipo_conector == null) ? 0 : tipo_conector.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result
				+ ((velocidade == null) ? 0 : velocidade.hashCode());
		result = prime * result + ((vlan == null) ? 0 : vlan.hashCode());
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
		Porta other = (Porta) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (estadoPorta == null) {
			if (other.estadoPorta != null)
				return false;
		} else if (!estadoPorta.equals(other.estadoPorta))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ligada == null) {
			if (other.ligada != null)
				return false;
		} else if (!ligada.equals(other.ligada))
			return false;
		if (modificacao == null) {
			if (other.modificacao != null)
				return false;
		} else if (!modificacao.equals(other.modificacao))
			return false;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		if (tipoVlan == null) {
			if (other.tipoVlan != null)
				return false;
		} else if (!tipoVlan.equals(other.tipoVlan))
			return false;
		if (tipo_conector == null) {
			if (other.tipo_conector != null)
				return false;
		} else if (!tipo_conector.equals(other.tipo_conector))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (velocidade == null) {
			if (other.velocidade != null)
				return false;
		} else if (!velocidade.equals(other.velocidade))
			return false;
		if (vlan == null) {
			if (other.vlan != null)
				return false;
		} else if (!vlan.equals(other.vlan))
			return false;
		return true;
	}
}