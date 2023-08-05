package ssj.model.zahtev;

import java.time.LocalDateTime;

import ssj.managers.ZahtevManager;
import ssj.model.Kurs;
import ssj.model.Zapisiv;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;
import ssj.utils.Izbrisiv;
import ssj.utils.KorisneFunkcije;

public class Zahtev extends Izbrisiv implements Zapisiv{
	private int id;
	private Ucenik ucenik;
	private Sekretar sekretar;
	private Kurs kurs;
	private ZahtevStanje stanje;
	private LocalDateTime vremeKreiranja;
	
	public Zahtev() {
		this.stanje = Kreiran.getInstance();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenik ucenik) {
		this.ucenik = ucenik;
	}

	public Sekretar getSekretar() {
		return sekretar;
	}

	public void setSekretar(Sekretar sekretar) {
		this.sekretar = sekretar;
	}

	public ZahtevStanje getStanje() {
		return stanje;
	}

	public void setStanje(ZahtevStanje stanje) {
		this.stanje = stanje;
	}

	public Kurs getKurs() {
		return kurs;
	}

	public void setKurs(Kurs kurs) {
		this.kurs = kurs;
	}
	

	public LocalDateTime getVremeKreiranja() {
		return vremeKreiranja;
	}

	public void setVremeKreiranja(LocalDateTime vremeKreiranja) {
		this.vremeKreiranja = vremeKreiranja;
	}

	public Object toCell(int columnIndex) {
		switch(columnIndex) {
		case 0: return "" + this.id;
		case 1: return this.ucenik.getIme() + " " + this.ucenik.getPrezime();
		case 2: return this.kurs.getNaziv();
		case 3: {
			if(this.stanje instanceof Prihvacen)
				return "Prihvaćen";
			else if(this.stanje instanceof Odbijen)
				return "Odbijen";
			else if(this.stanje instanceof UObradi)
				return "U obradi";
			else
				return "Kreiran";
		}
		case 4: return vremeKreiranja.format(KorisneFunkcije.dtf1);
		default: return "";
		}
	}

	@Override
	public String toString() {
		return "Zahtev [id=" + id + ", ucenik=" + ucenik + ", sekretar=" + sekretar + ", kurs=" + kurs + ", stanje="
				+ stanje + "]";
	}

//	# id; stanje; sekretar; ucenik; kurs
	@Override
	public String toFile() {
		String sekretarId = sekretar == null ? "": Integer.toString(sekretar.getId());
		return String.join("; ", Integer.toString(id), Integer.toString(ZahtevManager.idStanja(stanje)), sekretarId,
				Integer.toString(ucenik.getId()), Integer.toString(kurs.getId()), vremeKreiranja.format(KorisneFunkcije.dtf1), Boolean.toString(obrisan));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Zahtev other = (Zahtev) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Zahtev(int id, Ucenik ucenik, Sekretar sekretar, Kurs kurs, ZahtevStanje stanje,
			LocalDateTime vremeKreiranja) {
		super();
		this.id = id;
		this.ucenik = ucenik;
		this.sekretar = sekretar;
		this.kurs = kurs;
		this.stanje = stanje;
		this.vremeKreiranja = vremeKreiranja;
	}

	
	
	

}
