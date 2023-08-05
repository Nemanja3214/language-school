package ssj.gui.termin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;
import ssj.managers.TestManager;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class TerminEdit extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -8685179201518913130L;
	private JPanel pnlCentar;
	private JLabel lblVremeOdrzavanja;
	private JFormattedTextField ftfVremeOdrzavanja;
	private JComboBox<Test> cmbTestovi;
	private Termin t;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;
	private JLabel lblTest;

	public TerminEdit(Termin t, TerminiShow roditelj) {		
		super(roditelj, true);
		this.t = t;
		
		setSize(600,400);
		setTitle("Izmena termina");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblVremeOdrzavanja = new JLabel("Vreme održavanja(HH:mm dd.MM.yyyy.): ");
		pnlCentar.add(lblVremeOdrzavanja);

		ftfVremeOdrzavanja = new JFormattedTextField();
		ftfVremeOdrzavanja.setColumns(20);
		pnlCentar.add(ftfVremeOdrzavanja, "wrap");
		
		try {
			MaskFormatter mf = new MaskFormatter("##:## ##.##.####.");
			mf.setPlaceholderCharacter('_');
			mf.install(ftfVremeOdrzavanja);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Došlo je do greske prilikom parsiranja vremena održavanja");
		}
		
		lblTest = new JLabel("Test: ");
		pnlCentar.add(lblTest);
		
		cmbTestovi = new JComboBox<Test>();
		for(Test test: TestManager.getInstance().getTestovi()) {
			if(!test.isObrisan())
				cmbTestovi.addItem(test);
		}
		cmbTestovi.setRenderer(new ComboBoxRenderer());
		pnlCentar.add(cmbTestovi);
		
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					t.getTest().getTermini().remove(t);
					
					t.setTest((Test)cmbTestovi.getSelectedItem());
					t.setVremeOdrzavanja(LocalDateTime.parse(ftfVremeOdrzavanja.getText().trim(), KorisneFunkcije.dtf1));
					
					t.getTest().getTermini().add(t);
					
					System.out.println(t);
					TerminEdit.this.setVisible(false);
					
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
				TerminEdit.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		popuniPolja();
		setVisible(true);
	}

	private void popuniPolja() {
		this.ftfVremeOdrzavanja.setText(t.getVremeOdrzavanja().format(KorisneFunkcije.dtf1));
		this.cmbTestovi.setSelectedItem(t.getTest());
		
	}

	@Override
	public boolean proveraPogresno() {
		return ftfVremeOdrzavanja.getText().trim().equals("") || KorisneFunkcije.loseLokalnoVreme(ftfVremeOdrzavanja.getText().trim()) || cmbTestovi.getSelectedIndex() == -1;
	}

}
