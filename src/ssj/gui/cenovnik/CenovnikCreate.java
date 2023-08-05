package ssj.gui.cenovnik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ssj.managers.CenovnikManager;
import ssj.model.Cenovnik;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class CenovnikCreate extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = 2753429293437633038L;
	private JLabel lblVaziOd;
	private JPanel pnlCentar;
	private JFormattedTextField ftfVaziOd;
	private JLabel lblVaziDo;
	private JFormattedTextField ftfVaziDo;
	private JPanel pnlDugmad;
	private JButton btnDalje;

	public CenovnikCreate(JFrame roditelj) {
		super(roditelj, true);
		setSize(600,400);
		setTitle("Kreiranje novog cenovnika");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 2"));
		
		lblVaziOd = new JLabel("Važi od(dd.MM.yyyy.): ");
		pnlCentar.add(lblVaziOd);
		
		ftfVaziOd = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfVaziOd.setColumns(20);
		ftfVaziOd.setToolTipText("dd.MM.yyyy.");
		pnlCentar.add(ftfVaziOd, "wrap");
		
		
		lblVaziDo = new JLabel("Važi do(dd.MM.yyyy.): ");
		pnlCentar.add(lblVaziDo);
		
		ftfVaziDo = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfVaziDo.setColumns(20);
		ftfVaziDo.setToolTipText("dd.MM.yyyy.");
		pnlCentar.add(ftfVaziDo, "wrap");
		
		getContentPane().add(pnlCentar);
		
		
		pnlDugmad = new JPanel();
		
		btnDalje = new JButton("Dalje");
		btnDalje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					Cenovnik c = new Cenovnik();
					c.setId(KorisneFunkcije.generisiId(CenovnikManager.getInstance().getCenovniciMapa()));
					c.setVaziOd(LocalDate.parse(ftfVaziOd.getText().trim(), KorisneFunkcije.dtf));
					c.setVaziDo(LocalDate.parse(ftfVaziDo.getText().trim(), KorisneFunkcije.dtf));
					
					CenovnikManager.getInstance().getCenovnici().add(c);
					CenovnikManager.getInstance().getCenovniciMapa().put(c.getId(), c);
					
					KurseviCenePopunjavanje kcp = new KurseviCenePopunjavanje(roditelj, c);
					dispose();
					kcp.setVisible(true);
					CenovnikManager.getInstance().azurirajTrenutni();
				}
				else {
					JOptionPane.showMessageDialog(null, "Neispravan unos podataka, pokušajte ponovo");
				}
			}
		});
		pnlDugmad.add(btnDalje);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return ftfVaziDo.getText().trim().equals("") || ftfVaziOd.getText().trim().equals("") ||
				LocalDate.parse(ftfVaziOd.getText().trim(), KorisneFunkcije.dtf).isAfter(LocalDate.parse(ftfVaziDo.getText().trim(), KorisneFunkcije.dtf));
	}
}
