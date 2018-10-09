package uo.ri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import uo.ri.model.types.Address;
import uo.ri.model.types.AveriaStatus;

@Entity
@Table(name = "TCLIENTES")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

	@Column(unique = true) private String dni;
	private String nombre;
	private String apellidos;
	private Address address;
	private String phone;
	private String email;

	@OneToMany(mappedBy = "cliente") private Set<Vehiculo> vehiculos = new HashSet<>();
	@OneToMany(mappedBy = "cliente") private Set<MedioPago> mediosPago = new HashSet<>();

	@OneToMany(mappedBy = "recomendador")
	private Set<Recomendacion> recomendacionesHechas = new HashSet<>();
	@OneToOne private Recomendacion recomendacionRecibida;

	public Cliente(String dni, String nombre, String apellidos) {
		this( dni );
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public Cliente(String dni) {
		super();
		this.dni = dni;
	}

	Cliente() {}

	public Long getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	Set<Recomendacion> _getRecomendacionesHechas() {
		return recomendacionesHechas;
	}

	public Set<Recomendacion> getRecomendacionesHechas() {
		return new HashSet<>( recomendacionesHechas );
	}

	public Recomendacion getRecomendacionRecibida() {
		return recomendacionRecibida;
	}

	void _setRecomendacionRecibida(Recomendacion recomendador) {
		this.recomendacionRecibida = recomendador;
	}

	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

	Set<MedioPago> _getMediosPago() {
		return mediosPago;
	}

	public Set<Vehiculo> getVehiculos() {
		return new HashSet<>( vehiculos );
	}

	public Set<MedioPago> getMediosPago() {
		return new HashSet<>( mediosPago );
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDni() {
		return dni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre 
				+ ", apellidos=" + apellidos + ", address=" + address + "]";
	}

	/**
	 * Devuelve una lista con las averias disponibles para bono
	 * 
	 * @return lista con averías
	 */
	public List<Averia> getAveriasBono3NoUsadas() {
		List<Averia> averias = new ArrayList<Averia>();

		for (Vehiculo v : this.vehiculos) {
			for (Averia a : v._getAverias()) {
				if (a.esElegibleParaBono3()) {
					averias.add( a );
				}
			}
		}
		return averias;
	}

	/**
	 * Comprueba si se puede elegir para bono por recomendaciones o no.
	 * 
	 * @return true si se puede elegir, false en caso contrario
	 */
	public boolean elegibleBonoPorRecomendaciones() {
		if (recienRegistrado() == false && clienteConReparaciones( this ) == true
				&& this.recomendacionesHechas.size() != 0 && recomendadosConReparaciones() == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Un cliente recien registrado no tendrá vehículos en el taller
	 * 
	 * @return true si los tiene, false en caso contrario.
	 */
	private boolean recienRegistrado() {
		if (this.vehiculos.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Comprueba si el cliente tiene o no averías terminadas Es decir, si ha hecho
	 * reparaciones
	 * 
	 * @return true si las ha hecho, false en caso contrario
	 */
	private boolean clienteConReparaciones(Cliente c) {
		for (Vehiculo v : c._getVehiculos()) {
			for (Averia a : v._getAverias()) {
				if (a.getStatus().equals( AveriaStatus.TERMINADA )) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Comprueba que los recomendados tengan averias reparadas en el taller Se crea
	 * una lista de recomendaciones donde se almacenarán aquellas cuyos recomendados
	 * han hecho reparaciones y además no fue usada para generar bono.
	 * 
	 * @return true si las tienen, false en caso contrario
	 */
	private boolean recomendadosConReparaciones() {
		List<Recomendacion> lista = new ArrayList<Recomendacion>();

		for (Recomendacion r : this.recomendacionesHechas) {
			if (clienteConReparaciones(r.getRecomendado()) == true && r.isUsada_bono() == false) {
				if (!lista.contains( r )) {
					lista.add( r );
				}
			}
		}
		if (lista.size() >= 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Comprueba si el cliente tiene una factra abonada o no
	 * 
	 * @param c,
	 *            el cliente del que se saber si tiene facturas abonadas
	 * @return true, si tienen facturas abonadas, false en caso contrario
	 */
	public boolean clienteConFacturaAbonada() {
		for (Vehiculo v : this._getVehiculos()) {
			for (Averia a : v._getAverias()) {
				if (a.getFactura().isSettled() == true) {
					return true;
				}
			}
		}
		return false;
	}
}
