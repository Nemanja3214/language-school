package ssj.gui.sekretar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ssj.gui.LoginFrm;
import ssj.gui.doplata.Doplata;
import ssj.gui.termin.PrijavaUcenikaNaTermin;
import ssj.gui.ucenik.UceniciShow;
import ssj.gui.zahtev.ZahteviFrm;
import ssj.managers.Cuvanje;
import ssj.model.osoba.Sekretar;

public class SekretarFrm extends JFrame{

	private static final long serialVersionUID = -7868117145687829490L;
	private Sekretar s;
	private JButton btnZahtevi;
	private JPanel pnlCentar;
	private JButton btnUcenici;
	private JButton btnDoplata;
	private JButton btnPrijavaUcenikaNaTermin;

	public SekretarFrm(Sekretar s) {

		this.s = s;
		setSize(600,400);
		setTitle("Sekretarska forma");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnlCentar = new JPanel();
		
		
		btnUcenici = new JButton("Učenici");
		btnUcenici.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UceniciShow();
			}
		});
		pnlCentar.add(btnUcenici);
		
		btnZahtevi = new JButton("Zahtevi");
		btnZahtevi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ZahteviFrm(s);
			}
		});
		pnlCentar.add(btnZahtevi);

		btnDoplata = new JButton("Doplata novca učeniku");
		btnDoplata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Doplata(SekretarFrm.this);
			}
		});
		pnlCentar.add(btnDoplata);
		
		btnPrijavaUcenikaNaTermin = new JButton("Prijava učenika na termin");
		btnPrijavaUcenikaNaTermin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PrijavaUcenikaNaTermin(SekretarFrm.this);
			}
		});
		pnlCentar.add(btnPrijavaUcenikaNaTermin);
		
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

