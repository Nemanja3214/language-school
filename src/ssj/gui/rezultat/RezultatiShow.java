package ssj.gui.rezultat;

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

import ssj.gui.kurs.KursModel;
import ssj.managers.RezultatManager;
import ssj.model.Kurs;
import ssj.model.Rezultat;
import ssj.model.osoba.Administrator;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Ucenik;

public class RezultatiShow extends JFrame{
	private static final long serialVersionUID = -4442400971758584731L;
	private JTable tabela;
	private RezultatModel rm;
	private List<Rezultat> rezultati;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private JButton btnGrafickiPrikaz;
	private JButton btnIzvestaj;
	
	public RezultatiShow(List<Rezultat> rezultati2, Osoba o) {
		
		if(rezultati2 == null)
			rezultati = RezultatManager.getInstance().getRezultati();
		else
			rezultati = rezultati2;
		
		setSize(600,400);
		setTitle("Prikaz rezultata");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		rm = new RezultatModel(rezultati);
		RowFilter<RezultatModel,Integer> neobrisanFilter = new RowFilter<RezultatModel,Integer>() {
			 
			   public boolean include(Entry<? extends RezultatModel, ? extends Integer> entry) {
				   RezultatModel rm = entry.getModel();
			     Rezultat r = rm.getRezultati().get(entry.getIdentifier());
			     return !r.isObrisan();
			   }
			 };
		tabela = new JTable(rm);
		TableRowSorter<RezultatModel> sorter = new TableRowSorter<RezultatModel>(rm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		getContentPane().add(new JScrollPane(tabela));
		
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RezultatCreate(RezultatiShow.this);
				rm.fireTableDataChanged();
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
				}
				else
				{
					btnIzmeni.setEnabled(false);
					btnIzbrisi.setEnabled(false);
				}
			}
		});
		btnIzmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Rezultat r = rezultati.get(redKonvertovano);
					new RezultatEdit(r, RezultatiShow.this, rezultati);
					rm.fireTableDataChanged();
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
					
					Rezultat r = rezultati.get(redKonvertovano);
					
					r.setObrisan(true);
					
					r.getTermin().getRezultati().remove(r);
					r.getUcenik().getRezultati().remove(r);
					
					rm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		
		btnIzvestaj = new JButton("Izveštaj");
		btnIzvestaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RezultatIzvestaj(RezultatiShow.this, rezultati);
			}
		});
		pnlDugmad.add(btnIzvestaj);
		
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RezultatiShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		if(o instanceof Ucenik) {
			btnDodaj.setVisible(false);
			btnIzbrisi.setVisible(false);
			btnIzmeni.setVisible(false);
			btnIzvestaj.setVisible(false);

		}
		if(o instanceof Administrator)
		{
			btnIzvestaj.setVisible(false);
		}
		
		setVisible(true);
	}

}
