package ssj.gui.sekretar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.osoba.Predavac;
import ssj.model.osoba.Sekretar;

public class SekretarModel extends AbstractTableModel{
	private static final long serialVersionUID = -7044827849703161146L;
	private String[] kolone = {"ID", "Ime", "Prezime", "Korisničko ime", "Pol", "Datum rodjenja", "Adresa", "Telefon", "Nivo spreme", "Staž(godine)", "Koeficijent"};
	private List<Sekretar> sekretari;

	public SekretarModel(List<Sekretar> sekretari) {
		this.sekretari = sekretari;
	}
	@Override
	public int getRowCount() {
		return this.sekretari.size();
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
		Sekretar s = this.sekretari.get(rowIndex);
		return s.toCell(columnIndex);
	}
	public List<Sekretar> getSekretari() {
		return sekretari;
	}
	public void setSekretari(List<Sekretar> sekretari) {
		this.sekretari = sekretari;
	}

}
