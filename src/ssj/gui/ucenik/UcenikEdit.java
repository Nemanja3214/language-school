package ssj.gui.ucenik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ssj.gui.osoba.OsobaEdit;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Pol;
import ssj.model.osoba.Ucenik;
import ssj.model.osoba.Uloga;
import ssj.utils.KorisneFunkcije;

public class UcenikEdit extends OsobaEdit{
	private static final long serialVersionUID = 8339361673010302697L;
	private JLabel lblStanjeRacuna;
	private JTextField tfStanjeRacuna;

	public UcenikEdit(Ucenik u, JFrame roditelj) {
		super(u, roditelj);
		
		setTitle("Izmena učenika");
		lblStanjeRacuna = new JLabel("Stanje računa: ");
		pnlCentar.add(lblStanjeRacuna);
		
		tfStanjeRacuna = new JTextField(20);
		pnlCentar.add(tfStanjeRacuna);
		btnKreiraj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
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
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka pokušajte ponovo");
			}

			
		});
		popuniPolja(u);
		setVisible(true);
	}
	@Override
	public boolean proveraPogresno() {
		return super.proveraPogresno() || !KorisneFunkcije.isDouble(this.tfStanjeRacuna.getText()) ||
				(tfKorisnickoIme.getText().trim().equals("") && !(String.valueOf(pfLozinka.getPassword())).trim().equals(""))
				|| (!tfKorisnickoIme.getText().trim().equals("") && (String.valueOf(pfLozinka.getPassword())).trim().equals(""));
	}
	

	protected void popuniPolja(Ucenik u) {
		super.popuniPolja(u);
		this.tfStanjeRacuna.setText(Double.toString(u.getStanjeRacuna()));
	}
	
}
