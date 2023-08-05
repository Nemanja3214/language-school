package ssj.model;

public enum Meseci {
	JANUAR("Januar"), FEBRUAR("Februar"), MART("Mart"), APRIL("April"), MAJ("Maj"), JUN("Jun"), JUL("Jul"),
	AVGUST("Avgust"), SEPTEMBAR("Septembar"), OKTOBAR("Oktobar"), NOVEMBAR("Novembar"), DECEMBAR("Decembar");
	
	public String naziv;
	private Meseci(String naziv) {
		this.naziv = naziv;
		
	}

	
}
