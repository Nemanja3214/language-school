package ssj.gui.isplate;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Isplata;

public class IsplataModel extends AbstractTableModel{
	private static final long serialVersionUID = 4945099089705575034L;
	private List<Isplata> isplate;
	private String[] kolone = {"ID", "Vreme naplate", "Iznos", "Primalac", "Platilac"};

	public IsplataModel(List<Isplata> isplate) {
		this.isplate = isplate;
	}
	@Override
	public int getRowCount() {
		return this.isplate.size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}
	@Override
	public String getColumnName(int columnIndex) {
		return kolone[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Isplata i = this.isplate.get(rowIndex);
		return i.toCell(columnIndex);
	}

}
