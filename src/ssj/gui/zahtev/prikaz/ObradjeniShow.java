package ssj.gui.zahtev.prikaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ssj.managers.PohadjanjeManager;
import ssj.model.Pohadjanje;
import ssj.model.zahtev.Odbijen;
import ssj.model.zahtev.Prihvacen;
import ssj.model.zahtev.Zahtev;
import ssj.utils.KorisneFunkcije;

public class ObradjeniShow extends ZahteviShow{
	private static final long serialVersionUID = 6583418570894653290L;
	private JPanel pnlDugmad;
	private JButton btnIzadji;
	private JButton btnOdobri;
	private JButton btnOdbij;
	public ObradjeniShow(List<Zahtev> zahtevi) {
		super(zahtevi);
		pnlDugmad = new JPanel();
		
		btnOdobri = new JButton("Odobri");
		btnOdobri.setEnabled(false);
		btnOdobri.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Zahtev z = zahtevi.get(redKonvertovano);
					z.setStanje(Prihvacen.getInstance());
					
					Pohadjanje p = new Pohadjanje();
					p.setId(KorisneFunkcije.generisiId(PohadjanjeManager.getInstance().getPohadjanjaMapa()));
					p.setKurs(z.getKurs());
					p.setPolozio(false);
					p.setUcenik(z.getUcenik());
					p.setVremeUpisa(LocalDateTime.now());
					
					PohadjanjeManager.getInstance().getPohadjanja().add(p);
					PohadjanjeManager.getInstance().getPohadjanjaMapa().put(p.getId(), p);
					
					p.getUcenik().getPohadjanja().add(p);
					p.getKurs().getPohadjanja().add(p);
					
					zm.getZahtevi().remove(z);
					zm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnOdobri);
		
		
		btnOdbij = new JButton("Odbij");
		btnOdbij.setEnabled(false);
		btnOdbij.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int redIndeks = tabela.getSelectedRow();
				if(redIndeks != -1) {
					int redKonvertovano = tabela.convertRowIndexToModel(redIndeks);
					Zahtev z = zahtevi.get(redKonvertovano);
					z.setStanje(Odbijen.getInstance());
					zahtevi.remove(z);
					zm.fireTableDataChanged();
				}
			}
		});
		pnlDugmad.add(btnOdbij);
		
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tabela.getSelectedRow() != -1)
				{
					btnOdobri.setEnabled(true);
					btnOdbij.setEnabled(true);
				}
				else
				{
					btnOdobri.setEnabled(false);
					btnOdbij.setEnabled(false);
				}
			}
		});
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ObradjeniShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
