package uo.ri.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.DateUtil;
import alb.util.math.Round;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;
import uo.ri.util.exception.BusinessException;

@Entity
@Table(name = "TFACTURAS")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)private Long id;

	@Column(unique = true) private Long numero;
	@Temporal(TemporalType.DATE) private Date fecha;
	private double importe;
	private double iva;
	@Enumerated(EnumType.STRING) private FacturaStatus status = FacturaStatus.SIN_ABONAR;

	@OneToMany(mappedBy = "factura") private Set<Averia> averias = new HashSet<>();
	@OneToMany(mappedBy = "factura")private Set<Cargo> cargos = new HashSet<>();

	private boolean usada_bono = false;

	public Factura(long numero) {
		super();
		this.numero = numero;
		this.fecha = DateUtil.today();
	}

	public Factura(long numero, Date fecha) {
		this( numero );
		this.fecha = fecha;
	}

	public Factura(long numero, List<Averia> averias) throws BusinessException {
		this( numero );
		addAverias( averias );
	}

	public Factura(long numero, Date fecha, List<Averia> averias) throws BusinessException {
		this(numero, fecha);
		addAverias( averias );
	}

	Factura() {}

	public Long getId() {
		return id;
	}

	private void addAverias(List<Averia> averias) throws BusinessException {
		for (Averia a : averias) {
			addAveria( a );
		}
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	public Set<Cargo> getCargos() {
		return new HashSet<>( cargos );
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public FacturaStatus getStatus() {
		return status;
	}

	public void setStatus(FacturaStatus status) {
		this.status = status;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFecha() {
		return fecha;
	}

	Set<Averia> _getAverias() {
		return averias;
	}

	public Set<Averia> getAverias() {
		return new HashSet<>( averias );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha 
				+ ", importe=" + importe + ", iva=" + iva + ", status="
				+ status + "]";
	}

	/**
	 * Se verifica que la factura esta en el estado SIN_ABONAR Se verifica que la
	 * avería está en el estado TERMINADA Se hace el link de factura y avería Se
	 * marca la avería como facturada Se calcula el importe
	 * 
	 * @param averia
	 * @throws BusinessException.
	 *             Puede ser que la avería no se encuentre en el estado TERMINADA
	 */
	public void addAveria(Averia averia) throws BusinessException {
		if (this.status.equals(FacturaStatus.SIN_ABONAR) 
				&& averia.getStatus().equals(AveriaStatus.TERMINADA)) {
			Association.Facturar.link(this, averia);
			averia.markAsInvoiced();
			averias.add( averia );
			averia.calcularImporte();
			calcularImporte();
		} else {
			throw new BusinessException( "La avería no está TERMINADA" );
		}
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de
	 * factura
	 */
	void calcularImporte() {
		this.importe = 0.0;
		for (Averia a : this.averias) {
			this.importe += a.getImporte();
		}
		calcularIva();
		this.importe += Round.twoCents(this.importe * this.iva);
	}

	/**
	 * Calcula el iva segun la fecha. Si es posterior al 01/07/2012 el iva es 0.21,
	 * en caso contrario el iva es 0.18
	 */
	void calcularIva() {
		if (this.fecha.before( DateUtil.fromString("01/07/2012" ))) {
			this.iva = 0.18;
		} else {
			this.iva = 0.21;
		}
	}

	/**
	 * Se verifica que la factura está sin abonar Se hace el unlink entre factura y
	 * averia Se establece el estado de la avería como FINALIZADA Se calcula el
	 * importe de la avería Se calcula el importe de la factura
	 * 
	 * @param averia
	 *            a eliminar
	 */
	public void removeAveria(Averia averia) {
		if (this.status.equals(FacturaStatus.SIN_ABONAR)) {
			Association.Facturar.unlink(this, averia);
			averia.markBackToFinished();
			averia.calcularImporte();
			calcularImporte();
		}
	}

	public void setFecha(Date today) {
		this.fecha = today;
	}

	/**
	 * Liquida una factura, siempre que tenga averías y puede liquidarse
	 * 
	 * @throws BusinessException.
	 *             Puede ser que la factra no se pueda liquidar
	 */
	public void settle() throws BusinessException {
		if (this.averias.size() != 0 && facturaPuedeLiquidarse() == true) {
			setStatus( FacturaStatus.ABONADA );
		} else {
			throw new BusinessException( "No se puede liquidar la factura" );
		}
	}

	/**
	 * Una factura puede liquidarse si tiene un margen de +-0.01€
	 * 
	 * @return true si se puede liquidar, false en caso contrario
	 */
	private boolean facturaPuedeLiquidarse() {
		double total = 0.0;
		for (Cargo c : this.cargos) {
			total += c.getImporte();
		}
		double margen = Math.abs( total - this.importe );
		if (margen > 0.01) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Una factura abonada y con importe de más 500€ puede generar bono
	 * 
	 * @return true si lo genera, false en caso contrario
	 */
	public boolean puedeGenerarBono500() {
		if (this.status.equals(FacturaStatus.ABONADA)) {
			if (this.importe >= 500.0) {
				if (this.usada_bono == false) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Marca la factura como usada para generar bono
	 * 
	 * @throws BusinessException.
	 *             Puede ser que la factura no pueda generar bono
	 */
	public void markAsBono500Used() throws BusinessException {
		if (puedeGenerarBono500() == false) {
			throw new BusinessException( "Esta factura no puede generar bono" );
		} else {
			this.usada_bono = true;
		}
	}

	/**
	 * Comprueba si la factura está abonada o no
	 * 
	 * @return true si está abonada, false en caso contrario
	 */
	public boolean isSettled() {
		if (this.status.equals( FacturaStatus.ABONADA )) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Comprueba si la factura fue usada o no para bono
	 * 
	 * @return true si fue usada, false en caso contrario
	 */
	public boolean isBono500Used() {
		if (usada_bono == true) {
			return true;
		} else {
			return false;
		}
	}

}
