package ssj.model;

import java.time.LocalDateTime;

import ssj.managers.OsobaManager;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Zaposlen;
import ssj.utils.KorisneFunkcije;

public class Isplata extends Transakcija implements Zapisiv{
	private Zaposlen zaposlen;
	
	public Isplata() {
		super();
	}

	

	public Zaposlen getZaposlen() {
		return zaposlen;
	}

	public void setZaposlen(Zaposlen zaposlen) {
		this.zaposlen = zaposlen;
	}

	public static Isplata parse(String[] podaci) {
//		#id; vremeIsplate; iznos; zaposlenID
		Isplata i = new Isplata();
		
		i.setId(Integer.parseInt(podaci[0].trim()));
		i.setVremeNaplate(LocalDateTime.parse(podaci[1].trim(), KorisneFunkcije.dtf1));
		i.setIznos(Double.parseDouble(podaci[2].trim()));
		i.setZaposlen(OsobaManager.getInstance().getZaposleniMapa().get(Integer.parseInt(podaci[3].trim())));
		
		return i;
	}
//	String[] kolone = {"ID", "Vreme isplate", "Iznos", "Primalac"};
	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return id;
		case 1: return vremeNaplate;
		case 2: return iznos;
		case 3: return this.getZaposlen().toString();
		case 4: return "Škola";
		default: return "";
		}
	}

	@Override	
	public String toFile() {
//		#id; vremeIsplate; iznos; zaposlenId
		return String.join(";", Integer.toString(this.id), vremeNaplate.format(KorisneFunkcije.dtf1), Double.toString(iznos),
				Integer.toString(zaposlen.getId()), Boolean.toString(obrisan));
		}



	public Isplata(int id, LocalDateTime vremeNaplate, double iznos, Zaposlen zaposlen) {
		super(id, vremeNaplate, iznos);
		this.zaposlen = zaposlen;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((zaposlen == null) ? 0 : zaposlen.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Isplata other = (Isplata) obj;
		if (zaposlen == null) {
			if (other.zaposlen != null)
				return false;
		} else if (!zaposlen.equals(other.zaposlen))
			return false;
		return true;
	}



	
	
	
}
