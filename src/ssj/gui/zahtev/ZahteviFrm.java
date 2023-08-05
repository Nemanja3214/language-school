package ssj.gui.zahtev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ssj.controllers.ZahtevController;
import ssj.model.osoba.Sekretar;

public class ZahteviFrm extends JFrame{
	private static final long serialVersionUID = 8622861149242561548L;
	JPanel pnlCentar;
	private JButton btnPZ;
	private JButton btnPO;
	private Sekretar s;
	private JButton btnDodaj;
	private JButton btnObradjeni;

	public ZahteviFrm(Sekretar s) {
		this.s = s;
		setSize(600,400);
		setTitle("Zahtevi");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ZahtevCreate(ZahteviFrm.this, s);
			}
		});
		pnlCentar.add(btnDodaj);
		
		btnPZ = new JButton("Obrada kreiranih");
		btnPZ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahtevController.prikazKreiranih(s);
			}
		});
		pnlCentar.add(btnPZ);
		
		
		btnPO = new JButton("Prikaz zahteva u obradi");
		btnPO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahtevController.prikazZahtevaUObradi(s);
			}
		});
		pnlCentar.add(btnPO);
		
		
		btnObradjeni = new JButton("Prikaz obradjenih zahteva");
		btnObradjeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahtevController.prikazObradjenih();
			}
		});
		pnlCentar.add(btnObradjeni);
		
		getContentPane().add(pnlCentar);
		
		if(s == null) {
			btnPO.setVisible(false);
			btnPZ.setVisible(false);
		}
		
		setVisible(true);
	}
}
