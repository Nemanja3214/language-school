package ssj.gui.termin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import ssj.controllers.OsobaController;
import ssj.controllers.RezultatController;
import ssj.controllers.TestController;
import ssj.gui.kurs.KursModel;
import ssj.managers.RacunManager;
import ssj.managers.TerminManager;
import ssj.model.Kurs;
import ssj.model.Racun;
import ssj.model.RacunSkole;
import ssj.model.Termin;
import ssj.model.osoba.Administrator;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Ucenik;
import ssj.utils.KorisneFunkcije;

public class TerminiShow extends JFrame{
	private static final long serialVersionUID = 3235624153748961532L;
	private List<Termin> termini;
	private JTable tabela;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private JButton btnRezultati;
	private JButton btnPrijava;
	private Osoba o;

	public TerminiShow(List<Termin> termini2, Osoba o, boolean prijavaNaTermin) {
		this.o = o;
		
		if(termini2 == null)
			termini = TerminManager.getInstance().getTermini();
		else
			termini = termini2;
		
		setSize(600,400);
		setTitle("Prikaz termina");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		TerminModel tm = prijavaNaTermin ? new TerminModel(termini, (Ucenik)o) : new TerminModel(termini);
		RowFilter<TerminModel,Integer> neobrisanFilter = new RowFilter<TerminModel,Integer>() {
			 
			   public boolean include(Entry<? extends TerminModel, ? extends Integer> entry) {
				   TerminModel tm = entry.getModel();
			     Termin t = tm.getTermini().get(entry.getIdentifier());
			     return !t.isObrisan();
			   }
			 };
		tabela = new JTable(tm);
		TableRowSorter<TerminModel> sorter = new TableRowSorter<TerminModel>(tm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		getContentPane().add(new JScrollPane(tabela));
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(o instanceof Administrator)
					new TerminCreate(TerminiShow.this, null);
				else if(o instanceof Predavac)
					new TerminCreate(TerminiShow.this, (Predavac) o);
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
					btnRezultati.setEnabled(true);
				}
				else
				{
					btnIzmeni.setEnabled(false);
					btnIzbrisi.setEnabled(false);
					btnRezultati.setEnabled(false);
				}
			}
		});
		btnIzmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Termin t = termini.get(redKonvertovano);
					new TerminEdit(t, TerminiShow.this);
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
					
					Termin t = termini.get(redKonvertovano);
					t.setObrisan(true);
					
					t.getTest().getTermini().remove(t);
					
					tm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		
		btnRezultati = new JButton("Rezultati");
		btnRezultati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					
					Termin t = termini.get(redKonvertovano);
					
					RezultatController.prikazRezultataTermina(t, o);
				}
			}
		});
		btnRezultati.setEnabled(false);
		pnlDugmad.add(btnRezultati);
		
		
		btnPrijava = new JButton("Prijava");
		btnPrijava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					
					Termin t = termini.get(redKonvertovano);
					
					Ucenik u = (Ucenik) o;
					
					double iznos = TestController.izracunajIznos(t, u);
					
					if(u.getStanjeRacuna() > iznos) {
						
						Racun r = new Racun();
						
						r.setId(KorisneFunkcije.generisiId(RacunManager.getInstance().getRacuniMapa()));
						r.setIznos(iznos);
						r.setTermin(t);
						r.setUcenik(u);
						r.setVremeNaplate(LocalDateTime.now());
						
						RacunManager.getInstance().getRacuni().add(r);
						RacunManager.getInstance().getRacuniMapa().put(r.getId(), r);
						
						r.getTermin().getRacuni().add(r);
						r.getUcenik().getRacuni().add(r);
						
						u.setStanjeRacuna(u.getStanjeRacuna() - iznos);
						RacunSkole.getInstance().setStanje(RacunSkole.getInstance().getStanje() + iznos);
						termini.remove(t);
						tm.fireTableDataChanged();
					}
					else {
						JOptionPane.showMessageDialog(null, "Nemate dovoljno novca na računu.");
					}
				}
				
			}
		});
		pnlDugmad.add(btnPrijava);
		btnPrijava.setVisible(false);
		
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TerminiShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		if(o instanceof Ucenik) {
			btnDodaj.setVisible(false);
			btnIzbrisi.setVisible(false);
			btnIzmeni.setVisible(false);
			if(prijavaNaTermin) {
				btnPrijava.setVisible(true);
				btnRezultati.setVisible(false);
			}

		}
		
		setVisible(true);
		}}
