package ssj.utils;

import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;

public class FilterNeobrisanih extends RowFilter<AbstractTableModel, Izbrisiv>{

	@Override
	public boolean include(Entry<? extends AbstractTableModel, ? extends Izbrisiv> entry) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
