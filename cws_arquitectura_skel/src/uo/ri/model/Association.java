package uo.ri.model;

public class Association {

	public static class Poseer {

		/**
		 * Hace el link entre un cliente y un vehículo. Asigna el vehículo al cliente y
		 * añade el vehículo a la lista de vehículos del cliente
		 * 
		 * @param cliente
		 *            a linkar
		 * @param vehiculo
		 *            a linkar
		 */
		public static void link(Cliente cliente, Vehiculo vehiculo) {
			vehiculo._setCliente( cliente );
			cliente._getVehiculos().add( vehiculo );
		}

		/**
		 * Hace el unlink entre el cliente y el vehículo Elimina el vehículo de la lista
		 * de vehículos del cliente y desasigna el vehículo al cliente
		 * 
		 * @param cliente
		 *            a hacer unlink
		 * @param vehiculo
		 *            a hacer unlink
		 */
		public static void unlink(Cliente cliente, Vehiculo vehiculo) {
			cliente._getVehiculos().remove( vehiculo );
			vehiculo._setCliente( null );
		}
	}

	public static class Clasificar {

		/**
		 * Hace el link entre tipoVehiculo y vehiculo Asigna el vehículo al tipoVehiculo
		 * y añade a la lista de vehículo de tipoVehiculo el vehículo
		 * 
		 * @param tipoVehiculo
		 *            a linkar
		 * @param vehiculo
		 *            a linkar
		 */
		public static void link(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			vehiculo._setTipo( tipoVehiculo );
			tipoVehiculo._getVehiculos().add( vehiculo );
		}

		/**
		 * Hace el unlink entre tipoVehiculo y vehiculo Elimina de la lista de
		 * tipoVehiculo el vehículo y desasigna el vehiculo al tipoVehiculo
		 * 
		 * @param tipoVehiculo
		 *            a hacer unlink
		 * @param vehiculo
		 *            a hacer unlink
		 */
		public static void unlink(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			tipoVehiculo._getVehiculos().remove( vehiculo );
			vehiculo._setTipo( null );
		}
	}

	public static class Pagar {

		/**
		 * Hace el link entre cliente y medioPago Asigna el medioPago al cliente y a
		 * lista de mediosPago del cliente el medioPago
		 * 
		 * @param cliente
		 *            a linkar
		 * @param medioPago
		 *            a linkar
		 */
		public static void link(Cliente cliente, MedioPago medioPago) {
			medioPago._setCliente( cliente );
			cliente._getMediosPago().add( medioPago );
		}

		/**
		 * Hace el unlink entre cliente y medioPago Elimina de la lista de mediosPago
		 * del cliente el medioPago y desasigna el medioPago del cliente
		 * 
		 * @param cliente
		 *            a hacer unlink
		 * @param medioPago
		 *            a hacer unlink
		 */
		public static void unlink(Cliente cliente, MedioPago medioPago) {
			cliente._getMediosPago().remove( medioPago );
			medioPago._setCliente( null );
		}
	}

	public static class Averiar {

		/**
		 * Hace el link entre vehículo y averia Asigna la avería al vehículo y añade a
		 * la lista de averías del vehiculo la averia. Además se aumenta el numero de
		 * averias del vehiculo en una unidad.
		 * 
		 * @param vehiculo
		 *            a linkar
		 * @param averia
		 *            a linkar
		 */
		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo( vehiculo );
			vehiculo._getAverias().add( averia );
			vehiculo.setNumAverias( vehiculo.getNumAverias() + 1 );
		}

		/**
		 * Hace el unlink entre averia y vehiculo elimina de la lista de averias del
		 * vehiculo la averia y desasigna la averia al vehiculo. Además se decrementa en
		 * una unidad el número de averías del vehículo
		 * 
		 * @param vehiculo
		 *            a hacer unlink
		 * @param averia
		 *            a hacer unlink
		 */
		public static void unlink(Vehiculo vehiculo, Averia averia) {
			vehiculo._getAverias().remove( averia );
			averia._setVehiculo( null );
			vehiculo.setNumAverias( vehiculo.getNumAverias() - 1 );
		}
	}

	public static class Facturar {

		/**
		 * Hace el link entre factura y averia Asigna la avería a la factura y añade a
		 * la lista de averias de la factura la averia
		 * 
		 * @param factura
		 *            a linkar
		 * @param averia
		 *            a linkar
		 */
		public static void link(Factura factura, Averia averia) {
			averia._setFactura( factura );
			factura._getAverias().add( averia );
		}

		/**
		 * Hace el unlink entre factura y averia elimina de la lista de averias de la
		 * factura, la avería y desasigna la avería de la factura.
		 * 
		 * @param factura
		 *            a hacer unlink
		 * @param averia
		 *            a hacer unlink
		 */
		public static void unlink(Factura factura, Averia averia) {
			factura._getAverias().remove( averia );
			averia._setFactura( null );
		}
	}

	public static class Cargar {

		/**
		 * Hace el link de Cargo Asigna el cargo a la factura Asigna el cargo al medio
		 * de pago Añade a la lista de cargos de la factura el cargo Añade a la lista de
		 * cargos del medioPago el cargo
		 * 
		 * @param cargo
		 *            a linkar
		 */
		public static void link(Cargo cargo) {
			cargo._setFactura( cargo.getFactura() );
			cargo._setMedioPago( cargo.getMedioPago() );

			cargo.getFactura()._getCargos().add( cargo );
			cargo.getMedioPago()._getCargos().add( cargo );
		}

		/**
		 * Hace el unlink de cargo Elimina de la lista de cargos del medioPago el cargo
		 * Elimina de la lista de cargos de la factura el cargo Desasigna el cargo al
		 * medioPago Desasigna el cargo a la factura
		 * 
		 * @param cargo
		 */
		public static void unlink(Cargo cargo) {
			cargo.getMedioPago()._getCargos().remove( cargo );
			cargo.getFactura()._getCargos().remove( cargo );

			cargo._setMedioPago( null );
			cargo._setFactura( null );
		}
	}

	public static class Asignar {

		/**
		 * Hace el link entre mecanico y averia Asigna la averia el mecanico Añade la
		 * averia a la lista de averias del mecanico
		 * 
		 * @param mecanico
		 *            a linkar
		 * @param averia
		 *            a linkar
		 */
		public static void link(Mecanico mecanico, Averia averia) {
			averia._setMecanico( mecanico );
			mecanico._getAsignadas().add( averia );
		}

		/**
		 * Hace el unlink de mecanico y averia Elimina la averia de la lista de averias
		 * del mecanico Desasigna la averia al mecanico
		 * 
		 * @param mecanico
		 *            a hacer unlink
		 * @param averia
		 *            a hacer unlink
		 */
		public static void unlink(Mecanico mecanico, Averia averia) {
			mecanico._getAsignadas().remove( averia );
			averia._setMecanico( null );
		}
	}

	public static class Intervenir {

		/**
		 * Hace el link entre averia, intervencion y mecanico. Asigna la intervencion a
		 * la averia Asigna la intervencion al mecanico Añade la intervencion a la lista
		 * de intervenciones del la averia Añade la intervencion a la lista de
		 * intervenciones del mecanico
		 * 
		 * @param averia
		 *            a linkar
		 * @param intervencion
		 *            a linkar
		 * @param mecanico
		 *            a linkar
		 */
		public static void link(Averia averia, Intervencion intervencion, Mecanico mecanico) {
			intervencion._setAveria( averia );
			intervencion._setMecanico( mecanico );

			averia._getIntervenciones().add( intervencion );
			mecanico._getIntervenciones().add( intervencion );
		}

		/**
		 * Hace el unlink entre intervencion, averia y mecanico Elimina de la lista de
		 * intervenciones de la averia la intervencion Elmina de la lista de
		 * intervenciones del mecanico la intervencion Desasigna la intervencion a la
		 * averia Desasigna la intervencion al mecanico
		 * 
		 * @param intervencion
		 *            a hacer unlink
		 */
		public static void unlink(Intervencion intervencion) {
			intervencion.getAveria()._getIntervenciones().remove( intervencion );
			intervencion.getMecanico()._getIntervenciones().remove( intervencion );

			intervencion._setAveria( null );
			intervencion._setMecanico( null );

		}
	}

	public static class Sustituir {

		/**
		 * Hace el link entre sustitucion, intervencion y repuesto Asigna la sustitución
		 * a la intervencion Asigna la sustitucion al repuesto Añade la sustitucion a la
		 * lista de sustituciones del repuesto Añade la sustitucion a la lsta de
		 * sustituciones de la intervencion
		 * 
		 * @param sustitucion
		 *            a linkar
		 * @param intervencion
		 *            a linkar
		 * @param repuesto
		 *            a linkar
		 */
		public static void link(Sustitucion sustitucion, Intervencion intervencion, 
				Repuesto repuesto) {
			sustitucion._setIntervencion( intervencion );
			sustitucion._setRepuesto( repuesto );

			sustitucion.getRepuesto()._getSustituciones().add( sustitucion );
			sustitucion.getIntervencion()._getSustituciones().add( sustitucion );
		}

		/**
		 * Hace el unlink de sustitucion, intervencion y repuesto Elimina la sustitucion
		 * de la lista de sustituciones de la intervencion Elimina la sustitucion de la
		 * lista de sustituciones del repuesto Desasigna la sustitucion a la
		 * intervencion Desasigna la sustitucion al repuesto
		 * 
		 * @param sustitucion
		 *            a hacer unlink
		 */
		public static void unlink(Sustitucion sustitucion) {
			sustitucion.getIntervencion()._getSustituciones().remove( sustitucion );
			sustitucion.getRepuesto()._getSustituciones().remove( sustitucion );

			sustitucion._setIntervencion( null );
			sustitucion._setRepuesto( null );
		}
	}

	public static class Recomendar {

		/**
		 * Hace el link entre el cliente recomendador, el recomendado y la recomendacion
		 * 
		 * @param recomendador,
		 *            el cliente que recomienda al cliente recomendado
		 * @param recomendado,
		 *            el cliente que es recomendado por el cliente recomendador
		 * @param recomendacion,
		 *            la que le da el recomendador al recomendado
		 */
		public static void link(Cliente recomendador, Cliente recomendado, 
				Recomendacion recomendacion) {

			recomendacion._setRecomendado( recomendado );
			recomendacion._setRecomendador( recomendador );

			recomendado._setRecomendacionRecibida( recomendacion );
			recomendador._getRecomendacionesHechas().add( recomendacion );

		}

		/**
		 * Hace el unlnk entre el cliente recomendador, el recomendado y la
		 * recomendacion
		 * 
		 * @param recomendacion,
		 *            la que le da el recomendador al recomendado
		 */
		public static void unlink(Recomendacion recomendacion) {
			recomendacion.getRecomendador()._getRecomendacionesHechas().remove( recomendacion );
			recomendacion.getRecomendado()._setRecomendacionRecibida( null );

			recomendacion._setRecomendado( null );
			recomendacion._setRecomendador( null );
		}

	}

}
