package ssj.model.zahtev;

public abstract class ZahtevStanje {
	protected ZahtevStanje trenutnoStanje;

	
	public abstract void ispisStanja();
	
	public void setStanje(ZahtevStanje zs) {
		this.trenutnoStanje = zs;
	}
}
