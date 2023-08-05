package ssj.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.OsobaController;
import ssj.gui.administrator.AdministratorFrm;
import ssj.gui.predavac.PredavacFrm;
import ssj.gui.sekretar.SekretarFrm;
import ssj.gui.ucenik.UcenikFrm;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Administrator;
import ssj.model.osoba.Osoba;
import ssj.model.osoba.Predavac;
import ssj.model.osoba.Sekretar;
import ssj.model.osoba.Ucenik;

public class LoginFrm extends JFrame{

	private static final long serialVersionUID = -283617208285478780L;
	private OsobaManager om;
	private JLabel lblKorisnickoIme;
	private JTextField tfKorisnickoIme;
	private JLabel lblLozinka;
	private JPasswordField tfLozinka;
	private JButton btnLogin;
	
	
	public LoginFrm() {
		
		try {
			this.om = OsobaManager.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(400,200);
		setTitle("Dobrodošli, molimo ulogujte se");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		initLogin();
	}

	private void initLogin() {
		MigLayout loginLayout = new MigLayout("wrap 3", "[][]", "30[]20[][]20[]30");
//		MigLayout loginLayout = new MigLayout(" wrap 3");
		setLayout(loginLayout);
		
		lblKorisnickoIme = new JLabel("Korisničko ime: ");
		tfKorisnickoIme = new JTextField(20);
		
		lblLozinka = new JLabel("Lozinka: ");
		tfLozinka = new JPasswordField(20);
		
		add(lblKorisnickoIme);
		add(tfKorisnickoIme, "span 2");
		
		
		add(lblLozinka);
		add(tfLozinka, "span 2");
		
		btnLogin = new JButton("Prijava");
		getRootPane().setDefaultButton(btnLogin);
		add(btnLogin, "align center, cell 1 3");
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = tfKorisnickoIme.getText();
				String lozinka = String.valueOf(tfLozinka.getPassword());
				
				if(!username.trim().equals("")) {
					Osoba loginRezultat = OsobaController.login(username, lozinka);
					
					if( loginRezultat != null && loginRezultat.getKorisnickoIme().equals(username) && loginRezultat.getLozinka().equals(lozinka)
							&& !loginRezultat.isObrisan()) {
						
						if(loginRezultat instanceof Administrator) {
							new AdministratorFrm((Administrator)loginRezultat);
						}
						else if(loginRezultat instanceof Sekretar) {
							new SekretarFrm((Sekretar)loginRezultat);
						}
						else if(loginRezultat instanceof Predavac) {
							new PredavacFrm((Predavac)loginRezultat);
						}
						else if(loginRezultat instanceof Ucenik) {
							new UcenikFrm((Ucenik)loginRezultat);
						}
						dispose();
					}
				}
				
				System.out.println("Korisnicko: " + username + "\n Lozinka: " + lozinka);
			}
		});
		
		setVisible(true);
		
	}
}
