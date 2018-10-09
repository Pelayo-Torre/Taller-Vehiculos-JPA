package uo.ri.ui.admin.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.AdminService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class UpdateMechanicAction implements Action {

	@Override
	/**
	 * Le pide al usuario los datos para actualizar el mecánico
	 */
	public void execute() throws BusinessException {
		
		Long id = Console.readLong( "Id del mecánico" ); 
		String nombre = Console.readString( "Nombre" ); 
		String apellidos = Console.readString( "Apellidos" );
		
		AdminService as = Factory.service.forAdmin();

		MechanicDto m = as.findMechanicById( id );
		if ( m == null) {
			throw new BusinessException( "No existe el mecánico" );
		}
		m.name = nombre;
		m.surname = apellidos;  
		
		as.updateMechanic( m );
		
		Console.println( "Mecánico actualizado" );
	}

}
