package uo.ri.ui.foreman.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.business.dto.ClientDto;
import uo.ri.conf.Factory;
import uo.ri.util.exception.BusinessException;

public class UpdateClientAction implements Action{

	@Override
	/**
	 * Se le pide al usuario el ID del cliente que se va
	 * a modificar y sus principales datos.
	 */
	public void execute() throws BusinessException {
		ClientDto dto = new ClientDto();
		
		dto.id = Console.readLong( "Introduzca el ID del cliente que se va a actualizar" );
		dto.name = Console.readString( "Nombre" );
		dto.surname = Console.readString( "Apellidos" );
		dto.addressCity = Console.readString( "Ciudad" );
		dto.addressStreet = Console.readString( "Calle" );
		dto.addressZipcode = Console.readString( "Código Postal" );
		dto.phone = Console.readString( "Teléfono" );
		dto.email = Console.readString( "Email" );
		
		ForemanService fs = Factory.service.forForeman();
		fs.updateClient( dto );
		
		Console.println( "Cliente actualizado con éxito" );
	}

}
