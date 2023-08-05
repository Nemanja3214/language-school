package transakcije;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Transakcija;

public class TransakcijeModel extends AbstractTableModel{
	private static final long serialVersionUID = -3596646604945608692L;
	private List<Transakcija> transakcije;
	private String[] kolone = {"ID", "Vreme naplate", "Iznos", "Primalac", "Platilac"};
	
	public TransakcijeModel(List<Transakcija> transakcije) {
		this.transakcije = transakcije;
	}
	
	@Override
	public int getRowCount() {
		return transakcije.size();
	}
	
	@Override
	public String getColumnName(int colIndex) {
		return kolone[colIndex];
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Transakcija t = transakcije.get(rowIndex);
		return t.toCell(columnIndex);
	}

}
