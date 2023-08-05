package ssj.model.zahtev;

public class Kreiran extends ZahtevStanje{
	private static Kreiran instance = null;
	
	private Kreiran() {
		
	}
	
	public static Kreiran getInstance() {
		if(instance == null) 
			instance = new Kreiran();
		return instance;
	}


	@Override
	public void ispisStanja() {
		System.out.println("Zahtev je kreiran");
		
	}

}
