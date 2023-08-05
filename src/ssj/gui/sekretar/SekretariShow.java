package ssj.gui.sekretar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
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
import ssj.gui.ucenik.UceniciShow;
import ssj.gui.ucenik.UcenikCreate;
import ssj.gui.ucenik.UcenikEdit;
import ssj.managers.OsobaManager;
import ssj.model.Kurs;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;

public class SekretariShow extends JFrame{
	private static final long serialVersionUID = 3664242141219230855L;
	private JTable tabela;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private List<Sekretar> sekretari;
	private JButton btnIzadji;

	public SekretariShow() {
		setSize(600,400);
		setTitle("Prikaz svih sekretara");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		sekretari = OsobaManager.getInstance().getSekretari();
		SekretarModel sm = new SekretarModel(sekretari);
		RowFilter<SekretarModel,Integer> neobrisanFilter = new RowFilter<SekretarModel,Integer>() {
			 
			   public boolean include(Entry<? extends SekretarModel, ? extends Integer> entry) {
				   SekretarModel sm = entry.getModel();
			     Sekretar s = sm.getSekretari().get(entry.getIdentifier());
			     return !s.isObrisan();
			   }
			 };
		tabela = new JTable(sm);
		TableRowSorter<SekretarModel> sorter = new TableRowSorter<SekretarModel>(sm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		getContentPane().add(new JScrollPane(tabela), BorderLayout.CENTER);
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SekretarCreate(SekretariShow.this);
				sm.fireTableDataChanged();
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
					Sekretar s = sekretari.get(redKonvertovano);
					new SekretarEdit(s, SekretariShow.this);
					sm.fireTableDataChanged();
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
					
					Sekretar s = sekretari.get(redKonvertovano);
					s.setObrisan(true);
					sm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SekretariShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		setVisible(true);
	}
}
