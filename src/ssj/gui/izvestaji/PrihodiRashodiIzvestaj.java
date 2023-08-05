package ssj.gui.izvestaji;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ssj.managers.IsplataManager;
import ssj.managers.RacunManager;
import ssj.model.Isplata;
import ssj.model.Racun;
import ssj.model.Transakcija;
import transakcije.TransakcijeModel;

public class PrihodiRashodiIzvestaj extends JDialog{
	private static final long serialVersionUID = 1113786780699989143L;
	private JPanel pnlCentar;
	private JTable tabela;


	public PrihodiRashodiIzvestaj(IzvestajiFrm roditelj) {
		super(roditelj, true);
		
		setSize(600,400);
		setTitle("Izveštaj o prihodima i rashodima");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		List<Transakcija> transakcije = new ArrayList<Transakcija>();
		
		for(Racun r: RacunManager.getInstance().getRacuni()) {
			transakcije.add(r);
		}
		for(Isplata i :  IsplataManager.getInstance().getIsplate()) {
			transakcije.add(i);
		}
		TransakcijeModel tm = new TransakcijeModel(transakcije);
		tabela = new JTable(tm);
		tabela.setAutoCreateRowSorter(true);
		
		getContentPane().add(new JScrollPane(tabela));
		
		
		setVisible(true);
		
	}

}
