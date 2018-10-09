package uo.ri.business.impl.admin;

import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.exception.Check;

public class DeleteMechanic implements Command<Void> {

	private Long idMecanico;

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	/**
	 * Elimina un mecánico del repositorio
	 */
	public Void execute() throws BusinessException {
		MecanicoRepository r = Factory.repository.forMechanic();
		Mecanico m = r.findById( idMecanico );

		Check.isNotNull(m, "El mecánico no existe");
		assertCambeDeleted( m );

		r.remove( m );

		return null;
	}

	/**
	 * Comprueba que el mecánico no tenga intervenciones ni averías asignadas
	 * 
	 * @param m,
	 *            el mecánico que se va a eliminar
	 * @throws BusinessException,
	 *             puede ser que el mecánico tenga averías e intervenciones, por lo
	 *             tanto no se podrá borrar.
	 */
	private void assertCambeDeleted(Mecanico m) throws BusinessException {
		Check.isTrue(m.getIntervenciones().isEmpty(), "El mecánico tiene intervenciones");
		Check.isTrue(m.getAsignadas().isEmpty(), "El mecánico tiene averias asignadas");
	}

}
