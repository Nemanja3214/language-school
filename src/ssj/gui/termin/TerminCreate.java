package ssj.gui.termin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;
import ssj.managers.TerminManager;
import ssj.managers.TestManager;
import ssj.model.Kurs;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Predavac;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class TerminCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 1520849972947170878L;
	private JPanel pnlCentar;
	private JLabel lblVremeOdrzavanja;
	private SimpleDateFormat df;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;
	private JComboBox<Test> cmbTestovi;
	private JFormattedTextField ftfVremeOdrzavanja;
	private JLabel lblTestovi;
	private List<Test> testovi;

	public TerminCreate(TerminiShow roditelj, Predavac p) {
		super(roditelj, true);
		if(p == null) {
			testovi = TestManager.getInstance().getTestovi();
		}
		else{
			testovi = new ArrayList<Test>();
			for(Kurs k : p.getKursevi()) {
				testovi.addAll(k.getTestovi());
			}
		}
		
		setSize(600,400);
		setTitle("Kreiranje novog termina");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblVremeOdrzavanja = new JLabel("Vreme održavanja(HH:mm dd.MM.yyyy.): ");
		pnlCentar.add(lblVremeOdrzavanja);

		ftfVremeOdrzavanja = new JFormattedTextField();
		ftfVremeOdrzavanja.setToolTipText("HH:mm dd.MM.yyyy.");
		ftfVremeOdrzavanja.setColumns(20);
		pnlCentar.add(ftfVremeOdrzavanja, "wrap");
		
		try {
			MaskFormatter mf = new MaskFormatter("##:## ##.##.####.");
			mf.setPlaceholderCharacter('_');
			mf.install(ftfVremeOdrzavanja);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom parsiranja vremena održavanja");
		}
		
		
		lblTestovi = new JLabel("Test: ");
		pnlCentar.add(lblTestovi);
		
		cmbTestovi = new JComboBox<Test>();
		
		for(Test t: testovi) {
			if(!t.isObrisan())
				cmbTestovi.addItem(t);
		}
		cmbTestovi.setRenderer(new ComboBoxRenderer());
		pnlCentar.add(cmbTestovi);
		
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("\""+ftfVremeOdrzavanja.getText().charAt(0)+"\"");
				if(!proveraPogresno()) {
					

					Termin t = new Termin();
					
					t.setId(KorisneFunkcije.generisiId(TerminManager.getInstance().getTerminiMapa()));
					t.setVremeOdrzavanja(LocalDateTime.parse(ftfVremeOdrzavanja.getText().trim(), KorisneFunkcije.dtf1));
					t.setTest((Test)cmbTestovi.getSelectedItem());
					
					System.out.println(t);
					
					
					TerminManager.getInstance().getTermini().add(t);
					TerminManager.getInstance().getTerminiMapa().put(t.getId(), t);
					
					t.getTest().getTermini().add(t);
					
					TerminCreate.this.setVisible(false);
					
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka pokusajte ponovo");
			}
		});
		pnlDugmad.add(btnSnimi);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TerminCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		
		setVisible(true);
	}




	@Override
	public boolean proveraPogresno() {
		return ftfVremeOdrzavanja.getText().trim().equals("") || KorisneFunkcije.loseLokalnoVreme(ftfVremeOdrzavanja.getText().trim()) || cmbTestovi.getSelectedIndex() == -1;
	}

}
