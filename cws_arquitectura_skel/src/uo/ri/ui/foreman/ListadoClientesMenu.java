package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import uo.ri.ui.foreman.action.FindClientByIdAction;
import uo.ri.ui.foreman.action.FindClientsRecommendedAction;
import uo.ri.ui.foreman.action.ListAllClientsAction;

public class ListadoClientesMenu extends BaseMenu {
	
	public ListadoClientesMenu() {
		menuOptions = new Object[][] { 
			{ "Jefe de Taller > Gestión de Clientes > Listado de Clientes", null },

			{ "Listar todos los clientes", ListAllClientsAction.class }, 
			{ "Ver detalle de un cliente", FindClientByIdAction.class }, 
			{ "Listar clientes recomendados por otro cliente", FindClientsRecommendedAction.class }, 
		};
	}

}
