package uo.ri.ui.foreman.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;
import uo.ri.util.exception.BusinessException;

public class FindClientByIdAction implements Action{

	@Override
	/**
	 * Se pide al usuario el ID del cliente del que desea saber
	 * la información
	 */
	public void execute() throws BusinessException {
		
		Long id = Console.readLong( "ID del cliente que desea buscar" );
		
		ForemanService fs = Factory.service.forForeman();
		ClientDto dto = fs.findClientById( id );
		
		if(dto != null){
			Printer.printClient( dto );
		} else {
			Console.println( "El cliente buscado no se encuentra registrado en el sistema" );
		}
	}

}
