package ssj.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ssj.managers.CenovnikManager;
import ssj.utils.KorisneFunkcije;

public class Cenovnik implements Zapisiv{
	private int id;
	private LocalDate vaziOd;
	private LocalDate vaziDo;
	private Map<Kurs, Double> cene;
	private Map<Kurs, Double> popusti;
	private Map<Kurs, Double> uvecanjaCena;
	
	public static Double podrazumevanaCena = 10000.0;
	public static Double podrazumevanoUvecanje = 5000.0;
	public static Double podrazumevanPopust = 10.0;
	
	public static Cenovnik trenutni;
	public static int norma = 10;
	
	
	public Cenovnik() {
		this.cene = new HashMap<Kurs, Double>();
		this.popusti = new HashMap<Kurs, Double>();
		this.uvecanjaCena = new HashMap<Kurs, Double>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getVaziOd() {
		return vaziOd;
	}

	public void setVaziOd(LocalDate vaziOd) {
		this.vaziOd = vaziOd;
	}

	public LocalDate getVaziDo() {
		return vaziDo;
	}

	public void setVaziDo(LocalDate vaziDo) {
		this.vaziDo = vaziDo;
	}

	public Map<Kurs, Double> getCene() {
		return cene;
	}

	public void setCene(Map<Kurs, Double> cene) {
		this.cene = cene;
	}

	public Map<Kurs, Double> getPopusti() {
		return popusti;
	}

	public void setPopusti(Map<Kurs, Double> popusti) {
		this.popusti = popusti;
	}

	

	public Map<Kurs, Double> getUvecanjaCena() {
		return uvecanjaCena;
	}

	public void setUvecanjaCena(Map<Kurs, Double> uvecanjaCena) {
		this.uvecanjaCena = uvecanjaCena;
	}
	
	public static Cenovnik parse(String[] podaci) {
//		# id; vaziOd; vaziDo
		Cenovnik c = new Cenovnik();
		c.setId(Integer.parseInt(podaci[0].trim()));
		c.setVaziOd(LocalDate.parse(podaci[1].trim(), KorisneFunkcije.dtf));
		c.setVaziDo(LocalDate.parse(podaci[2].trim(), KorisneFunkcije.dtf));
		
		CenovnikManager.getInstance().azurirajTrenutni();
		return c;
	}

	@Override
	public String toFile() {
		return String.join("; ", Integer.toString(id), vaziOd.format(KorisneFunkcije.dtf), vaziDo.format(KorisneFunkcije.dtf));
	}

	public Cenovnik(int id, LocalDate vaziOd, LocalDate vaziDo) {
		this();
		this.id = id;
		this.vaziOd = vaziOd;
		this.vaziDo = vaziDo;

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
		Cenovnik other = (Cenovnik) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
}
