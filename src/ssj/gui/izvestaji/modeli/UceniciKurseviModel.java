package ssj.gui.izvestaji.modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.managers.RacunManager;
import ssj.model.Racun;
import ssj.model.osoba.Ucenik;

public class UceniciKurseviModel extends AbstractTableModel {
	private static final long serialVersionUID = -5833699401607766773L;
	private String[] kolone = {"Učenik", "Broj pohadjanja", "Ukupan iznos"};
	private List<Ucenik> ucenici;
	
	public UceniciKurseviModel(List<Ucenik> ucenici) {
		this.ucenici = new ArrayList<Ucenik>();
		for(Ucenik u: ucenici) {
			if(!u.isObrisan()) {
				this.ucenici.add(u);
			}
		}
	}
	
	@Override
	public String getColumnName(int colIndex) {
		return kolone[colIndex];
	}
	
	@Override
	public int getRowCount() {
		return this.ucenici.size();
	}

	@Override
	public int getColumnCount() {
		return this.kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ucenik u = this.ucenici.get(rowIndex);
		double ukupanIznos = u.getUkupanIznos();
		
		switch(columnIndex) {
		case 0: return u.getIme() + " " + u.getPrezime();
		case 1: return u.getPohadjanja().size();
		case 2: return ukupanIznos;
		default: return "";
		}
	}

}
