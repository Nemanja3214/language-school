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

public class JezikEdit extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 2047754151262091967L;
	private JPanel pnlCentar;
	private JLabel lblNaziv;
	private JTextField tfNazvi;
	private JPanel pnlDugmad;
	private JButton btnIzadji;
	private Jezik j;
	private JButton btnSnimi;

	public JezikEdit(Jezik j, JFrame roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Izmena jezika");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.j = j;
		
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
					j.setId(KorisneFunkcije.generisiId(JezikManager.getInstance().getJeziciMapa()));
					j.setNaziv(tfNazvi.getText());
					JezikEdit.this.setVisible(false);
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
				JezikEdit.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		popuniPolja();
		
		setVisible(true);
				
	}
	public boolean proveraPogresno() {
		return this.tfNazvi.getText().trim().equals("");
	}
	public void popuniPolja() {
		this.tfNazvi.setText(j.getNaziv());
	}
	}	
