package ssj.gui.termin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.TerminController;
import ssj.controllers.TestController;
import ssj.managers.OsobaManager;
import ssj.managers.RacunManager;
import ssj.model.Racun;
import ssj.model.RacunSkole;
import ssj.model.Termin;
import ssj.model.osoba.Ucenik;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class PrijavaUcenikaNaTermin extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -604238198916518844L;
	private JComboBox<Ucenik> cmbUcenik;
	private JPanel pnlCentar;
	private JLabel lblUcenik;
	private JLabel lblTest;
	private JLabel lblTestVrednost;
	private JComboBox<Termin> cmbTermin;
	private JPanel pnlDugmad;
	private JButton btnPrijavi;
	private JLabel lblTermin;

	public PrijavaUcenikaNaTermin(JFrame roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Prijava učenika na termin");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		
		lblUcenik = new JLabel("Učenik: ");
		pnlCentar.add(lblUcenik);
		
		cmbUcenik = new JComboBox<Ucenik>();
		for(Ucenik u: OsobaManager.getInstance().getUcenici()) {
			if(!u.isObrisan())
				cmbUcenik.addItem(u);
		}
		cmbUcenik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cmbUcenik.getSelectedIndex() != -1) {
					Ucenik u = (Ucenik) cmbUcenik.getSelectedItem();
					List<Termin> termini = TerminController.dostupniTermini(u);
					cmbTermin.removeAllItems();
					for(Termin t: termini) {
						cmbTermin.addItem(t);
					}
				}
			}
		});
		pnlCentar.add(cmbUcenik);
		
		
		lblTest = new JLabel("Test: ");
		pnlCentar.add(lblTest);
		
		lblTestVrednost = new JLabel();
		pnlCentar.add(lblTestVrednost);
		
		
		lblTermin = new JLabel("Termin: ");
		pnlCentar.add(lblTermin);
		
		
		cmbTermin = new JComboBox<Termin>();
		cmbTermin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cmbTermin.getSelectedIndex() != -1) {
					Termin t = (Termin) cmbTermin.getSelectedItem();
					lblTestVrednost.setText(t.getTest().getNaziv());
				}
				else
					lblTestVrednost.setText("");
			}
		});
		pnlCentar.add(cmbTermin);
		
		pnlDugmad = new JPanel();
		
		
		btnPrijavi = new JButton("Prijavi");
		btnPrijavi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Termin t = (Termin) cmbTermin.getSelectedItem();
					
					Ucenik u = (Ucenik) cmbUcenik.getSelectedItem();
					
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
						
						cmbUcenik.setSelectedIndex(0);
					}
					else {
						JOptionPane.showMessageDialog(null, "Nemate dovoljno novca na računu.");
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka, pokušajte ponovo");
			}
		});
		pnlDugmad.add(btnPrijavi);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		getContentPane().add(pnlCentar);
		
		setVisible(true);
		
	}

	@Override
	public boolean proveraPogresno() {
		return cmbTermin.getSelectedIndex() == -1 || cmbUcenik.getSelectedIndex() == -1;
	}
}
