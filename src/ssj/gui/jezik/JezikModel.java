package ssj.gui.jezik;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Jezik;

public class JezikModel extends AbstractTableModel{
	private static final long serialVersionUID = 3499285043513000146L;
	private String[] kolone = {"ID", "Naziv"};
	private List<Jezik> jezici;
	
	public JezikModel(List<Jezik> jezici) {
		this.jezici = jezici;
	}

	@Override
	public int getRowCount() {
		return this.jezici.size();
	}

	@Override
	public int getColumnCount() {
		return this.kolone.length;
	}
	@Override 
	public String getColumnName(int index) {
		return this.kolone[index];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Jezik j = this.jezici.get(rowIndex);
		return j.toCell(columnIndex);
	}

	public List<Jezik> getJezici() {
		return jezici;
	}

	public void setJezici(List<Jezik> jezici) {
		this.jezici = jezici;
	}
	
	

}
