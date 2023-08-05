package ssj.gui.rezultat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.controllers.RacunController;
import ssj.managers.RezultatManager;
import ssj.managers.TerminManager;
import ssj.model.Kurs;
import ssj.model.Pohadjanje;
import ssj.model.Racun;
import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.model.osoba.Ucenik;
import ssj.utils.ComboBoxRenderer;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class RezultatEdit extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 5537728811253056950L;
	private Rezultat r;
	private JPanel pnlCentar;
	private JLabel lblBrojBodova;
	private JTextField tfBrojBodova;
	private JLabel lblOcena;
	private JComboBox<Integer> cmbOcena;
	private JCheckBox cbPolozio;
	private JLabel lblTermin;
	private JComboBox<Termin> cmbTermin;
	private JLabel lblUcenik;
	private JComboBox<Ucenik> cmbUcenik;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JButton btnIzadji;
	private ComboBoxRenderer cbr;

	public RezultatEdit(Rezultat r, RezultatiShow roditelj, List<Rezultat> rezultati) {
		super(roditelj, true);
		this.r = r;
		
		setSize(600,400);
		setTitle("Izmena rezultata");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		
		lblBrojBodova = new JLabel("Broj bodova: ");
		pnlCentar.add(lblBrojBodova);
		
		tfBrojBodova = new JTextField(20);
		pnlCentar.add(tfBrojBodova);
		
		
		lblOcena = new JLabel("Ocena: ");
		pnlCentar.add(lblOcena);
		
		cmbOcena = new JComboBox<Integer>();
		for(int i=1; i<6; i++) {
			cmbOcena.addItem(i);
		}
		pnlCentar.add(cmbOcena);
		
		
		cbPolozio = new JCheckBox("Položio");
		pnlCentar.add(cbPolozio, "span");
		
		
		lblTermin = new JLabel("Termin: ");
		pnlCentar.add(lblTermin);
		
		cmbTermin = new JComboBox<Termin>();
		for(Termin t: TerminManager.getInstance().getTermini()) {
			cmbTermin.addItem(t);
		}

		cbr = new ComboBoxRenderer();
		cmbTermin.setRenderer(cbr);
		pnlCentar.add(cmbTermin);
		
		
		lblUcenik = new JLabel("Učenik: ");
		pnlCentar.add(lblUcenik);
		
		cmbUcenik = new JComboBox<Ucenik>();
		cmbUcenik.addItem(r.getUcenik());
		cmbUcenik.setRenderer(cbr);
		pnlCentar.add(cmbUcenik);
		
		getContentPane().add(pnlCentar);
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					
					r.getTermin().getRezultati().remove(r);
					r.getUcenik().getRezultati().remove(r);

					r.setBrojBodova(Double.parseDouble(tfBrojBodova.getText().trim()));
					r.setOcena((Integer)cmbOcena.getSelectedItem());
					r.setPolozio(cbPolozio.isSelected());
					r.setTermin((Termin) cmbTermin.getSelectedItem());
					r.setUcenik((Ucenik) cmbUcenik.getSelectedItem());
					
					System.out.println(r);
					
					r.getTermin().getRezultati().add(r);
					r.getUcenik().getRezultati().add(r);
					
					
					Kurs k = r.getTermin().getTest().getKurs();
					Pohadjanje pohadjanje = null;
					for(Pohadjanje p: r.getUcenik().getPohadjanja()) {
						if(p.getKurs().equals(k)) {
							pohadjanje = p;
							break;
						}
					}
					if(pohadjanje != null)
						pohadjanje.setPolozio(r.isPolozio());
					else
						JOptionPane.showMessageDialog(null, "Došlo je do greške, nije nadjeno pohadjanje ovog učenika sa ovim kursom");

					
					RezultatEdit.this.setVisible(false);
					
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
				RezultatEdit.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		popuniPolja();
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return tfBrojBodova.getText().trim().equals("") || cmbOcena.getSelectedIndex() == -1
				|| cmbTermin.getSelectedIndex() == -1 || cmbUcenik.getSelectedIndex() == -1;
	}
	
	public void popuniPolja() {
		tfBrojBodova.setText(Double.toString(this.r.getBrojBodova()));
		cmbOcena.setSelectedItem(r.getOcena());
		cmbTermin.setSelectedItem(r.getTermin());
		cmbUcenik.setSelectedItem(r.getUcenik());
		cbPolozio.setSelected(r.isPolozio());
	}
	

}
