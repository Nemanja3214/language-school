package ssj.gui.zahtev.prikaz;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableRowSorter;

import ssj.gui.kurs.KursModel;
import ssj.gui.zahtev.ZahtevModel;
import ssj.model.Kurs;
import ssj.model.zahtev.Zahtev;

public class ZahteviShow extends JFrame{
	private static final long serialVersionUID = -3283584434911102940L;
	protected JTable tabela;
	protected ZahtevModel zm;
	protected JPanel pnlCentar;

	public ZahteviShow(List<Zahtev> zahtevi) {
		setSize(600,400);
		setTitle("Prikaz zahteva");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		zm = new ZahtevModel(zahtevi);
		tabela = new JTable(zm);
		RowFilter<ZahtevModel,Integer> neobrisanFilter = new RowFilter<ZahtevModel,Integer>() {
			 
			   public boolean include(Entry<? extends ZahtevModel, ? extends Integer> entry) {
				   ZahtevModel zm = entry.getModel();
			     Zahtev z = zm.getZahtevi().get(entry.getIdentifier());
			     return !z.isObrisan();
			   }
			 };
		tabela = new JTable(zm);
		TableRowSorter<ZahtevModel> sorter = new TableRowSorter<ZahtevModel>(zm);
		 sorter.setRowFilter(neobrisanFilter);
		tabela.setRowSorter(sorter);
		
		getContentPane().add(new JScrollPane(tabela));
		
		setVisible(true);
	}
}
