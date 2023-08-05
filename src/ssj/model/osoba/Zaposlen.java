package ssj.model.osoba;

import java.util.ArrayList;
import java.util.List;

import ssj.model.Isplata;

public abstract class Zaposlen extends Osoba{
	protected String nivoSpreme;
	protected Double koeficijent;
	protected int godineStaza;
	public static double osnova = 10000;
	List<Isplata> isplate;
	public Zaposlen() {
		super();
		this.isplate = new ArrayList<Isplata>();
	}
	
	public double izracunajPlatu() {
		return osnova * koeficijent + 1000 * godineStaza + izracunajBonus();
	}
	
	protected abstract double izracunajBonus();

	public String getNivoSpreme() {
		return nivoSpreme;
	}
	public void setNivoSpreme(String nivoSpreme) {
		this.nivoSpreme = nivoSpreme;
	}
	public int getGodineStaza() {
		return godineStaza;
	}
	public void setGodineStaza(int godineStaza) {
		this.godineStaza = godineStaza;
	}

	public List<Isplata> getIsplate() {
		return isplate;
	}

	public void setIsplate(List<Isplata> isplate) {
		this.isplate = isplate;
	}

	public Double getKoeficijent() {
		return koeficijent;
	}

	public void setKoeficijent(Double koeficijent) {
		this.koeficijent = koeficijent;
	}
	
}
