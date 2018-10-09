package uo.ri.ui.foreman.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class DeleteClientAction implements Action{

	@Override
	/**
	 * Se pide al usuario el ID del cliente a eliminar, 
	 * y si existe y cumple unas condiciones, se elimina
	 */
	public void execute() throws BusinessException {
		
		Long id = Console.readLong( "ID del cliente que se va a eliminar" );
		
		ForemanService fs = Factory.service.forForeman();
		fs.deleteClient( id );
		
		Console.println( "Cliente borrado correctamente" );
	}

}
