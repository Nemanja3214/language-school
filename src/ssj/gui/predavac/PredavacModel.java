package ssj.gui.predavac;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.osoba.Predavac;

public class PredavacModel extends AbstractTableModel{
	private static final long serialVersionUID = -7044827849703161146L;
	private String[] kolone = {"ID", "Ime", "Prezime", "Korisničko ime", "Pol", "Datum rodjenja", "Adresa", "Telefon", "Nivo spreme", "Staž(godine)", "Koeficijent"};
	private List<Predavac> predavaci;

	public PredavacModel(List<Predavac> predavaci) {
		this.predavaci = predavaci;
	}
	@Override
	public int getRowCount() {
		return this.predavaci.size();
	}

	@Override
	public int getColumnCount() {
		return this.kolone.length;
	}
	@Override
	public String getColumnName(int index) {
		return kolone[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Predavac p = this.predavaci.get(rowIndex);
		return p.toCell(columnIndex);
	}
	public List<Predavac> getPredavaci() {
		return predavaci;
	}
	public void setPredavaci(List<Predavac> predavaci) {
		this.predavaci = predavaci;
	}

}
