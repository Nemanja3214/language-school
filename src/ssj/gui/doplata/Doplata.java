package ssj.gui.doplata;

import java.awt.BorderLayout;
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
import ssj.gui.sekretar.SekretarFrm;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Ucenik;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class Doplata extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -2570950319762084324L;
	private JPanel pnlCentar;
	private JComboBox<Ucenik> cmbUcenik;
	private JTextField tfIznos;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JLabel lblUcenik;
	private JLabel lblIznos;
	private JLabel lblTrenutnoStanje;
	private JLabel lblTrenutnoStanjeVrednost;
	public Doplata(SekretarFrm roditelj) {
		super(roditelj,true);
		setSize(600,400);
		setTitle("Doplata novca na finansijsku karticu");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));

		lblUcenik = new JLabel("Učenik: ");
		pnlCentar.add(lblUcenik);
		
		cmbUcenik = new JComboBox<Ucenik>();
		for(Ucenik u : OsobaManager.getInstance().getUcenici()) {
			if(!u.isObrisan())
				cmbUcenik.addItem(u);
		}
		cmbUcenik.setRenderer(new ComboBoxRenderer());
		cmbUcenik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Ucenik u = (Ucenik)cmbUcenik.getSelectedItem();
				lblTrenutnoStanjeVrednost.setText(Double.toString(u.getStanjeRacuna()));
			}
		});
		pnlCentar.add(cmbUcenik);
		
		
		lblTrenutnoStanje = new JLabel("Trenutno stanje: ");
		pnlCentar.add(lblTrenutnoStanje);
		
		lblTrenutnoStanjeVrednost = new JLabel();
		pnlCentar.add(lblTrenutnoStanjeVrednost);
		
		
		lblIznos = new JLabel("Iznos: ");
		pnlCentar.add(lblIznos);
		
		tfIznos = new JTextField(20);
		pnlCentar.add(tfIznos);
		
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Ucenik u  = (Ucenik) cmbUcenik.getSelectedItem();
					u.setStanjeRacuna(u.getStanjeRacuna() + Double.parseDouble(tfIznos.getText()));
					cmbUcenik.setSelectedItem(u);
				}
				else {
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka, pokušajte ponovo.");
				}
			}
		});
		pnlDugmad.add(btnDodaj);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	@Override
	public boolean proveraPogresno() {
		return cmbUcenik.getSelectedIndex() == -1 || tfIznos.getText().equals("") || !KorisneFunkcije.isDouble(tfIznos.getText());
	}
}
