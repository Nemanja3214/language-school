package ssj.gui.ucenik;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.managers.OsobaManager;
import ssj.model.Termin;
import ssj.model.osoba.Ucenik;

public class UcenikModel extends AbstractTableModel{
	private static final long serialVersionUID = 5394522343122475282L;
	private String[] kolone = {"ID", "Ime", "Prezime", "Korisničko ime", "Pol", "Datum rodjenja", "Adresa", "Telefon", "Stanje računa"};
	private List<Ucenik> ucenici;
	
	public UcenikModel(List<Ucenik> ucenici) {
		this.ucenici= ucenici;
	}

	@Override
	public int getRowCount() {
		return ucenici.size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}
	
	@Override
	public String getColumnName(int index) {
		return kolone[index];
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ucenik u = ucenici.get(rowIndex);
		return u.toCell(columnIndex);
	}

	public List<Ucenik> getUcenici() {
		return ucenici;
	}

	public void setUcenici(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}

}
