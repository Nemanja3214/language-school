package ssj.gui.izvestaji;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ssj.gui.izvestaji.modeli.ZahteviSekretaraModel;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Sekretar;

public class ZahteviSekretara extends JDialog{
	private static final long serialVersionUID = -8038147705117779336L;
	private JTable tabela;
	private List<Sekretar> sekretari;
	
	public ZahteviSekretara(IzvestajiFrm izvestajiFrm) {
		super(izvestajiFrm, true);
		setSize(600,400);
		setTitle("Sekretari i broj obradjenih zahteva");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		sekretari = OsobaManager.getInstance().getSekretari();
		ZahteviSekretaraModel zsm = new ZahteviSekretaraModel(sekretari);
		tabela = new JTable(zsm);
		
		getContentPane().add(new JScrollPane(tabela));
		
		setVisible(true);
	}
	
}
