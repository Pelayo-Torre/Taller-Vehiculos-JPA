package uo.ri.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TMEDIOSPAGO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class MedioPago implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) protected Long id;

	protected double acumulado = 0.0;

	@ManyToOne protected Cliente cliente;
	@OneToMany(mappedBy = "medioPago") private Set<Cargo> cargos = new HashSet<>();

	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public Set<Cargo> getCargos() {
		return new HashSet<>( cargos );
	}

	public double getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(double acumulado) {
		this.acumulado = acumulado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		MedioPago other = (MedioPago) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedioPago [acumulado=" + acumulado + "]";
	}

	/**
	 * Comprueba que el medioPago sea correcto y carga el importe
	 * 
	 * @param importe
	 *            a cargar
	 */
	public void cagar(double importe) throws BusinessException {
		comprobarMedioValido( importe );
		double total = getAcumulado() + importe;
		setAcumulado( total );
	}

	/**
	 * Comprueba que el medio de pago sea válido y en el caso del bono pone su
	 * cantidad disponible de acuerdo con la cantidad que se gaste (importe)
	 * 
	 * @param importe
	 *            para restarle a la cantidad del bono
	 * @throws BusinessException.
	 *             Puede ser que la cantidad a pagar mayor que la cantidad
	 *             disponible
	 */
	abstract protected void comprobarMedioValido(double importe) throws BusinessException;
	

}
