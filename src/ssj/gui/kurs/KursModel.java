package ssj.gui.kurs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Kurs;

public class KursModel extends AbstractTableModel{
	private static final long serialVersionUID = 6585076907386149873L;
	private String[] kolone = {"ID", "Naziv", "Cena", "Uvećanje cena","Popust", "Jezik", "Predavač", "Broj učenika"};
	private List<Kurs> kursevi;
	
	public KursModel(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}

	@Override
	public int getRowCount() {
		return this.kursevi.size();
	}

	@Override
	public int getColumnCount() {
		return this.kolone.length;
	}
	public String getColumnName(int columnIndex) {
		return kolone[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Kurs k = this.kursevi.get(rowIndex);
		return k.toCell(columnIndex);
	}

	public List<Kurs> getKursevi() {
		return kursevi;
	}

	public void setKursevi(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}

	
}
