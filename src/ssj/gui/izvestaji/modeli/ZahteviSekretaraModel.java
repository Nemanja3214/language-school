package ssj.gui.izvestaji.modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.osoba.Sekretar;

public class ZahteviSekretaraModel extends AbstractTableModel{
	private static final long serialVersionUID = 4249775061998765681L;
	private List<Sekretar> sekretari;
	private String[] kolone= {"Sekretar", "Broj obradjenih zahteva"};

	public ZahteviSekretaraModel(List<Sekretar> sekretari) {
		this.sekretari = sekretari;
	}
	
	@Override
	public String getColumnName(int colIndex) {
		return kolone[colIndex];
	}
	
	@Override
	public int getRowCount() {
		return this.sekretari.size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Sekretar s = this.sekretari.get(rowIndex);
		switch(columnIndex) {
		case 0: return s.getIme() + " " + s.getPrezime();
		case 1: return s.getZahtevi().size();
		default: return "";
		}
	}

}
