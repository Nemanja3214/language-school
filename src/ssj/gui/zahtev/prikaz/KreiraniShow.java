package ssj.gui.zahtev.prikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ssj.model.osoba.Sekretar;
import ssj.model.zahtev.UObradi;
import ssj.model.zahtev.Zahtev;

public class KreiraniShow extends ZahteviShow{
	private static final long serialVersionUID = 3857545055699609391L;
	private JPanel pnlDugmad;
	private JButton btnPreuzmi;
	private JButton btnIzadji;
	public KreiraniShow(List<Zahtev> zahtevi, Sekretar s) {
		super(zahtevi);
		pnlDugmad = new JPanel();
		
		btnPreuzmi = new JButton("Preuzmi");
		btnPreuzmi.setEnabled(false);
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tabela.getSelectedRow() != -1)
				{
					btnPreuzmi.setEnabled(true);
				}
				else
				{
					btnPreuzmi.setEnabled(false);
				}
			}
		});
		btnPreuzmi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Zahtev z = zahtevi.get(redKonvertovano);
					z.setStanje(UObradi.getInstance());
					z.setSekretar(s);

					zm.getZahtevi().remove(z);
					zm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnPreuzmi);
		
		
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KreiraniShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	

}
