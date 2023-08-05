package ssj.gui.predavac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ssj.gui.jezik.JezikListRenderer;
import ssj.gui.osoba.OsobaEdit;
import ssj.managers.JezikManager;
import ssj.managers.OsobaManager;
import ssj.model.Jezik;
import ssj.model.osoba.Pol;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Uloga;
import ssj.utils.KorisneFunkcije;

public class PredavacEdit extends OsobaEdit{
	private static final long serialVersionUID = 6950833312468582360L;
	private JLabel lblStrucnaSprema;
	private JTextField tfStrucnaSprema;
	private JLabel lblStaz;
	private JTextField tfStaz;
	private JList<Jezik> lbJezici;
	private JLabel lblKoeficijent;
	private JTextField tfKoeficijent;
	public PredavacEdit(Predavac p, JFrame roditelj) {
		super(p, roditelj);
		
		setSize(800,600);
		
		setTitle("Izmena predavača");
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
		List<Jezik> jezici = JezikManager.getInstance().getJezici();
		List<Integer> selektovani = new ArrayList<Integer>();
		for(int i=0; i<jezici.size();i++) {
			if(!jezici.get(i).isObrisan()) {
				dlm.addElement(jezici.get(i));
				if(p.getJezici().contains(jezici.get(i)))
					selektovani.add(i);
			}
			
		}
		lbJezici = new JList<Jezik>(dlm);
		lbJezici.setCellRenderer(new JezikListRenderer());

		int selektovaniNiz[] = new int[selektovani.size()];
		for(int i=0; i<selektovani.size(); i++) {
			selektovaniNiz[i] = selektovani.get(i);
		}
		lbJezici.setSelectedIndices(selektovaniNiz);
		
		pnlCentar.add(new JScrollPane(lbJezici), "span, wrap, align center");

		
		
		
		btnKreiraj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
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
					
					for(Jezik j: p.getJezici()) {
						j.getPredavaci().remove(p);
					}
					
					p.getJezici().clear();
					
					
					
					int[] selektovani = lbJezici.getSelectedIndices();
					if(selektovani.length > 0) {
						for(int index: selektovani) {
							p.getJezici().add(lbJezici.getModel().getElementAt(index));
						}
					}
					
					for(Jezik j: p.getJezici()) {
						j.getPredavaci().add(p);
					}
					
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka pokušajte ponovo");
			}

			
		});
		this.popuniPolja(p);
		setVisible(true);
	}
	@Override
	public boolean proveraPogresno() {
		return super.proveraPogresno() || !KorisneFunkcije.isInt(tfStaz.getText().trim()) || !KorisneFunkcije.isDouble(tfKoeficijent.getText().trim())
				|| tfKorisnickoIme.getText().trim().equals("") || (String.valueOf(pfLozinka.getPassword())).trim().equals("");
	}
	

	protected void popuniPolja(Predavac p) {
		super.popuniPolja(p);
		this.tfStrucnaSprema.setText(p.getNivoSpreme());
		this.tfStaz.setText(Integer.toString(p.getGodineStaza()));
		this.tfKoeficijent.setText(Double.toString(p.getKoeficijent()));
	}
	}

	


