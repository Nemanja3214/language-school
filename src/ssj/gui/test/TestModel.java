package ssj.gui.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.model.Termin;
import ssj.model.Test;

public class TestModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private List<Test> testovi;
	private String[] kolone = {"ID", "Naziv", "Kurs"};
	
	public TestModel(List<Test> testovi) {
		this.testovi = testovi;
	}	

	@Override
	public int getRowCount() {
		return this.testovi.size();
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
		Test t = this.testovi.get(rowIndex);
		return t.toCell(columnIndex);
	}

	public List<Test> getTestovi() {
		return testovi;
	}

	public void setTestovi(List<Test> testovi) {
		this.testovi = testovi;
	}

}
