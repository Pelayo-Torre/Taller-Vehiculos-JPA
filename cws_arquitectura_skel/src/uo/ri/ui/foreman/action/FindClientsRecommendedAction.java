package uo.ri.ui.foreman.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;
import uo.ri.util.exception.BusinessException;

public class FindClientsRecommendedAction implements Action{

	@Override
	/**
	 * Se pide al usuario el ID del cliente recomendador para 
	 * mostrar sus clientes recomendados.
	 */
	public void execute() throws BusinessException {
		
		Long id = Console.readLong( "ID del cliente recomendador" );
		
		ForemanService fs = Factory.service.forForeman();
		List<ClientDto> lista = fs.findRecomendedClientsByClienteId( id );
		
		for(ClientDto dto : lista){
			Printer.printClient( dto );
		}
		
		if(lista.isEmpty()){
			Console.println( "El cliente seleccionado no ha recomendado a ningún cliente" );
		}
		
	}

}
