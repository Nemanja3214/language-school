package ssj.gui.sekretar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ssj.gui.osoba.OsobaEdit;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Pol;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;
import ssj.model.osoba.Uloga;
import ssj.utils.KorisneFunkcije;

public class SekretarEdit extends OsobaEdit{
	private static final long serialVersionUID = 6950833312468582360L;
	private JLabel lblStrucnaSprema;
	private JTextField tfStrucnaSprema;
	private JLabel lblStaz;
	private JTextField tfStaz;
	private JLabel lblKoeficijent;
	private JTextField tfKoeficijent;
	public SekretarEdit(Sekretar s, JFrame roditelj) {
		super(s, roditelj);
		
		setTitle("Izmena učenika");
		lblStrucnaSprema = new JLabel("Stručna sprema: ");
		pnlCentar.add(lblStrucnaSprema);
		
		tfStrucnaSprema = new JTextField(20);
		pnlCentar.add(tfStrucnaSprema);
		
		
		lblStaz = new JLabel("Staž: ");
		pnlCentar.add(lblStaz);
		
		tfStaz = new JTextField(20);
		pnlCentar.add(tfStaz);
		
		lblKoeficijent = new JLabel("Koeficijent: ");
		pnlCentar.add(lblKoeficijent);
		
		tfKoeficijent = new JTextField(20);
		pnlCentar.add(tfKoeficijent);


		btnKreiraj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					s.setId(KorisneFunkcije.generisiId(OsobaManager.getInstance().getOsobeMapa()));
					s.setUloga(Uloga.SEKRETAR);
					s.setAdresa(tfAdresa.getText());
					s.setTelefon(tfTelefon.getText());
					s.setIme(tfIme.getText());
					s.setDatumRodjenja(LocalDate.parse(ftfDatumRodjenja.getText(), KorisneFunkcije.dtf));
					s.setKorisnickoIme(tfKorisnickoIme.getText());
					s.setLozinka(String.valueOf(pfLozinka.getPassword()));
					s.setPrezime(tfPrezime.getText());
					if(rbMuski.isSelected())
						s.setPol(Pol.muski);
					else
						s.setPol(Pol.zenski);
					s.setNivoSpreme(tfStrucnaSprema.getText());
					s.setGodineStaza(Integer.parseInt(tfStaz.getText()));
					s.setKoeficijent(Double.parseDouble(tfKoeficijent.getText()));
					
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka pokušajte ponovo");
			}

			
		});
		popuniPolja(s);
		setVisible(true);
	}
	@Override
	public boolean proveraPogresno() {
		return super.proveraPogresno() || !KorisneFunkcije.isInt(tfStaz.getText().trim()) || !KorisneFunkcije.isDouble(tfKoeficijent.getText().trim())
				|| tfKorisnickoIme.getText().trim().equals("") || (String.valueOf(pfLozinka.getPassword())).trim().equals("");
	}
	

	protected void popuniPolja(Sekretar s) {
		super.popuniPolja(s);
		this.tfStrucnaSprema.setText(s.getNivoSpreme());
		this.tfStaz.setText(Integer.toString(s.getGodineStaza()));
	}
	}

	


