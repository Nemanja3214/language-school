package ssj.gui.ucenik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ssj.gui.osoba.OsobaCreate;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Pol;
import ssj.model.osoba.Ucenik;
import ssj.model.osoba.Uloga;
import ssj.utils.KorisneFunkcije;

public class UcenikCreate extends OsobaCreate{
	private static final long serialVersionUID = 6500607465722395441L;
	private JLabel lblStanjeRacuna;
	private JTextField tfStanjeRacuna;
	private JLabel lblFusnota;
	
	public UcenikCreate(JFrame roditelj) {
		super(roditelj);
		lblStanjeRacuna = new JLabel("Stanje računa: ");
		pnlCentar.add(lblStanjeRacuna);
		
		tfStanjeRacuna = new JTextField(20);
		pnlCentar.add(tfStanjeRacuna);
		
		lblFusnota = new JLabel("*Korisničko ime i lozinka ne moraju biti uneti ukoliko učenik ne želi da kreira online nalog");
		pnlCentar.add(lblFusnota, "span");
		
		btnKreiraj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Ucenik u = new Ucenik();
					u.setId(KorisneFunkcije.generisiId(OsobaManager.getInstance().getOsobeMapa()));
					u.setUloga(Uloga.UCENIK);
					u.setAdresa(tfAdresa.getText());
					u.setTelefon(tfTelefon.getText());
					u.setIme(tfIme.getText());
					u.setDatumRodjenja(LocalDate.parse(ftfDatumRodjenja.getText(), KorisneFunkcije.dtf));
					u.setKorisnickoIme(tfKorisnickoIme.getText());
					u.setLozinka(String.valueOf(pfLozinka.getPassword()));
					u.setPrezime(tfPrezime.getText());
					if(rbMuski.isSelected())
						u.setPol(Pol.muski);
					else
						u.setPol(Pol.zenski);
					u.setStanjeRacuna(Double.parseDouble(tfStanjeRacuna.getText()));
					
					OsobaManager.getInstance().getOsobe().add(0,u);
					OsobaManager.getInstance().getOsobeMapa().put(u.getId(), u);
					
					OsobaManager.getInstance().getUcenici().add(0,u);
					OsobaManager.getInstance().getUceniciMapa().put(u.getId(), u);
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
		return super.proveraPogresno() || !KorisneFunkcije.isDouble(this.tfStanjeRacuna.getText()) ||
				(tfKorisnickoIme.getText().trim().equals("") && !(String.valueOf(pfLozinka.getPassword())).trim().equals(""))
				|| (!tfKorisnickoIme.getText().trim().equals("") && (String.valueOf(pfLozinka.getPassword())).trim().equals(""));
	}
	
}
