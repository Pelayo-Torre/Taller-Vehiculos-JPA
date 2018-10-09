package uo.ri.ui.foreman.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class AddClientAction implements Action {

	@Override
	/**
	 * Se pide los datos al usuario para añadir un cliente y se añade
	 * siempre y cuando no esté ya registrado
	 */
	public void execute() throws BusinessException{
		
		ClientDto dto = new ClientDto();
		
		dto.name = Console.readString( "Nombre" );
		dto.surname = Console.readString( "Apellidos" );
		dto.dni = Console.readString( "DNI" );
		dto.addressCity = Console.readString( "Ciudad" );
		dto.addressStreet = Console.readString( "Calle" );
		dto.addressZipcode = Console.readString( "Código Postal" );
		dto.email = Console.readString( "Email" );
		dto.phone = Console.readString( "Teléfono" );
		
		Long idRecomendador = null;
		
		if(Console.readString( "¿Viene usted recomendado por algún cliente? (s/n) " ).equalsIgnoreCase( "s" )){
			idRecomendador = Console.readLong( "Introduzca el id del cliente que le recomendó" );
		}
		
		ForemanService fs = Factory.service.forForeman();
		fs.addClient(dto, idRecomendador);
		
		Console.println( "Cliente añadido correctamente" );
	}

}
