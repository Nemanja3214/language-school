package ssj.gui.jezik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ssj.managers.JezikManager;
import ssj.model.Jezik;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class JezikCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -3975178437962754606L;
	private JLabel lblNaziv;
	private JPanel pnlCentar;
	private JTextField tfNazvi;
	private JPanel pnlDugmad;
	private JButton btnKreiraj;
	private JButton btnIzadji;
	private JButton btnSnimi;

	public JezikCreate(JFrame roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje novog jezika");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel();
		
		lblNaziv = new JLabel("Naziv: ");
		pnlCentar.add(lblNaziv);
		
		tfNazvi = new JTextField(20);
		pnlCentar.add(tfNazvi);
				
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Jezik j = new Jezik();
					j.setId(KorisneFunkcije.generisiId(JezikManager.getInstance().getJeziciMapa()));
					j.setNaziv(tfNazvi.getText());
					JezikManager.getInstance().getJezici().add(j);
					JezikManager.getInstance().getJeziciMapa().put(j.getId(), j);
					JezikCreate.this.setVisible(false);
					
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
				JezikCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
				
	}
	public boolean proveraPogresno() {
		return this.tfNazvi.getText().trim().equals("");
	}
}
