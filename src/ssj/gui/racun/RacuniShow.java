package ssj.gui.racun;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ssj.managers.RacunManager;
import ssj.model.Racun;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Ucenik;

public class RacuniShow extends JFrame{
	private static final long serialVersionUID = -56571515971671741L;
	private JTable tabela;
	private List<Racun> racuni;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzmeni;
	private JButton btnIzbrisi;
	private JButton btnIzadji;
	private JLabel lblTrenutnoStanje;
	private JLabel lblTrenutnoStanjeVrednost ;

	public RacuniShow(List<Racun> racuni, Osoba o) {
		this.racuni = racuni;
		
		setSize(600,400);
		setTitle("Prikaz računa");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		RacunModel rm = new RacunModel(racuni);
		tabela = new JTable(rm);
		tabela.setAutoCreateRowSorter(true);
		
		getContentPane().add(new JScrollPane(tabela));
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RacunCreate(RacuniShow.this);
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
					Racun r = RacuniShow.this.racuni.get(redKonvertovano);
					new RacunEdit(r, RacuniShow.this);
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
					
					Racun r = RacuniShow.this.racuni.get(redKonvertovano);
					RacuniShow.this.racuni.remove(r);
					
					RacunManager.getInstance().getRacuni().remove(r);
					RacunManager.getInstance().getRacuniMapa().remove(r.getId());
					
					r.getUcenik().getRacuni().remove(r);
					r.getTermin().getRacuni().remove(r);
					
					rm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnIzbrisi);
		btnIzbrisi.setEnabled(false);
		btnIzbrisi.setVisible(false);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RacuniShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		if(o instanceof Ucenik) {
			btnDodaj.setVisible(false);
			btnIzbrisi.setVisible(false);
			btnIzmeni.setVisible(false);
			btnIzadji.setVisible(false);
			lblTrenutnoStanje = new JLabel("Trenutno stanje: ");
			pnlDugmad.add(lblTrenutnoStanje);
			
			Ucenik u = (Ucenik)o;
			lblTrenutnoStanjeVrednost = new JLabel(Double.toString(u.getStanjeRacuna()));
			pnlDugmad.add(lblTrenutnoStanjeVrednost);
		}
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		
		
		
		setVisible(true);
	}
}
