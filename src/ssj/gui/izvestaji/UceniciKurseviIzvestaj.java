package ssj.gui.izvestaji;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;

import ssj.charts.IznosiChart;
import ssj.gui.izvestaji.modeli.UceniciKurseviModel;
import ssj.managers.OsobaManager;
import ssj.model.osoba.Ucenik;

public class UceniciKurseviIzvestaj extends JDialog{
	private static final long serialVersionUID = 1113786780699989143L;
	private JPanel pnlCentar;
	private JTable tabela;
	private List<Ucenik> ucenici;
	private UceniciKurseviModel ukim;
	private JPanel pnlDugmad;
	private JButton btnGrafik;


	public UceniciKurseviIzvestaj(IzvestajiFrm roditelj) {
		super(roditelj);
		
		setSize(600,400);
		setTitle("Izveštaj o prihodima i rashodima");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		ucenici = OsobaManager.getInstance().getUcenici();
		ukim = new UceniciKurseviModel(ucenici);
		tabela = new JTable(ukim);
		
		
		getContentPane().add(new JScrollPane(tabela));
		
		
		pnlDugmad = new JPanel();
		
		btnGrafik = new JButton("Grafik cena");
		btnGrafik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	List<Ucenik> ucenici = new ArrayList<Ucenik>();
                    	ExampleChart<CategoryChart> chart = new IznosiChart(OsobaManager.getInstance().getUcenici());
                    	CategoryChart stickChart = chart.getChart();
        			    new SwingWrapper<CategoryChart>(stickChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
			}
		});
		pnlDugmad.add(btnGrafik);
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		
		setVisible(true);
		
	}
}
