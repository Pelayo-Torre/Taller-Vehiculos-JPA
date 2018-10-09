package uo.ri.business.impl;

import java.util.List;

import uo.ri.business.AdminService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.dto.VoucherSummary;
import uo.ri.business.impl.admin.AddMechanic;
import uo.ri.business.impl.admin.DeleteMechanic;
import uo.ri.business.impl.admin.FindAllMechanics;
import uo.ri.business.impl.admin.FindMechanicById;
import uo.ri.business.impl.admin.GenerateVouchersByNumberOfBreakdowns;
import uo.ri.business.impl.admin.UpdateMechanic;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class AdminServiceImpl implements AdminService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	/**
	 * Se añade un nuevo mecánico al sistema.
	 * 
	 * @param mecanico,
	 *            contiene los datos del mecánico que se va a añadir
	 */
	public void addMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute( new AddMechanic( mecanico ) );
	}

	@Override
	/**
	 * Se actualiza un mecánico en el sistema.
	 * 
	 * @param mecanico,
	 *            contiene los datos del mecánico a actualizar.
	 */
	public void updateMechanic(MechanicDto mecanico) throws BusinessException {
		executor.execute( new UpdateMechanic( mecanico ) );
	}

	@Override
	/**
	 * Se elimina un mecánico del sistema.
	 * 
	 * @param idMecanico,
	 *            ID del mecánico que se va a eliminar
	 */
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		executor.execute( new DeleteMechanic( idMecanico ) );
	}

	@Override
	/**
	 * Devuelve una lista con todos los mecánicos del sistema.
	 * 
	 * @return lista de mecánicos
	 */
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return executor.execute( new FindAllMechanics() );
	}

	@Override
	/**
	 * Busca un mecánico en el sistema a partir de su ID.
	 * 
	 * @param id,
	 *            id del mecánico que se quiere buscar.
	 * @return mechanicDto, datos del mecánico buscado.
	 */
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		return executor.execute( new FindMechanicById( id ) );
	}

	@Override
	/**
	 * Genera los bonos por 3 averías no usadas ya para bono.
	 * 
	 * @return nº de bonos generados.
	 */
	public int generateVouchers() throws BusinessException {
		return executor.execute( new GenerateVouchersByNumberOfBreakdowns() );
	}

	@Override
	public List<VoucherDto> findVouchersByClientId(Long id) throws BusinessException {
		return null;
	}

	@Override
	public List<VoucherSummary> getVoucherSummary() throws BusinessException {
		return null;
	}

}
