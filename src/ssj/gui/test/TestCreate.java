package ssj.gui.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.gui.jezik.JezikCreate;
import ssj.managers.JezikManager;
import ssj.managers.KursManager;
import ssj.managers.TestManager;
import ssj.model.Kurs;
import ssj.model.Test;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class TestCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 5026498511073293162L;
	private JPanel pnlCentar;
	private JLabel lblNaziv;
	private JTextField tfNaziv;
	private JLabel lblOpis;
	private JTextArea taOpis;
	private JComboBox<Kurs> cmbKurs;
	private JLabel lblKurs;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;

	public TestCreate(TestoviShow roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje novog testa");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblNaziv = new JLabel("Naziv: ");
		pnlCentar.add(lblNaziv);
		
		tfNaziv = new JTextField(20);
		pnlCentar.add(tfNaziv);
		
		
		lblOpis = new JLabel("Opis: ");
		pnlCentar.add(lblOpis);
		
		taOpis = new JTextArea(10,25);
		taOpis.setLineWrap(true);
		pnlCentar.add(new JScrollPane(taOpis));
		
		
		lblKurs = new JLabel("Kurs: ");
		pnlCentar.add(lblKurs);
		
		cmbKurs = new JComboBox<Kurs>();
		for(Kurs k : KursManager.getInstance().getKursevi()) {
			cmbKurs.addItem(k);
		}
		cmbKurs.setRenderer(new ComboBoxRenderer());
		pnlCentar.add(cmbKurs);
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Test t = new Test();
					t.setId(KorisneFunkcije.generisiId(TestManager.getInstance().getTestoviMapa()));
					t.setNaziv(tfNaziv.getText());
					t.setOpis(taOpis.getText());
					t.setKurs((Kurs) cmbKurs.getSelectedItem());
					
					TestManager.getInstance().getTestovi().add(t);
					TestManager.getInstance().getTestoviMapa().put(t.getId(), t);
					
					t.getKurs().getTestovi().add(t);
					
					TestCreate.this.setVisible(false);
					
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
				TestCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return tfNaziv.getText().trim().equals("") || taOpis.getText().trim().equals("") || cmbKurs.getSelectedIndex() == -1;
	}
}
