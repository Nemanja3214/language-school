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

import ssj.controllers.JezikController;
import ssj.gui.zahtev.ZahtevModel;
import ssj.managers.JezikManager;
import ssj.managers.ZahtevManager;
import ssj.model.Jezik;
import ssj.model.osoba.Sekretar;
import ssj.model.zahtev.Zahtev;
import ssj.utils.KorisneFunkcije;
import ssj.utils.ProveraUnosa;

public class ZahteviIzvestaj extends JDialog implements ProveraUnosa{
	private static final long serialVersionUID = -8038147705117779336L;
	private JTable tabela;
	private List<Sekretar> sekretari;
	private JComboBox<Jezik> cmbJezik;
	private JPanel pnlGore;
	private JLabel lblOpsegDatuma;
	private JFormattedTextField ftfDatum1;
	private JFormattedTextField ftfDatum2;
	private List<Zahtev> zahtevi;
	private ZahtevModel zm;
	private JButton btnPretraga;
	
	public ZahteviIzvestaj(IzvestajiFrm izvestajiFrm) {
//		TODO
		super(izvestajiFrm, true);
		setSize(1000,400);
		setTitle("Izveštaj o zahtevima");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlGore = new JPanel();
		
		cmbJezik = new JComboBox<Jezik>();
		cmbJezik.addItem(null);
		for(Jezik j: JezikManager.getInstance().getJezici()) {
			cmbJezik.addItem(j);
		}
		pnlGore.add(cmbJezik);
		
		lblOpsegDatuma = new JLabel("Opseg datuma");
		pnlGore.add(lblOpsegDatuma);
		
		ftfDatum1 = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfDatum1.setColumns(20);
		ftfDatum1.setToolTipText("dd.MM.yyyy.");
		pnlGore.add(ftfDatum1, "wrap");
		
		ftfDatum2 = new JFormattedTextField(KorisneFunkcije.sdf);
		ftfDatum2.setColumns(20);
		ftfDatum2.setToolTipText("dd.MM.yyyy.");
		pnlGore.add(ftfDatum2, "wrap");
		
		
		btnPretraga = new JButton("Pretraga");
		pnlGore.add(btnPretraga);
		
		btnPretraga.addActionListener(new ActionListener() {
			private LocalDate datum1;
			private LocalDate datum2;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!proveraPogresno()) {
					
					zahtevi = new ArrayList<Zahtev>();

					datum1 = LocalDate.parse(ftfDatum1.getText().trim(), KorisneFunkcije.dtf);
					datum2 = LocalDate.parse(ftfDatum2.getText().trim(), KorisneFunkcije.dtf);
					for(Zahtev z: ZahtevManager.getInstance().getZahtevi()) {
						if(z.getVremeKreiranja().toLocalDate().isAfter(datum1) && z.getVremeKreiranja().toLocalDate().isBefore(datum2)) {
							zahtevi.add(z);
						}
					}
					
					if(cmbJezik.getSelectedIndex() != 0 && cmbJezik.getSelectedIndex() != -1) {
						List<Zahtev> zahteviZaBrisanje = new ArrayList<Zahtev>();
						Jezik j = (Jezik) cmbJezik.getSelectedItem();
						for(Zahtev z: zahtevi) {
							if(!z.getKurs().getJezik().equals(j))
								zahteviZaBrisanje.add(z);
						}
						for(Zahtev z: zahteviZaBrisanje) {
							zahtevi.remove(z);
						}
					}
					else {
						List<Zahtev> zahteviZaBrisanje = new ArrayList<Zahtev>();
						List<Jezik> najpopularniji = JezikController.najpopularnijiJezici(3);
						for(Zahtev z: zahtevi) {
							if(!najpopularniji.contains(z.getKurs().getJezik())) {
								zahteviZaBrisanje.add(z);
							}
						}
						for(Zahtev z: zahteviZaBrisanje) {
							zahtevi.remove(z);
						}
					}
					
					zm.setZahtevi(zahtevi);
					zm.fireTableDataChanged();
				}
			}
				
			
		});
		
		
		
		getContentPane().add(pnlGore, BorderLayout.NORTH);	
			
		zahtevi = ZahtevManager.getInstance().getZahtevi();
		zm = new ZahtevModel(zahtevi);
		tabela = new JTable(zm);
		
		getContentPane().add(new JScrollPane(tabela));
		
		setVisible(true);
	}

	@Override
	public boolean proveraPogresno() {
		return ftfDatum1.getText().equals("") || ftfDatum2.getText().equals("");
	}
	
	public static void main(String[] args) {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(4);
		lista.add(1, 6);
		System.out.println(lista.get(2));
	}
	
}
