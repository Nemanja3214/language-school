package ssj.gui.predavac;

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
import ssj.managers.OsobaManager;
import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.model.osoba.Predavac;

public class PredavaciShow extends JFrame{
	private static final long serialVersionUID = 3664242141219230855L;
	private JTable tabela;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private List<Predavac> predavaci;

	public PredavaciShow() {
		setSize(600,400);
		setTitle("Prikaz svih predavača");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		predavaci = OsobaManager.getInstance().getPredavaci();
		PredavacModel pm = new PredavacModel(predavaci);
		RowFilter<PredavacModel,Integer> neobrisanFilter = new RowFilter<PredavacModel,Integer>() {
			 
			   public boolean include(Entry<? extends PredavacModel, ? extends Integer> entry) {
				   PredavacModel pm = entry.getModel();
			     Predavac p = pm.getPredavaci().get(entry.getIdentifier());
			     return !p.isObrisan();
			   }
			 };
		tabela = new JTable(pm);
		TableRowSorter<PredavacModel> sorter = new TableRowSorter<PredavacModel>(pm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		getContentPane().add(new JScrollPane(tabela), BorderLayout.CENTER);
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PredavacCreate(PredavaciShow.this);
				pm.fireTableDataChanged();
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
					Predavac p = predavaci.get(redKonvertovano);
					new PredavacEdit(p, PredavaciShow.this);
					pm.fireTableDataChanged();
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
					
					Predavac p = predavaci.get(redKonvertovano);
					
					p.setObrisan(true);
					
					for(Jezik j: p.getJezici()) {
						j.getPredavaci().remove(p);
					}
					
					pm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PredavaciShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		setVisible(true);
	}
}
