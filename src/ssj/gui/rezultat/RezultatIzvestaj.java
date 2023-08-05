package ssj.gui.rezultat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;

import net.miginfocom.swing.MigLayout;
import ssj.charts.OceneChart;
import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.utils.KorisneFunkcije;

public class RezultatIzvestaj extends JDialog{
	private static final long serialVersionUID = -2245659768659388094L;
	private JPanel pnlCentar;
	private JLabel lblOpste;
	private JLabel lblId;
	private JLabel lblUcenik;
	private JLabel lblBodovi;
	private JLabel lblOcena;
	private JPanel pnlDugmad;
	private JButton btnGrafickiPrikaz;
	private List<Rezultat> rezultati;
	private JLabel lblPolozilo;
	private JLabel lblNijePolozilo;

	public RezultatIzvestaj(JFrame roditelj, List<Rezultat> rezultati) {
		super(roditelj);
		this.rezultati = rezultati;
		setSize(1000,600);
		setTitle("Statistika odradjenog testa");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel(new MigLayout("wrap 4, align center",
				 "[]20[]20[]20[]",
				 "[]20[]"));
		
		Termin t = rezultati.get(0).getTermin();
		lblOpste = new JLabel("Test " + t.getTest().getNaziv() + ", radjen " + t.getVremeOdrzavanja().format(KorisneFunkcije.dtf1));
		pnlCentar.add(lblOpste, "wrap, span");
		
		lblId = new JLabel("ID");
		pnlCentar.add(lblId);
		
		lblUcenik = new JLabel("Učenik");
		pnlCentar.add(lblUcenik);
		
		lblBodovi = new JLabel("Bodovi");
		pnlCentar.add(lblBodovi);
		
		lblOcena = new JLabel("Ocena");
		pnlCentar.add(lblOcena);
		
		int polozilo = 0;
		
		for(Rezultat r: t.getRezultati()) {
			JLabel id = new JLabel(Integer.toString(r.getUcenik().getId()));
			JLabel ucenik = new JLabel(r.getUcenik().toString());
			JLabel bodovi = new JLabel(Double.toString(r.getBrojBodova()));
			JLabel ocena = new JLabel(Integer.toString(r.getOcena()));
			
			pnlCentar.add(id);
			pnlCentar.add(ucenik);
			pnlCentar.add(bodovi);
			pnlCentar.add(ocena);
			
			if(r.isPolozio())
				polozilo++;
		}
		
		lblPolozilo = new JLabel("Položilo: " + polozilo);
		pnlCentar.add(lblPolozilo, "span");
		
		lblNijePolozilo = new JLabel("Nije položilo: " + (rezultati.size() - polozilo));
		pnlCentar.add(lblNijePolozilo, "span");
		
		getContentPane().add(pnlCentar);
		
		
		pnlDugmad = new JPanel();
		
		btnGrafickiPrikaz = new JButton("Odnos ocena");
		btnGrafickiPrikaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	ExampleChart<PieChart> chart = new OceneChart(rezultati);
        			    PieChart pieChart = chart.getChart();
        			    new SwingWrapper<PieChart>(pieChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
				
			}
		});
		pnlDugmad.add(btnGrafickiPrikaz);
		
		
		getContentPane().add(pnlDugmad, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}
