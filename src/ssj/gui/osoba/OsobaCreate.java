package ssj.gui.osoba;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.OsobaController;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Osoba;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public abstract class OsobaCreate extends JDialog implements ProveraUnosa{
	protected Osoba o;

	private static final long serialVersionUID = 4577257812547848618L;
	protected JTextField tfKorisnickoIme;
	protected JPanel pnlCentar;
	protected JTextField tfIme;
	protected JTextField tfPrezime;
	protected JFormattedTextField ftfDatumRodjenja;
	protected SimpleDateFormat df;
	protected JLabel lblKorisnickoIme;
	protected JPasswordField pfLozinka;
	protected JLabel lblLozinka;
	protected JLabel lblIme;
	protected JLabel lblPrezime;
	protected JLabel lblDatumRodjenja;
	protected ButtonGroup bgPol;
	protected JRadioButton rbMuski;
	protected JRadioButton rbZenski;
	protected JLabel lblTelefon;
	protected JTextField tfTelefon;
	protected JLabel lblAdresa;
	protected JTextField tfAdresa;
	protected JPanel pnlDugmad;
	protected JButton btnKreiraj;
	protected JButton btnIzadji;

	public OsobaCreate(JFrame roditelj) {
		super(roditelj,true);
		
		setSize(800,600);
		setTitle("Kreiranje novog naloga");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		pnlCentar = new JPanel();
		pnlCentar.setLayout(new MigLayout("wrap 2"));
		
		
		lblKorisnickoIme = new JLabel("Korisničko ime: ");
		pnlCentar.add(lblKorisnickoIme);
		
		tfKorisnickoIme = new JTextField(20);
		pnlCentar.add(tfKorisnickoIme);
		
		
		lblLozinka = new JLabel("Lozinka: ");
		pnlCentar.add(lblLozinka);
		
		pfLozinka = new JPasswordField(20);
		pnlCentar.add(pfLozinka);
		
		
		lblIme = new JLabel("Ime: ");
		pnlCentar.add(lblIme);
		
		tfIme = new JTextField(20);
		pnlCentar.add(tfIme);
		
		
		lblPrezime = new JLabel("Prezime: ");
		pnlCentar.add(lblPrezime);
		
		tfPrezime = new JTextField(20);
		pnlCentar.add(tfPrezime);

		
		lblDatumRodjenja = new JLabel("Datum rodjenja(dd.MM.yyyy.): ");
		pnlCentar.add(lblDatumRodjenja);
		
		ftfDatumRodjenja = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfDatumRodjenja.setColumns(20);
		ftfDatumRodjenja.setToolTipText("dd.MM.yyyy.");
		pnlCentar.add(ftfDatumRodjenja, "wrap");
		
		
		bgPol = new ButtonGroup();
		
		rbMuski = new JRadioButton("Muški");
		rbMuski.setSelected(true);
		bgPol.add(rbMuski);
		pnlCentar.add(rbMuski);
		
		rbZenski = new JRadioButton("Ženski");
		bgPol.add(rbZenski);
		pnlCentar.add(rbZenski);
		
		lblTelefon = new JLabel("Telefon: ");
		pnlCentar.add(lblTelefon);
		
		tfTelefon = new JTextField(20);
		pnlCentar.add(tfTelefon);
		
		lblAdresa = new JLabel("Adresa: ");
		pnlCentar.add(lblAdresa);
		
		tfAdresa = new JTextField(20);
		pnlCentar.add(tfAdresa);
		
		
//		#id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa
		
		getContentPane().add(pnlCentar,BorderLayout.CENTER);
		
		pnlDugmad = new JPanel();
		
		btnKreiraj = new JButton("Kreiraj");

		pnlDugmad.add(btnKreiraj);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ugasiDijalog();
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
	}
//	#id; korisnickoIme; lozinka; ime; prezime; uloga; datumRodjenja; pol; telefon; adresa
	public boolean proveraPogresno() {
		if(OsobaManager.getInstance().getKorisnicka().contains(tfKorisnickoIme.getText().trim())) {
			JOptionPane.showMessageDialog(null, "Korisničko ime je već zauzeto");
			return true;
		}
			
		return  tfIme.getText().trim().equals("") || tfPrezime.getText().trim().equals("") || ftfDatumRodjenja.getText().trim().equals("") ||
				!KorisneFunkcije.isInt(tfTelefon.getText().trim()) || tfAdresa.getText().trim().equals("") || 
				OsobaManager.getInstance().getKorisnicka().contains(tfKorisnickoIme.getText().trim());
	}
	private void ugasiDijalog() {
		setVisible(false);
	}
	
	
}
