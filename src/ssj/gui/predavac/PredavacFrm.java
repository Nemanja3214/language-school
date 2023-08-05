package ssj.gui.predavac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;

import ssj.charts.UceniciPoGodistuChart;
import ssj.controllers.KursController;
import ssj.gui.LoginFrm;
import ssj.gui.notifikacija.NotifikacijaCreate;
import ssj.managers.Cuvanje;
import ssj.model.osoba.Predavac;
import ssj.utils.KorisneFunkcije;

public class PredavacFrm extends JFrame{

	private Predavac p;
	private JButton btnKursevi;
	private JPanel pnlCentar;
	private JButton btnObavestenje;
	private JButton btnStatistikaPoGodinama;

	public PredavacFrm(Predavac p) {

		this.p = p;
		setSize(600,400);
		setTitle("Predavačka forma");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnlCentar = new JPanel();
		
		btnKursevi = new JButton("Kursevi");
		btnKursevi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KursController.prikazPredavacKursevi(p);
			}
		});
		pnlCentar.add(btnKursevi);
		
		btnObavestenje = new JButton("Napravite obaveštenje");
		btnObavestenje.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NotifikacijaCreate(PredavacFrm.this, p);
			}
		});
		pnlCentar.add(btnObavestenje);
		
		
		btnStatistikaPoGodinama = new JButton("Statistika po godinama");
		btnStatistikaPoGodinama.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String rezultat = JOptionPane.showInputDialog("Unesite donju granicu godina: ");
				if(!KorisneFunkcije.isPositiveInt(rezultat) || rezultat.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Pogrešan unos podataka, broj godina mora biti broj");
				}
				else {
					final int donjaGranica = Integer.parseInt(rezultat);
					rezultat = JOptionPane.showInputDialog("Unesite gornju granicu godina: ");
					if(!KorisneFunkcije.isPositiveInt(rezultat) || rezultat.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Pogrešan unos podataka, broj godina mora biti broj");
						
					}
					else {
						final int gornjaGranica = Integer.parseInt(rezultat);
						Thread t = new Thread(new Runnable() {
		                    @Override
		                    public void run() {
		                    	ExampleChart<PieChart> chart = new UceniciPoGodistuChart(p, donjaGranica, gornjaGranica);
		        			    PieChart pieChart = chart.getChart();
		        			    new SwingWrapper<PieChart>(pieChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		                    }
		                });
		                t.start();
					}
						
				}
				
				
			}
		});
		pnlCentar.add(btnStatistikaPoGodinama);
		
		getContentPane().add(pnlCentar);
		
		setVisible(true);
	}
	@Override
	public void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING) {
			Cuvanje.sacuvaj();
			dispose();
			new LoginFrm();
		}
	}
}
