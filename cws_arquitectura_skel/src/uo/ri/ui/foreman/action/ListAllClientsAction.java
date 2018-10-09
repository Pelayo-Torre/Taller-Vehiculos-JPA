package uo.ri.ui.foreman.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;
import uo.ri.util.exception.BusinessException;

public class ListAllClientsAction implements Action{

	@Override
	/**
	 * Lista todos los clientes del sistema
	 */
	public void execute() throws BusinessException
	{
		Console.println( "Listado de todos los clientes:" );
		
		ForemanService fs = Factory.service.forForeman();
		List<ClientDto> lista = fs.findAllClients();
		
		for(ClientDto dto : lista){
			Printer.printClient( dto );
		}
		
	}

}
