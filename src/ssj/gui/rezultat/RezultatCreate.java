package ssj.gui.rezultat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
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

public class RezultatCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -2534970697798411227L;
	private JPanel pnlDugmad;
	private AbstractButton btnSnimi;
	private JButton btnIzadji;
	private JLabel lblBrojBodova;
	private JPanel pnlCentar;
	private JTextField tfBrojBodova;
	private JLabel lblOcena;
	private JComboBox<Integer> cmbOcena;
	private JLabel lblPolozio;
	private JCheckBox cbPolozio;
	private JComboBox<Termin> cmbTermin;
	private JLabel lblTermin;
	private JLabel lblUcenik;
	private JComboBox<Ucenik> cmbUcenik;
	private ComboBoxRenderer cbr;
	private JLabel lblTest;
	private JLabel lblTestVrednost;

	public RezultatCreate(RezultatiShow roditelj) {
		super(roditelj, true);
		
		setSize(600,400);
		setTitle("Kreiranje rezultata");
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
		cmbTermin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbUcenik.removeAllItems();
				
				
				Termin t =(Termin) cmbTermin.getSelectedItem();
				
				lblTestVrednost.setText(t.getTest().getNaziv());
				
				Set<Ucenik> vecOcenjeni = new HashSet<Ucenik>();
				
				for(Rezultat r: t.getRezultati()) {
					vecOcenjeni.add(r.getUcenik());
				}
				
				for(Racun r: t.getRacuni()) {
					if(!vecOcenjeni.contains(r.getUcenik()))
						cmbUcenik.addItem(r.getUcenik());
				}
				
			}
		});
		cbr = new ComboBoxRenderer();
		cmbTermin.setRenderer(cbr);
		pnlCentar.add(cmbTermin);
		
		lblTest = new JLabel("Test: ");
		pnlCentar.add(lblTest);
		
		lblTestVrednost = new JLabel();
		pnlCentar.add(lblTestVrednost);
		
		lblUcenik = new JLabel("Ucenik: ");
		pnlCentar.add(lblUcenik);
		
		cmbUcenik = new JComboBox<Ucenik>();
		cmbUcenik.setRenderer(cbr);
		pnlCentar.add(cmbUcenik);
		
		getContentPane().add(pnlCentar);
		
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					

					Rezultat r = new Rezultat();
					
					r.setId(KorisneFunkcije.generisiId(RezultatManager.getInstance().getRezultatiMapa()));
					r.setBrojBodova(Integer.parseInt(tfBrojBodova.getText().trim()));
					r.setOcena((Integer)cmbOcena.getSelectedItem());
					r.setPolozio(cbPolozio.isSelected());
					r.setTermin((Termin) cmbTermin.getSelectedItem());
					r.setUcenik((Ucenik) cmbUcenik.getSelectedItem());
					
					System.out.println(r);
					
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
					
					RezultatManager.getInstance().getRezultati().add(r);
					RezultatManager.getInstance().getRezultatiMapa().put(r.getId(), r);
					
					r.getTermin().getRezultati().add(r);
					r.getUcenik().getRezultati().add(r);
					
					RezultatCreate.this.setVisible(false);
					
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
				RezultatCreate.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return tfBrojBodova.getText().trim().equals("") || cmbOcena.getSelectedIndex() == -1
				|| cmbTermin.getSelectedIndex() == -1 || cmbUcenik.getSelectedIndex() == -1;
	}

}
