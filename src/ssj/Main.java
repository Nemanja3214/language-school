package ssj;

import ssj.gui.LoginFrm;
import ssj.managers.Cuvanje;

public class Main {

	public static void main(String[] args) {
		Cuvanje.ucitajSve();
		new LoginFrm();

	}

}
