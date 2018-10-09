package uo.ri.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TBONOS")
@DiscriminatorValue("TBonos")
public class Bono extends MedioPago implements Serializable {

	private static final long serialVersionUID = 1L;

	private double disponible = 0.0;
	private String descripcion;
	@Column(unique = true)private String codigo;

	public Bono(Cliente cliente, String codigo) {
		this( codigo );
		Association.Pagar.link(cliente, this);
	}
	
	public Bono(Cliente cliente, String codigo, String descripcion, double disponible) {
		this(cliente, codigo);
		this.descripcion = descripcion;
		this.disponible = disponible;
	}

	public Bono(String codigo) {
		super();
		this.codigo = codigo;
		this.descripcion = "";
	}

	Bono() {}

	public Bono(String codigo, double disponible) {
		this( codigo );
		this.disponible = disponible;
	}

	public Bono(String codigo, String descripcion, double disponible) {
		this( codigo );
		this.descripcion = descripcion;
		this.disponible = disponible;
	}

	public Double getDisponible() {
		return disponible;
	}

	public void setDisponible(Double disponible) {
		this.disponible = disponible;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bono other = (Bono) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bono [disponible=" + disponible + ", descripcion=" 
				+ descripcion + ", codigo=" + codigo + "]";
	}

	@Override
	/**
	 * Comprueba que el medioPago sea válido y además calcula la cantidad disponible
	 * del medioPago de tipo Bono
	 * 
	 * @param importe
	 *            a cargar
	 * @throws BusinessException.
	 *             Puede ser que no haya suficiente dinero
	 */
	protected void comprobarMedioValido(double importe) throws BusinessException {
		if (getDisponible() < importe) {
			throw new BusinessException( "No hay suficiente dinero" );
		} else {
			double disponible = getDisponible();
			setDisponible( disponible - importe );
		}
	}

	
	/**
	 * Se aumenta el acumulado y se decrementa la cantidad disponible
	 * 
	 * @param d,
	 *            cantidad a aumentar/decrementar
	 * @throws BusinessException.
	 *             Puede ser que la cantidad a pagar sea mayor que la cantidad
	 *             disponible
	 */
	public void pagar(double d) throws BusinessException {
		if (this.disponible >= d) {
			this.acumulado += d;
			this.disponible -= d;
		} else
			throw new BusinessException("La cantidad que se "
					+ "va a pagar no puede ser mayor que la cantidad disponible");
	}
}
