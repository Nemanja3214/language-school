package ssj.gui.isplate;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ssj.managers.IsplataManager;
import ssj.managers.OsobaManager;
import ssj.model.Isplata;
import ssj.model.RacunSkole;
import ssj.model.osoba.Zaposlen;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class IsplataCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 1295484657221447342L;
	private JLabel lblIznos;
	private JPanel pnlCentar;
	private JComboBox<Zaposlen> cmbZaposlen;
	private JPanel pnlDugmad;
	private JButton btnKreiraj;
	private JButton btnIzadji;
	private JLabel lblIznosVrednost;
	private JLabel lblZaposlen;

	public IsplataCreate(IsplateShow roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje isplate");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblIznos = new JLabel("Iznos");
		pnlCentar.add(lblIznos);
		
		lblIznosVrednost = new JLabel();
		pnlCentar.add(lblIznosVrednost);
		
		
		lblZaposlen = new JLabel("Primalac");
		pnlCentar.add(lblZaposlen);
		
		cmbZaposlen = new JComboBox<Zaposlen>();
		for(Zaposlen z: OsobaManager.getInstance().getZaposleni()) {
			if(!z.isObrisan())
				cmbZaposlen.addItem(z);
		}
		cmbZaposlen.setRenderer(new ComboBoxRenderer());
		cmbZaposlen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Zaposlen z = (Zaposlen) cmbZaposlen.getSelectedItem();
				lblIznosVrednost.setText(Double.toString(z.izracunajPlatu()));
			}
		});
		pnlCentar.add(cmbZaposlen);
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnKreiraj = new JButton("Kreiraj");
		btnKreiraj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Isplata i = new Isplata();
					i.setId(KorisneFunkcije.generisiId(IsplataManager.getInstance().getIsplateMapa()));
					
					Zaposlen z = (Zaposlen) cmbZaposlen.getSelectedItem();
					i.setIznos(z.izracunajPlatu());
					RacunSkole.getInstance().setStanje(RacunSkole.getInstance().getStanje() - i.getIznos());
					
					i.setVremeNaplate(LocalDateTime.now());
					i.setZaposlen(z);
					
					IsplataManager.getInstance().getIsplate().add(i);
					IsplataManager.getInstance().getIsplateMapa().put(i.getId(), i);
					IsplataCreate.this.setVisible(false);
				}
			}
		});
		pnlDugmad.add(btnKreiraj);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IsplataCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		cmbZaposlen.setSelectedIndex(0);
		
		setVisible(true);
		
		
	}
	@Override
	public boolean proveraPogresno() {
		return cmbZaposlen.getSelectedIndex() == -1;
	}

}
