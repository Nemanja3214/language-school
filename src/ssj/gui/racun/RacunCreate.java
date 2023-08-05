package ssj.gui.racun;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.RacunController;
import ssj.managers.OsobaManager;
import ssj.managers.RacunManager;
import ssj.managers.TerminManager;
import ssj.model.Racun;
import ssj.model.Termin;
import ssj.model.osoba.Ucenik;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class RacunCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -1496590651869780024L;
	private JLabel lblVremeNaplate;
	private JPanel pnlCentar;
	private JFormattedTextField ftfVremeNaplate;
	private JLabel lblIznos;
	private JLabel lblIznosVrednost;
	private JComboBox<Termin> cmbTermin;
	private JLabel lblTermin;
	private JComboBox<Ucenik> cmbUcenik;
	private JLabel lblUcenik;
	private ComboBoxRenderer cbr;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;
	private JLabel lblTest;
	private JLabel lblTestVrednost;

	public RacunCreate(RacuniShow roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje racuna");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblIznos = new JLabel("Iznos: ");
		pnlCentar.add(lblIznos);
		
		lblIznosVrednost = new JLabel();
		pnlCentar.add(lblIznosVrednost);
		
		cbr = new ComboBoxRenderer();
		
		lblTermin = new JLabel("Termin: ");
		pnlCentar.add(lblTermin);
		
		cmbTermin = new JComboBox<Termin>();
		for(Termin t: TerminManager.getInstance().getTermini()) {
			if(!t.isObrisan())
				cmbTermin.addItem(t);
		}
		cmbTermin.setRenderer(cbr);
		cmbTermin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popuniUcenikCmb();
				
				if(cmbTermin.getSelectedIndex() != -1 && cmbUcenik.getSelectedIndex() != -1) {
					popuniUcenikCmb();
					lblIznosVrednost.setText(Double.toString(
							RacunController.izracunajIznos((Ucenik) cmbUcenik.getSelectedItem(), (Termin) cmbTermin.getSelectedItem())));
					
					Termin t = (Termin) cmbTermin.getSelectedItem();
					lblTestVrednost.setText(t.getTest().toString());
				}
			}
		});
		pnlCentar.add(cmbTermin);
		
		
		lblTest = new JLabel("Test: ");
		pnlCentar.add(lblTest);
		
		lblTestVrednost = new JLabel();
		pnlCentar.add(lblTestVrednost);
		
		
		lblUcenik = new JLabel("Učenik: ");
		pnlCentar.add(lblUcenik);
		
		cmbUcenik = new JComboBox<Ucenik>();
		popuniUcenikCmb();
		cmbUcenik.setRenderer(cbr);
		cmbUcenik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(cmbTermin.getSelectedIndex() != -1 && cmbUcenik.getSelectedIndex() != -1) {
					lblIznosVrednost.setText(Double.toString(
							RacunController.izracunajIznos((Ucenik) cmbUcenik.getSelectedItem(), (Termin) cmbTermin.getSelectedItem())));
				}
			}
		});
		pnlCentar.add(cmbUcenik);
		
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					

					Racun r = new Racun();
					
					r.setId(KorisneFunkcije.generisiId(RacunManager.getInstance().getRacuniMapa()));
//					iznos
					
					Ucenik u = (Ucenik) cmbUcenik.getSelectedItem();
					Termin t = (Termin)cmbTermin.getSelectedItem();
					r.setTermin(t);
					r.setUcenik(u);
					r.setVremeNaplate(LocalDateTime.now());
					r.setIznos(RacunController.izracunajIznos(u, t));
					
					System.out.println(r);
					
					
					RacunManager.getInstance().getRacuni().add(r);
					RacunManager.getInstance().getRacuniMapa().put(r.getId(), r);
					
					r.getTermin().getRacuni().add(r);
					r.getUcenik().getRacuni().add(r);
					
					RacunCreate.this.setVisible(false);
					
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka pokušajte ponovo");
			}
		});
		pnlDugmad.add(btnSnimi);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RacunCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	private void popuniUcenikCmb() {
		cmbUcenik.removeAllItems();
		for(Ucenik u: OsobaManager.getInstance().getUcenici()) {
			List<Racun> racuni = (((Termin)cmbTermin.getSelectedItem())).getRacuni();
			
			boolean nijePlatio = true;
			for(Racun r: racuni) {
				if(r.getUcenik().equals(u)) {
					nijePlatio = false;
				}
			}
			if(nijePlatio) {
				if(!u.isObrisan())
					cmbUcenik.addItem(u);
			}
		}
	}

	@Override
	public boolean proveraPogresno() {
		return cmbTermin.getSelectedIndex() == -1 || cmbUcenik.getSelectedIndex() == -1;
	}

}
