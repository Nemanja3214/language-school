package ssj.gui.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import ssj.controllers.TerminController;
import ssj.gui.kurs.KursModel;
import ssj.managers.TestManager;
import ssj.model.Kurs;
import ssj.model.Test;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Ucenik;

public class TestoviShow extends JFrame{
	private static final long serialVersionUID = -2986113588461163112L;
	private JPanel pnlCentar;
	private JTable tabela;
	private List<Test> testovi;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private JButton btnTermini;

	public TestoviShow(List<Test> testovi2, Osoba o) {
		
		if(testovi2 == null) {
			testovi = TestManager.getInstance().getTestovi();
		}
		else {
			testovi = testovi2;
		}
		
		setSize(600,400);
		setTitle("Prikaz svih testova");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel();

		TestModel tm = new TestModel(testovi);
		RowFilter<TestModel,Integer> neobrisanFilter = new RowFilter<TestModel,Integer>() {
			 
			   public boolean include(Entry<? extends TestModel, ? extends Integer> entry) {
				   TestModel km = entry.getModel();
			     Test t = tm.getTestovi().get(entry.getIdentifier());
			     return !t.isObrisan();
			   }
			 };
		tabela = new JTable(tm);
		TableRowSorter<TestModel> sorter = new TableRowSorter<TestModel>(tm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);

		
		getContentPane().add(new JScrollPane(tabela));
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TestCreate(TestoviShow.this);
				tm.fireTableDataChanged();
			}
		});
		pnlDugmad.add(btnDodaj);
		
		btnIzmeni = new JButton("Izmeni");
		btnIzmeni.setEnabled(false);
		
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tabela.getSelectedRow() != -1)
				{
					btnIzmeni.setEnabled(true);
					btnIzbrisi.setEnabled(true);
					btnTermini.setEnabled(true);
				}
				else
				{
					btnIzmeni.setEnabled(false);
					btnIzbrisi.setEnabled(false);
					btnTermini.setEnabled(false);
				}
			}
		});
		btnIzmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Test t = testovi.get(redKonvertovano);
					new TestEdit(t, TestoviShow.this);
					tm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzmeni);
		
		btnIzbrisi = new JButton("Izbriši");
		btnIzbrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					
					Test t = testovi.get(redKonvertovano);
					t.setObrisan(true);
					t.getKurs().getTestovi().remove(t);
					
					tm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		
		btnTermini = new JButton("Termini");
		btnTermini.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					
					Test t = testovi.get(redKonvertovano);
					TerminController.prikazTerminaTesta(t, o);
				}
			}
		});
		btnTermini.setEnabled(false);
		pnlDugmad.add(btnTermini);
		
		
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TestoviShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		if(o instanceof Ucenik) {
			btnDodaj.setVisible(false);
			btnIzbrisi.setVisible(false);
			btnIzmeni.setVisible(false);

		}
		
		setVisible(true);
	}
}
