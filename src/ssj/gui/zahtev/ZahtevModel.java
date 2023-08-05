package ssj.gui.zahtev;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Termin;
import ssj.model.zahtev.Zahtev;

public class ZahtevModel extends AbstractTableModel{
	private static final long serialVersionUID = 2274540407049459039L;
	private List<Zahtev> zahtevi;
	private String[] kolone = {"ID", "Učenik", "Kurs", "Stanje", "Vreme kreiranja"};

	public ZahtevModel(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}
	@Override
	public int getRowCount() {
		return this.zahtevi.size();
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
		Zahtev z = this.zahtevi.get(rowIndex);
		return z.toCell(columnIndex);
	}
	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}
	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}

}
