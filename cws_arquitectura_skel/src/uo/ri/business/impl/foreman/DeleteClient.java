package uo.ri.business.impl.foreman;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.business.repository.RecomendacionRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Cliente;
import uo.ri.model.MedioPago;
import uo.ri.model.Recomendacion;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

public class DeleteClient implements Command<Void> {

	private Long idCliente;

	private ClienteRepository cr = Factory.repository.forCliente();
	private RecomendacionRepository rr = Factory.repository.forRecomendacion();
	private MedioPagoRepository mpr = Factory.repository.forMedioPago();

	public DeleteClient(Long id) {
		this.idCliente = id;
	}

	@Override
	/**
	 * Elimina un cliente del sistema
	 */
	public Void execute() throws BusinessException {
		Cliente cliente = cr.findById( idCliente );

		Check.isNotNull(cliente, "El cliente no existe");

		assertCambeDeleted( cliente );

		eliminarRecomendaciones( cliente );

		eliminarMetalico( cliente );

		cr.remove( cliente );

		return null;
	}

	/**
	 * Comprueba si el cliente puede ser eliminado o no
	 * 
	 * @param c,
	 *            el cliente a eliminar
	 * @throws BusinessException,
	 *             Puede ser que el cliente tenga vehículos registrados en el
	 *             sistema
	 */
	private void assertCambeDeleted(Cliente c) throws BusinessException {
		Check.isTrue(c.getVehiculos().isEmpty(), 
				"El cliente no puede ser eliminado al tener vehículos registrados");
	}

	/**
	 * Elimina las recomendaciones del cliente y su recomendación
	 * 
	 * @param c,
	 *            cliente del que se van a eliminar recomendaciones
	 */
	private void eliminarRecomendaciones(Cliente c) {
		
		if(c.getRecomendacionesHechas() != null) {
			for(Recomendacion r : c.getRecomendacionesHechas()) {
				r.unlink();
				rr.remove(r);
			}
		}
		
		if (c.getRecomendacionRecibida() != null) {
			Recomendacion recibida = c.getRecomendacionRecibida();
			recibida.unlink();
			rr.remove( recibida );
		}
	}

	/**
	 * Elimina el medioPago del cliente asociado.
	 * 
	 * @param c,
	 *            cliente del que se va a eliminar el medioPago
	 */
	private void eliminarMetalico(Cliente c) {
		for (MedioPago m : c.getMediosPago()) {
			Association.Pagar.unlink(c, m);
			mpr.remove( m );
		}

	}

}
