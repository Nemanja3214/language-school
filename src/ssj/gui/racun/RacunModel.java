package ssj.gui.racun;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Racun;
import ssj.model.Termin;

public class RacunModel extends AbstractTableModel{
	private static final long serialVersionUID = 3813414416978470678L;
	private List<Racun> racuni;
	private String[] kolone = {"ID", "Vreme naplate", "Iznos", "Primalac", "Platilac"};
	
	public RacunModel(List<Racun> racuni) {
		this.racuni = new ArrayList<Racun>();
		for(Racun r: racuni) {
			if(!r.isObrisan())
				this.racuni.add(r);
		}
	}
	@Override
	public int getRowCount() {
		return this.racuni.size();
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
		Racun r = this.racuni.get(rowIndex);
		return r.toCell(columnIndex);
	}

}
