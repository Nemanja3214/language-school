package ssj.model.zahtev;

public class UObradi extends ZahtevStanje{
	private static UObradi instance = null;
	
	private UObradi() {
		
	}
	
	public static UObradi getInstance() {
		if(instance == null) 
			instance = new UObradi();
		return instance;
	}

	@Override
	public void ispisStanja() {
		System.out.println("Zahtev je u obradi.");
		
	}

}
