package br.ufrn.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Rack implements Serializable{
		private Integer id;
		private String nome;
		private String qtdUS;
		private Municipio municipio = new Municipio();
		private Unidade unidade = new Unidade();
		private Predio predio = new Predio();
		private Pavimento pavimento = new Pavimento();
		private Sala sala = new Sala();
				
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getQtdUS() {
			return qtdUS;
		}
		public void setQtdUS(String qtdUS) {
			this.qtdUS = qtdUS;
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
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
			Rack other = (Rack) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
		}
}