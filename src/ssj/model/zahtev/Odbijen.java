package ssj.model.zahtev;

public class Odbijen extends ZahtevStanje{
	private static Odbijen instance = null;
	public int id;
	
	private Odbijen() {
		
	}
	
	public static Odbijen getInstance() {
		if(instance == null) 
			instance = new Odbijen();
		return instance;
	}

	@Override
	public void ispisStanja() {
		System.out.println("Zahtev je odbijen.");
		
	}
	
	

}
