package ssj.gui.kurs;

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

import ssj.controllers.TestController;
import ssj.gui.jezik.JezikModel;
import ssj.managers.KursManager;
import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Ucenik;

public class KurseviShow extends JFrame{
	private static final long serialVersionUID = -8490235065351338875L;
	private JPanel pnlCentar;
	private List<Kurs> kursevi;
	private JTable tabela;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private JButton btnTestovi;

	public KurseviShow(List<Kurs> kursevi2, Osoba o) {
		
		if(kursevi2 == null) {
			kursevi = KursManager.getInstance().getKursevi();
		}
		else {
			kursevi = kursevi2;
		}
		
		setSize(600,400);
		setTitle("Prikaz kurseva");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel();

		KursModel km = new KursModel(kursevi);
		RowFilter<KursModel,Integer> neobrisanFilter = new RowFilter<KursModel,Integer>() {
			 
			   public boolean include(Entry<? extends KursModel, ? extends Integer> entry) {
				   KursModel km = entry.getModel();
			     Kurs k = km.getKursevi().get(entry.getIdentifier());
			     return !k.isObrisan();
			   }
			 };
		tabela = new JTable(km);
		TableRowSorter<KursModel> sorter = new TableRowSorter<KursModel>(km);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		getContentPane().add(new JScrollPane(tabela));
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new KursCreate(KurseviShow.this);
				km.fireTableDataChanged();
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
					btnTestovi.setEnabled(true);
				}
				else
				{
					btnIzmeni.setEnabled(false);
					btnIzbrisi.setEnabled(false);
					btnTestovi.setEnabled(false);
				}
			}
		});
		btnIzmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Kurs k = kursevi.get(redKonvertovano);
					new KursEdit(k, KurseviShow.this);
					km.fireTableDataChanged();
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
					
					Kurs k = kursevi.get(redKonvertovano);
					k.setObrisan(true);
					
					k.getPredavac().getKursevi().remove(k);
					k.getJezik().getKursevi().remove(k);
					km.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		
		btnTestovi = new JButton("Testovi");
		btnTestovi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					
					Kurs k = kursevi.get(redKonvertovano);
					TestController.prikazTestovaKursa(k, o);
				}
			}
		});
		btnTestovi.setEnabled(false);
		pnlDugmad.add(btnTestovi);
		
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KurseviShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		if(o instanceof Ucenik || o instanceof Predavac) {
			btnDodaj.setVisible(false);
			btnIzbrisi.setVisible(false);
			btnIzmeni.setVisible(false);

		}
		
		setVisible(true);
	}
}
