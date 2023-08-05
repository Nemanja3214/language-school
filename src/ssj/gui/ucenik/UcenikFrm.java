package ssj.gui.ucenik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.KursController;
import ssj.controllers.RacunController;
import ssj.controllers.TerminController;
import ssj.gui.LoginFrm;
import ssj.gui.zahtev.ZahtevCreate;
import ssj.managers.Cuvanje;
import ssj.model.osoba.Ucenik;
import ssj.notifikacije.Notifikacija;

public class UcenikFrm extends JFrame{
	private Ucenik u;
	private JButton btnKursevi;
	private JPanel pnlCentar;
	private JButton btnPrijavaNaTermin;
	private JButton btnPodnosenjeZahteva;
	private JPanel pnlObavestenja;
	private JScrollPane pnlOkolni;
	private JButton btnFinansijskaKartica;
	private JButton btnPreporuke;
	public UcenikFrm(Ucenik u) {

		this.u = u;
		setSize(600,400);
		setTitle("Učenicka forma");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		#id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa
		
		pnlCentar = new JPanel();
		
		
		btnKursevi  = new JButton("Kursevi");
		btnKursevi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KursController.prikazUcenikKursevi(u);
			}
		});
		pnlCentar.add(btnKursevi);
		
		btnPrijavaNaTermin = new JButton("Prijava na termin");
		btnPrijavaNaTermin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TerminController.prikazDostupnihTermina(u);
			}
		});
		pnlCentar.add(btnPrijavaNaTermin);
		
		btnPodnosenjeZahteva = new JButton("Podnesi zahtev");
		btnPodnosenjeZahteva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ZahtevCreate(UcenikFrm.this, u);
			}
		});
		pnlCentar.add(btnPodnosenjeZahteva);
		
		
		btnFinansijskaKartica = new JButton("Finansijska kartica");
		btnFinansijskaKartica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RacunController.prikaziFinansijskuKarticuUcenika(u);
			}
		});
		pnlCentar.add(btnFinansijskaKartica);
		
		
		getContentPane().add(pnlCentar, BorderLayout.NORTH);
		
		pnlObavestenja = new JPanel(new MigLayout("wrap 3"));
		
		pnlObavestenja.add(new JLabel("Obaveštenja: "), "span");
		
		List<Notifikacija> notifikacije = u.getNotifikacije();
		ListIterator<Notifikacija> iterator = notifikacije.listIterator(notifikacije.size());
		
		while(iterator.hasPrevious()) {
			Notifikacija n = iterator.previous();
			pnlObavestenja.add(new JLabel(n.getNaslov() + ":"));
			pnlObavestenja.add(new JLabel(n.getTekst()));
			pnlObavestenja.add(new JLabel(" - " + n.getKurs().getNaziv()));
		}
		
		pnlOkolni = new JScrollPane(pnlObavestenja);
		pnlOkolni.setSize(WIDTH, 100);
		getContentPane().add(pnlOkolni, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	@Override
	public void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING) {
			Cuvanje.sacuvaj();
			dispose();
			new LoginFrm();
		}
	}
}
