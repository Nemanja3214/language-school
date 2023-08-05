package ssj.gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ssj.controllers.OsobaController;
import ssj.controllers.RacunController;
import ssj.gui.LoginFrm;
import ssj.gui.cenovnik.CenovnikCreate;
import ssj.gui.isplate.IsplateShow;
import ssj.gui.izvestaji.IzvestajiFrm;
import ssj.gui.jezik.JeziciShow;
import ssj.gui.kurs.KurseviShow;
import ssj.gui.racun.RacuniShow;
import ssj.gui.rezultat.RezultatiShow;
import ssj.gui.termin.TerminiShow;
import ssj.gui.test.TestoviShow;
import ssj.gui.zahtev.ZahteviFrm;
import ssj.managers.Cuvanje;
import ssj.model.osoba.Administrator;

public class AdministratorFrm extends JFrame{
	private Administrator a;
	private JMenuBar meniBar;
	private JMenu uceniciMenu;
	private JMenuItem ucenicicKreiraj;
	private JMenuItem ucenicicIzmeni;
	private JButton btnUcenici;
	private JPanel pnlCentar;
	private JButton btnSekretari;
	private JButton btnPredavaci;
	private JButton btnIsplata;
	private JButton btnJezik;
	private JButton btnKurs;
	private JButton btnPhadjanje;
	private JButton btnPohadjanje;
	private JButton btnRacun;
	private JButton btnRezultat;
	private JButton btnTermin;
	private JButton btnTest;
	private JButton btnZahtev;
	private JButton btnIzvestaji;
	private JButton btnKreirajCenovnik;

	public AdministratorFrm(Administrator a) {

		this.a = a;
		setSize(600,400);
		setTitle("Administratorska forma");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnlCentar = new JPanel();
		
		btnUcenici = new JButton("Učenici");
		btnUcenici.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OsobaController.prikazUcenika(AdministratorFrm.this);
			}
		});
		pnlCentar.add(btnUcenici);
		
		btnSekretari = new JButton("Sekretari");
		btnSekretari.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OsobaController.prikazSekretara();
			}
		});
		pnlCentar.add(btnSekretari);
		
		btnPredavaci = new JButton("Predavači");
		btnPredavaci.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OsobaController.prikazPredavaca();
			}
		});
		pnlCentar.add(btnPredavaci);
		
		btnIsplata = new JButton("Isplate");
		btnIsplata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IsplateShow();
			}
		});
		pnlCentar.add(btnIsplata);
		
		btnJezik = new JButton("Jezici");
		btnJezik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new JeziciShow();
			}
		});
		pnlCentar.add(btnJezik);
		
		btnKurs = new JButton("Kursevi");
		btnKurs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new KurseviShow(null, a);
			}
		});
		pnlCentar.add(btnKurs);
		
		btnRacun = new JButton("Računi");
		btnRacun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RacunController.prikaziSveRacune(a);
			}
		});
		pnlCentar.add(btnRacun);
		
		btnRezultat = new JButton("Rezultati");
		btnRezultat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RezultatiShow(null, a);
			}
		});
		pnlCentar.add(btnRezultat);
		
		btnTermin = new JButton("Termini");
		btnTermin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TerminiShow(null, a, false);
			}
		});
		pnlCentar.add(btnTermin);
		
		btnTest = new JButton("Testovi");
		btnTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TestoviShow(null, a);
			}
		});
		pnlCentar.add(btnTest);
		
		btnZahtev = new JButton("Zahtevi");
		btnZahtev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ZahteviFrm(null);
			}
		});
		pnlCentar.add(btnZahtev);
		
		
		btnIzvestaji = new JButton("Izveštaji");
		btnIzvestaji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IzvestajiFrm(AdministratorFrm.this);
			}
		});
		pnlCentar.add(btnIzvestaji);
		
		
		btnKreirajCenovnik = new JButton("Kreiraj cenovnik");
		btnKreirajCenovnik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CenovnikCreate(AdministratorFrm.this);
			}
		});
		pnlCentar.add(btnKreirajCenovnik);
		
		
		getContentPane().add(pnlCentar);
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
