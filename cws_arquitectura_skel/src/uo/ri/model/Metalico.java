package uo.ri.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TMETALICOS")
@DiscriminatorValue("TMetalicos")
public class Metalico extends MedioPago implements Serializable {

	private static final long serialVersionUID = 1L;

	public Metalico(Cliente cliente) {
		super();
		Association.Pagar.link(cliente, this);
	}

	Metalico() {}

	
	/**
	 * Aumenta el acumulado una cantidad i
	 * 
	 * @param i,
	 *            cantidad a aumentar
	 */
	public void pagar(double i) {
		this.acumulado += i;
	}

	@Override
	protected void comprobarMedioValido(double importe) throws BusinessException {
	}

}
