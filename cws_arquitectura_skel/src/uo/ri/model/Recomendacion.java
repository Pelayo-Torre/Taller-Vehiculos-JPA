package uo.ri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(columnNames = "RECOMENDADO_ID, RECOMENDADOR_ID") }
, name = "TRECOMENDACIONES")
public class Recomendacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean usada_bono = false;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	@ManyToOne private Cliente recomendador;
	@OneToOne private Cliente recomendado;

	public Recomendacion(Cliente recomendador, Cliente recomendado) {
		super();
		Association.Recomendar.link(recomendador, recomendado, this);
	}

	public Cliente getRecomendador() {
		return recomendador;
	}

	Recomendacion() {}

	public Long getId() {
		return id;
	}

	void _setRecomendador(Cliente recomendador) {
		this.recomendador = recomendador;
	}

	public Cliente getRecomendado() {
		return recomendado;
	}

	void _setRecomendado(Cliente recomendado) {
		this.recomendado = recomendado;
	}

	public boolean isUsada_bono() {
		return usada_bono;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recomendado == null) ? 0 : recomendado.hashCode());
		result = prime * result + ((recomendador == null) ? 0 : recomendador.hashCode());
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
		Recomendacion other = (Recomendacion) obj;
		if (recomendado == null) {
			if (other.recomendado != null)
				return false;
		} else if (!recomendado.equals(other.recomendado))
			return false;
		if (recomendador == null) {
			if (other.recomendador != null)
				return false;
		} else if (!recomendador.equals(other.recomendador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Recomendacion [usada_bono=" + usada_bono + "]";
	}

	/**
	 * Marca la recomendación como usada para generar bono.
	 */
	public void markAsUsadaBono() {
		this.usada_bono = true;
	}

	/**
	 * Hace el unlink entre el recomendador y el recomendado
	 */
	public void unlink() {
		Association.Recomendar.unlink( this );
	}

	/**
	 * Comprueba si la recomendación fue usada o no para bono
	 * 
	 * @return true si fue usada, false en caso contrario
	 */
	public boolean isUsada() {
		if (usada_bono == true) {
			return true;
		} else {
			return false;
		}
	}

}
