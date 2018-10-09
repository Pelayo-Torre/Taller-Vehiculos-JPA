package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import uo.ri.ui.foreman.action.AddClientAction;
import uo.ri.ui.foreman.action.DeleteClientAction;
import uo.ri.ui.foreman.action.UpdateClientAction;

public class ClientesMenu extends BaseMenu {

	public ClientesMenu() {
		menuOptions = new Object[][] { 
			{ "Jefe de Taller > Gestión de Clientes", null },

			{ "Añadir cliente", AddClientAction.class }, 
			{ "Modificar datos de cliente", UpdateClientAction.class }, 
			{ "Eliminar cliente", DeleteClientAction.class }, 
			{ "Listar clientes", ListadoClientesMenu.class }, 
		};
	}

}
