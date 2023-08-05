package ssj.gui.kurs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.gui.jezik.JezikCreate;
import ssj.managers.CenovnikManager;
import ssj.managers.JezikManager;
import ssj.managers.KursManager;
import ssj.managers.OsobaManager;
import ssj.model.Cenovnik;
import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.model.osoba.Predavac;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class KursCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -887714131223526936L;
	private JPanel pnlCentar;
	private JLabel lblNaziv;
	private JTextField tfNaziv;
	private JLabel lblJezik;
	private JComboBox<Jezik> cmbJezik;
	private JLabel lblPredavac;
	private JComboBox<Predavac> cmbPredavac;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;
	private ComboBoxRenderer cbr;

	public KursCreate(KurseviShow roditelj) {
		super(roditelj, true);
		
		setSize(600,400);
		setTitle("Kreiranje novog kursa");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblNaziv = new JLabel("Naziv: ");
		pnlCentar.add(lblNaziv);
		
		tfNaziv = new JTextField(20);
		pnlCentar.add(tfNaziv);
		
		
		lblPredavac = new JLabel("Predavač: ");
		pnlCentar.add(lblPredavac);
		
		cmbPredavac = new JComboBox<Predavac>();
		for(Predavac p : OsobaManager.getInstance().getPredavaci()) {
			if(!p.isObrisan())
				cmbPredavac.addItem(p);
		}
		cmbPredavac.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbJezik.removeAllItems();
				Predavac p = (Predavac)cmbPredavac.getSelectedItem();
				for(Jezik j: p.getJezici()) {
					if(!j.isObrisan())
						cmbJezik.addItem(j);
				}
			}
		});
		cbr = new ComboBoxRenderer();
		cmbPredavac.setRenderer(cbr);
		pnlCentar.add(cmbPredavac);
		
		
		lblJezik = new JLabel("Jezik: ");
		pnlCentar.add(lblJezik);
		
		cmbJezik = new JComboBox<Jezik>();
		cmbJezik.setRenderer(cbr);
		pnlCentar.add(cmbJezik);
		
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {

					Kurs k = new Kurs();
					
					k.setId(KorisneFunkcije.generisiId(KursManager.getInstance().getKurseviMapa()));
					k.setNaziv(tfNaziv.getText());
					
					k.setJezik((Jezik)cmbJezik.getSelectedItem());
					k.setPredavac((Predavac) cmbPredavac.getSelectedItem());
					System.out.println(k);
					
					
					KursManager.getInstance().getKursevi().add(k);
					KursManager.getInstance().getKurseviMapa().put(k.getId(), k);
					
					
					
					KursCreate.this.setVisible(false);
					
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
				KursCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return  tfNaziv.getText().trim().equals("") || cmbJezik.getSelectedIndex() == -1 || cmbPredavac.getSelectedIndex() == -1; 
	}

}
