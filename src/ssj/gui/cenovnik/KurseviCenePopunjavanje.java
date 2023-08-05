package ssj.gui.cenovnik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ssj.managers.KursManager;
import ssj.model.Cenovnik;
import ssj.model.Kurs;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class KurseviCenePopunjavanje extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -1877081736537956412L;
	private JPanel pnlCentar;
	private JLabel lblCena;
	private JTextField tfCena;
	private JLabel lblUvecanjeCene;
	private JTextField tfUvecanjeCene;
	private JLabel lblPopust;
	private JTextField tfPopust;
	private JLabel lblKurs;
	private JComboBox<Kurs> cmbKurs;
	private JPanel pnlDugmad;
	private JButton btnSnimi;
	private JLabel lblUputstvo;

	public KurseviCenePopunjavanje(JFrame roditelj, Cenovnik c) {

		super(roditelj, true);
		setSize(600,400);
		setTitle("Popunjavanje polja za kurs");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblKurs = new JLabel("Kurs: ");
		pnlCentar.add(lblKurs);
		
		cmbKurs = new JComboBox<Kurs>();
		for(Kurs k: KursManager.getInstance().getKursevi()) {
			cmbKurs.addItem(k);
		}
		pnlCentar.add(cmbKurs);
		
		
		lblCena = new JLabel("Cena: ");
		pnlCentar.add(lblCena);
		
		tfCena = new JTextField(20);
		pnlCentar.add(tfCena);
		
		
		lblUvecanjeCene = new JLabel("Uvećanje cene: ");
		pnlCentar.add(lblUvecanjeCene);
		
		tfUvecanjeCene = new JTextField(20);
		pnlCentar.add(tfUvecanjeCene);
		
		
		lblPopust = new JLabel("Popust(0-100): ");
		pnlCentar.add(lblPopust);
		
		tfPopust = new JTextField(20);
		tfPopust.setToolTipText("0-100");
		pnlCentar.add(tfPopust);
		
		getContentPane().add(pnlCentar);
		
		
		pnlDugmad = new JPanel();
		
		btnSnimi = new JButton("Snimi");
		btnSnimi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Kurs k = (Kurs) cmbKurs.getSelectedItem();
					c.getCene().put(k, Double.parseDouble(tfCena.getText().trim()));
					c.getPopusti().put(k, Double.parseDouble(tfPopust.getText().trim()));
					c.getUvecanjaCena().put(k, Double.parseDouble(tfUvecanjeCene.getText().trim()));
					
					cmbKurs.removeItem(k);
					tfCena.setText("");
					tfPopust.setText("");
					tfUvecanjeCene.setText("");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka, pokušajte ponovo");
				}
			}
		});
		pnlDugmad.add(btnSnimi);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
	}

	@Override
	public boolean proveraPogresno() {
		return cmbKurs.getSelectedIndex() == -1 || tfCena.getText().trim().equals("") || tfUvecanjeCene.getText().trim().equals("") || tfPopust.getText().trim().equals("")
				|| !KorisneFunkcije.isDouble(tfCena.getText().trim()) || !KorisneFunkcije.isDouble(tfPopust.getText().trim())
				|| !KorisneFunkcije.isDouble(tfUvecanjeCene.getText().trim()) || Double.parseDouble(tfPopust.getText().trim()) < 0 ||
				Double.parseDouble(tfPopust.getText().trim()) > 100;
	}
}
