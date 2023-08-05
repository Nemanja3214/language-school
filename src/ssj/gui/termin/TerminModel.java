package ssj.gui.termin;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ssj.controllers.TestController;
import ssj.model.Termin;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Ucenik;

public class TerminModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private List<Termin> termini;
	private List<String> kolone;
	private Ucenik u;
	
	public TerminModel(List<Termin> termini) {
		this.termini = termini;
		
		String[] niz = {"ID", "Vreme održavanja", "Test"};
		this.kolone = napraviArrayListu(niz);
	}
	
	public TerminModel(List<Termin> termini, Ucenik u) {
		this.termini = new ArrayList<Termin>();
		for(Termin t: termini) {
			if(!t.isObrisan())
				this.termini.add(t);
		}
		this.termini = termini;
		
		this.u = u;
		String[] niz = {"ID", "Vreme održavanja", "Test", "Iznos"};
		this.kolone = napraviArrayListu(niz);
	}
	
	private List<String> napraviArrayListu(String[] niz) {
		List<String> lista = new ArrayList<String>();
		for(String s: niz) {
			lista.add(s);
		}
		return lista;
	}

	@Override
	public int getRowCount() {
		return this.termini.size();
	}

	@Override
	public int getColumnCount() {
		return this.kolone.size();
	}
	@Override
	public String getColumnName(int columnIndex) {
		return this.kolone.get(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Termin t =this.termini.get(rowIndex);
		if(columnIndex == 3){
			return TestController.izracunajIznos(t, u);
		}
		return t.toCell(columnIndex);
	}

	public List<Termin> getTermini() {
		return termini;
	}

	public void setTermini(List<Termin> termini) {
		this.termini = termini;
	}

	
	
	
}
