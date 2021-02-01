package fr.Jack;

import fr.Jack.exceptions.MenuLaunchException;
import fr.Jack.menus.MainMENU;

public class Main {

	public static void main(String[] args) {
        try {
			new MainMENU();
		} catch (MenuLaunchException e1) {
			e1.setMenu("MainMENU");
			e1.getMessage();
			e1.printStackTrace();
		}
	}

}
