package ssj.gui.izvestaji;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ssj.gui.termin.TerminModel;
import ssj.managers.OsobaManager;
import ssj.managers.TerminManager;
import ssj.model.Kurs;
import ssj.model.Termin;
import ssj.model.Test;
import ssj.model.osoba.Predavac;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class TestoviPredavacaIzvestaj extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -5284689073296664579L;
	private JPanel pnlGore;
	private JComboBox<Predavac> cmbPredavac;
	private JLabel lblPredavac;
	private JLabel lblOpsegDatuma;
	private JFormattedTextField ftfDatum1;
	private JFormattedTextField ftfDatum2;
	private JTable tabela;
	private List<Termin> termini;
	private TerminModel tm;
	private JButton btnPretraga;

	public TestoviPredavacaIzvestaj(IzvestajiFrm roditelj) {
		super(roditelj, true);
		
		setSize(1000,400);
		setTitle("Izveštaj o testovima predavača");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlGore = new JPanel();
		
		
		lblPredavac = new JLabel("Predavač");
		pnlGore.add(lblPredavac);
		
		cmbPredavac = new JComboBox<Predavac>();
		for(Predavac p : OsobaManager.getInstance().getPredavaci()) {
			cmbPredavac.addItem(p);
		}
		pnlGore.add(cmbPredavac);
		
		
		lblOpsegDatuma = new JLabel("Opseg datuma");
		pnlGore.add(lblOpsegDatuma);
		
		ftfDatum1 = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfDatum1.setToolTipText("dd.MM.yyyy.");
		ftfDatum1.setColumns(20);
		pnlGore.add(ftfDatum1, "wrap");
		
		ftfDatum2 = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfDatum2.setToolTipText("dd.MM.yyyy.");
		ftfDatum2.setColumns(20);
		pnlGore.add(ftfDatum2, "wrap");
		
		btnPretraga = new JButton("Pretraga");
		btnPretraga.addActionListener(new ActionListener() {
			private LocalDate datum1;
			private LocalDate datum2;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cmbPredavac.getSelectedIndex() != -1) {
					
					ArrayList<Termin> terminiKurseva = new ArrayList<Termin>();
					Predavac p = (Predavac) cmbPredavac.getSelectedItem();
					for(Kurs k: p.getKursevi()) {
						for(Test t: k.getTestovi()) {
							terminiKurseva.addAll(t.getTermini());
						}
					}
					
					
					if(!ftfDatum1.getText().equals("") && !ftfDatum2.getText().equals(""))
					{
						datum1 = LocalDate.parse(ftfDatum1.getText().trim(), KorisneFunkcije.dtf);
						datum2 = LocalDate.parse(ftfDatum2.getText().trim(), KorisneFunkcije.dtf);
						termini = new ArrayList<Termin>();
						for(Termin t: terminiKurseva) {
							if(t.getVremeOdrzavanja().toLocalDate().isAfter(datum1) && t.getVremeOdrzavanja().toLocalDate().isBefore(datum2)) {
								termini.add(t);
							}
						}
					}
					else {
						termini = terminiKurseva;
					}
					
					
					
					tm.setTermini(termini);
					tm.fireTableDataChanged();
				}
			}
		});
		pnlGore.add(btnPretraga);
		
		getContentPane().add(pnlGore, BorderLayout.NORTH);
		
		
		
		termini = TerminManager.getInstance().getTermini();
		tm = new TerminModel(termini);
		tabela = new JTable(tm);
		tabela.setAutoCreateRowSorter(true);
		
		getContentPane().add(new JScrollPane(tabela));
		
		
		
		setVisible(true);
		
		
	}

	@Override
	public boolean proveraPogresno() {
		return KorisneFunkcije.loseLokalnoVreme(ftfDatum1.getText().trim()) || KorisneFunkcije.loseLokalnoVreme(ftfDatum2.getText().trim())
				|| ftfDatum1.getText().trim().equals("") || ftfDatum2.getText().trim().equals("");
	}

}
