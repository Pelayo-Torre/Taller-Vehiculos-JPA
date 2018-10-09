package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.GenerateVouchersByNumberOfBreakdownsAction;


public class MediosPagoMenu extends BaseMenu{
	
	public MediosPagoMenu() {
		menuOptions = new Object[][] { 
			{ "Administrador > Gestión de medios de pago", null },

			{ "Generar Bonos", GenerateVouchersByNumberOfBreakdownsAction.class }, 
			
		};
	}

}
