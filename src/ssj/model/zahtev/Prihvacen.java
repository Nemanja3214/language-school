package ssj.model.zahtev;

public class Prihvacen extends ZahtevStanje{
	private static Prihvacen instance = null;
	
	private Prihvacen() {
		
	}
	
	public static Prihvacen getInstance() {
		if(instance == null) 
			instance = new Prihvacen();
		return instance;
	}


	@Override
	public void ispisStanja() {
		System.out.println("Zahtev je prihvaćen");
		
	}

}
