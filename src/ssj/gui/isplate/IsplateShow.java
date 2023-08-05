package ssj.gui.isplate;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ssj.managers.IsplataManager;
import ssj.model.Isplata;

public class IsplateShow extends JFrame{
	private static final long serialVersionUID = 439561343898076882L;
	private JTable tabela;
	private List<Isplata> isplate;
	private JPanel pnlDugmad;
	private JButton btnDodaj;
	private JButton btnIzadji;

	public IsplateShow() {
		setSize(600,400);
		setTitle("Prikaz isplate");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		isplate = IsplataManager.getInstance().getIsplate();
		IsplataModel im =new IsplataModel(isplate);
		tabela = new JTable(im);
		tabela.setAutoCreateRowSorter(true);
		
		getContentPane().add(new JScrollPane(tabela));
		
		
		
		pnlDugmad = new JPanel();
				
		btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IsplataCreate(IsplateShow.this);
				im.fireTableDataChanged();
			}
		});
		pnlDugmad.add(btnDodaj);
		
		btnIzadji = new JButton("Izadji");
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IsplateShow.this.setVisible(false);
			}
		});
		pnlDugmad.add(btnIzadji);
				
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);	
		
		setVisible(true);
	}	
	
}
