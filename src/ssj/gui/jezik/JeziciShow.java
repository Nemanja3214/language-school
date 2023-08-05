package ssj.gui.jezik;

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

import ssj.gui.ucenik.UceniciShow;
import ssj.gui.ucenik.UcenikCreate;
import ssj.gui.ucenik.UcenikEdit;
import ssj.gui.ucenik.UcenikModel;
import ssj.managers.JezikManager;
import ssj.model.Jezik;
import ssj.model.osoba.Ucenik;

public class JeziciShow extends JFrame{
	private static final long serialVersionUID = 8326441088662123674L;
	private JTable tabela;
	private List<Jezik> jezici;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzadji;
	private JButton btnIzbrisi;

	public JeziciShow() {
		setTitle("Prikaz svih jezika");
		setSize(600,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		jezici = JezikManager.getInstance().getJezici();
		JezikModel jm = new JezikModel(jezici);
		RowFilter<JezikModel,Integer> neobrisanFilter = new RowFilter<JezikModel,Integer>() {
			 
			   public boolean include(Entry<? extends JezikModel, ? extends Integer> entry) {
				   JezikModel jm = entry.getModel();
			     Jezik j = jm.getJezici().get(entry.getIdentifier());
			     return !j.isObrisan();
			   }
			 };
		tabela = new JTable(jm);
		TableRowSorter<JezikModel> sorter = new TableRowSorter<JezikModel>(jm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		getContentPane().add(new JScrollPane(tabela));
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new JezikCreate(JeziciShow.this);
				jm.fireTableDataChanged();
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
					Jezik j = jezici.get(redKonvertovano);
					new JezikEdit(j, JeziciShow.this);
					jm.fireTableDataChanged();
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
					Jezik j = jezici.get(redKonvertovano);
					j.setObrisan(true);
					jm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JeziciShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
