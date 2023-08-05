package ssj.gui.notifikacija;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.managers.NotifikacijeManager;
import ssj.model.Kurs;
import ssj.model.osoba.Predavac;
import ssj.notifikacije.Notifikacija;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class NotifikacijaCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 7484185166354175843L;
	private JLabel lblNaslov;
	private JPanel pnlCentar;
	private JTextField tfNaslov;
	private JLabel lblOpis;
	private JTextArea taOpis;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JComboBox<Kurs> cmbKurs;
	private JLabel lblKurs;

	public NotifikacijaCreate(JFrame roditelj, Predavac p) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje notifikacije");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		
		lblNaslov = new JLabel("Naslov: ");
		pnlCentar.add(lblNaslov);
		
		tfNaslov = new JTextField(20);
		pnlCentar.add(tfNaslov);
		
		
		lblOpis = new JLabel("Opis: ");
		pnlCentar.add(lblOpis);
		
		taOpis = new JTextArea(20,15);
		taOpis.setLineWrap(true);
		pnlCentar.add(taOpis);
		
		
		lblKurs = new JLabel("Kurs: ");
		pnlCentar.add(lblKurs);
		
		cmbKurs = new JComboBox<Kurs>();
		for(Kurs k: p.getKursevi()) {
			if(!k.isObrisan())
				cmbKurs.addItem(k);
		}
		pnlCentar.add(cmbKurs);
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Notifikacija n = new Notifikacija();
					n.setId(KorisneFunkcije.generisiId(NotifikacijeManager.getInstance().getNotifikacijeMapa()));
					
					Kurs k = (Kurs) cmbKurs.getSelectedItem();
					n.setKurs(k);
					n.setNaslov(tfNaslov.getText());
					n.setTekst(taOpis.getText());
					
					NotifikacijeManager.getInstance().getNotifikacije().add(n);
					NotifikacijeManager.getInstance().getNotifikacijeMapa().put(n.getId(), n);
					
					k.obavesti(n);
					
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka, pokušajte ponovo");
			}
		});
		pnlDugmad.add(btnSnimi);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return tfNaslov.getText().trim().equals("") || taOpis.getText().trim().equals("") || cmbKurs.getSelectedIndex() == -1;
	}
	
}
