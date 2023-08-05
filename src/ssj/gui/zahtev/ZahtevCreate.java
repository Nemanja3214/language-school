package ssj.gui.zahtev;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.JezikController;
import ssj.managers.KursManager;
import ssj.managers.OsobaManager;
import ssj.managers.ZahtevManager;
import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Ucenik;
import ssj.model.zahtev.Kreiran;
import ssj.model.zahtev.Zahtev;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class ZahtevCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 9094686214720353870L;
	private JPanel pnlCentar;
	private JComboBox<Ucenik> cmbUcenik;
	private JComboBox<Kurs> cmbKurs;
	private JLabel lblStanje;
	private JLabel lblUcenik;
	private JLabel lblKurs;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;
	private ComboBoxRenderer cbr;
	private JLabel lblPreporuke;
	private JPanel pnlDesno;
	
//	# id; stanje; sekretar; ucenik; kurs
	public ZahtevCreate(JFrame roditelj, Osoba o) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje novog zahteva");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cbr = new ComboBoxRenderer();
		
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		
		lblUcenik = new JLabel("Učenik: ");
		pnlCentar.add(lblUcenik);
		
		cmbUcenik = new JComboBox<Ucenik>();
		for(Ucenik u: OsobaManager.getInstance().getUcenici()) {
			cmbUcenik.addItem(u);
		}
		cmbUcenik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbKurs.removeAllItems();
				Ucenik u = (Ucenik) cmbUcenik.getSelectedItem();
				for(Kurs k: KursManager.getInstance().getKursevi()) {
					if(!u.getKursevi().contains(k) && !k.isObrisan()) {
						cmbKurs.addItem(k);
					}
				}
			}
		});
		cmbUcenik.setRenderer(cbr);
		pnlCentar.add(cmbUcenik);
		
		
		lblKurs = new JLabel("Kurs: ");
		pnlCentar.add(lblKurs);
		
		cmbKurs = new JComboBox<Kurs>();
		cmbKurs.setRenderer(cbr);
		pnlCentar.add(cmbKurs);
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {

					Zahtev z = new Zahtev();
					
					z.setId(KorisneFunkcije.generisiId(ZahtevManager.getInstance().getZahteviMapa()));
					z.setKurs((Kurs) cmbKurs.getSelectedItem());
					z.setSekretar(null);
					z.setStanje(Kreiran.getInstance());
					z.setUcenik((Ucenik) cmbUcenik.getSelectedItem());
					z.setVremeKreiranja(LocalDateTime.now());
					System.out.println(z);
					
					
					ZahtevManager.getInstance().getZahtevi().add(z);
					ZahtevManager.getInstance().getZahteviMapa().put(z.getId(), z);
					
					ZahtevCreate.this.setVisible(false);
					
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
				ZahtevCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		if(o instanceof Ucenik) {
			Ucenik u = (Ucenik)o;
			cmbUcenik.addItem(u);
			cmbUcenik.setSelectedItem(u);
			
			lblUcenik.setVisible(false);
			cmbUcenik.setVisible(false);
			
			pnlDesno = new JPanel(new MigLayout("wrap 1", "20[]20", "[]20[]"));
			
			
			List<Jezik> najpopularnijiJezici = JezikController.najpopularnijiJezici(4);
			List<Kurs> kurseviNajpopularnijih = new ArrayList<Kurs>();
			for(Jezik j: najpopularnijiJezici) {
				kurseviNajpopularnijih.addAll(j.getKursevi());
			}
			List<Kurs> preporuke = new ArrayList<Kurs>();
			for(Kurs k: KursManager.getInstance().getKursevi()) {
				if(!u.getKursevi().contains(k) && !k.isObrisan()) {
					preporuke.add(k);
				}
			}
			if(!(preporuke.size() == 0)) {
				lblPreporuke = new JLabel("Preporuke za kurseve: ");
				pnlDesno.add(lblPreporuke);
				
				for(Kurs k: preporuke) {
					pnlDesno.add(new JLabel(k.getNaziv()));
				}
			}
			
			
			getContentPane().add(pnlDesno, BorderLayout.EAST);

		}
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return cmbKurs.getSelectedIndex() == -1 || cmbUcenik.getSelectedIndex() == -1;
	}
	
}
