package ssj.gui.ucenik;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import ssj.managers.OsobaManager;
import ssj.model.osoba.Ucenik;

public class UceniciShow extends JFrame{
	private static final long serialVersionUID = -7011804598165245006L;
	private JTable tabela;
	private JScrollPane pnlCentar;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private List<Ucenik> ucenici;
	
	public UceniciShow() {
		setSize(600,400);
		setTitle("Prikaz svih učenika");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		ucenici = OsobaManager.getInstance().getUcenici();
		
		UcenikModel um = new UcenikModel(ucenici);
		 RowFilter<UcenikModel,Integer> neobrisanFilter = new RowFilter<UcenikModel,Integer>() {
			 
			   public boolean include(Entry<? extends UcenikModel, ? extends Integer> entry) {
				 UcenikModel um = entry.getModel();
			     Ucenik u = um.getUcenici().get(entry.getIdentifier());
			     return !u.isObrisan();
			   }
			 };
		tabela = new JTable(um);
		TableRowSorter<UcenikModel> sorter = new TableRowSorter<UcenikModel>(um);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		pnlCentar = new JScrollPane(tabela);
		getContentPane().add(pnlCentar, BorderLayout.CENTER);
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UcenikCreate(UceniciShow.this);
				um.fireTableDataChanged();
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
					int id = (Integer)tabela.getModel().getValueAt(redKonvertovano, 0);
					Ucenik u = OsobaManager.getInstance().getUceniciMapa().get(id);
					new UcenikEdit(u, UceniciShow.this);
					um.fireTableDataChanged();
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
					int id = (Integer)tabela.getModel().getValueAt(redKonvertovano, 0);
					
					Ucenik u = OsobaManager.getInstance().getUceniciMapa().get(id);
					u.setObrisan(true);

					um.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UceniciShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
