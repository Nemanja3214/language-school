package ssj.gui.predavac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ssj.gui.jezik.JezikListRenderer;
import ssj.gui.osoba.OsobaCreate;
import ssj.managers.JezikManager;
import ssj.managers.OsobaManager;
import ssj.model.Jezik;
import ssj.model.osoba.Pol;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Uloga;
import ssj.utils.KorisneFunkcije;

public class PredavacCreate extends OsobaCreate{
	private static final long serialVersionUID = -3719240267520993014L;
	private JLabel lblStrucnaSprema;
	private JTextField tfStrucnaSprema;
	private JLabel lblStaz;
	private JTextField tfStaz;
	private JList<Jezik> lbJezici;
	private JTextField tfKoeficijent;
	private JLabel lblKoeficijent;

	public PredavacCreate(JFrame roditelj) {
		super(roditelj);
		
		setTitle("Kreiranje novog predavača");
		
		lblStrucnaSprema = new JLabel("Stručna sprema");
		pnlCentar.add(lblStrucnaSprema);
		
		tfStrucnaSprema = new JTextField(20);
		pnlCentar.add(tfStrucnaSprema);
		
		
		lblStaz = new JLabel("Staž");
		pnlCentar.add(lblStaz);
		
		tfStaz = new JTextField(20);
		pnlCentar.add(tfStaz);
		
		
		lblKoeficijent = new JLabel("Koeficijent: ");
		pnlCentar.add(lblKoeficijent);
		
		tfKoeficijent = new JTextField(20);
		pnlCentar.add(tfKoeficijent);
		
		
		DefaultListModel<Jezik> dlm = new DefaultListModel<Jezik>();
		for(Jezik j: JezikManager.getInstance().getJezici()) {
			if(!j.isObrisan())
				dlm.addElement(j);
		}
		lbJezici = new JList<Jezik>(dlm);
		lbJezici.setCellRenderer(new JezikListRenderer());
		pnlCentar.add(new JScrollPane(lbJezici), "span, wrap, align center");

		
		
		btnKreiraj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Predavac p = new Predavac();
					p.setId(KorisneFunkcije.generisiId(OsobaManager.getInstance().getOsobeMapa()));
					p.setUloga(Uloga.PREDAVAC);
					p.setAdresa(tfAdresa.getText());
					p.setTelefon(tfTelefon.getText());
					p.setIme(tfIme.getText());
					p.setDatumRodjenja(LocalDate.parse(ftfDatumRodjenja.getText(), KorisneFunkcije.dtf));
					p.setKorisnickoIme(tfKorisnickoIme.getText());
					p.setLozinka(String.valueOf(pfLozinka.getPassword()));
					p.setPrezime(tfPrezime.getText());
					if(rbMuski.isSelected())
						p.setPol(Pol.muski);
					else
						p.setPol(Pol.zenski);
					
					p.setNivoSpreme(tfStrucnaSprema.getText());
					p.setGodineStaza(Integer.parseInt(tfStaz.getText()));
					p.setKoeficijent(Double.parseDouble(tfKoeficijent.getText()));
					
					int[] selektovani = lbJezici.getSelectedIndices();
					if(selektovani.length > 0) {
						for(int index: selektovani) {
							p.getJezici().add(lbJezici.getModel().getElementAt(index));
						}
					}
					
					
					OsobaManager.getInstance().getOsobe().add(p);
					OsobaManager.getInstance().getOsobeMapa().put(p.getId(), p);
					
					OsobaManager.getInstance().getPredavaci().add(p);
					OsobaManager.getInstance().getPredavaciMapa().put(p.getId(), p);
					
					for(Jezik j: p.getJezici()) {
						j.getPredavaci().add(p);
					}
					
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka pokušajte ponovo");
			}

			
		});
		setVisible(true);
	}
	@Override
	public boolean proveraPogresno() {
		return super.proveraPogresno() || !KorisneFunkcije.isInt(tfStaz.getText().trim()) || !KorisneFunkcije.isDouble(tfKoeficijent.getText().trim()) 
				|| tfKorisnickoIme.getText().trim().equals("") || (String.valueOf(pfLozinka.getPassword())).trim().equals("");
	}
}
