package ssj.gui.rezultat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Rezultat;
import ssj.model.osoba.Predavac;

public class RezultatModel extends AbstractTableModel{
	private static final long serialVersionUID = 1265158181919584394L;
	private List<Rezultat> rezultati;
	private String[] kolone = {"ID", "Broj bodova", "Ocena", "Položio/Nije položio", "Učenik", "Vreme rada", "Test"};
	
	public RezultatModel(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}
	@Override
	public int getRowCount() {
		return this.rezultati.size();
	}

	@Override
	public int getColumnCount() {
		return this.kolone.length;
	}
	@Override
	public String getColumnName(int columnIndex) {
		return kolone[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Rezultat r = this.rezultati.get(rowIndex);
		return r.toCell(columnIndex);
	}
	public List<Rezultat> getRezultati() {
		return rezultati;
	}
	public void setRezultati(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}

}
