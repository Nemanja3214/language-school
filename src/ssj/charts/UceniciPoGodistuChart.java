package ssj.charts;

import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import ssj.managers.JezikManager;
import ssj.model.Jezik;
import ssj.model.Kurs;
import ssj.model.Pohadjanje;
import ssj.model.osoba.Predavac;

public class UceniciPoGodistuChart implements ExampleChart<PieChart>{

	private HashMap<Jezik, Integer> mapa;

	public UceniciPoGodistuChart(Predavac predavac, int donjaGranica, int gornjaGranica) {
		mapa = new HashMap<Jezik, Integer>();
		mapa.put(JezikManager.getInstance().getJezici().get(0), 0);
		for(Jezik j: predavac.getJezici()) {
			mapa.put(j, 0);
		}
		for(Kurs k: predavac.getKursevi()) {
			for(Pohadjanje p: k.getPohadjanja()) {
			    long brojGodinaUcenika = Math.abs(ChronoUnit.YEARS.between(LocalDate.now(), p.getUcenik().getDatumRodjenja()));
			    if(brojGodinaUcenika > donjaGranica && brojGodinaUcenika < gornjaGranica) {
			    	mapa.put(k.getJezik(), mapa.get(k.getJezik()) + 1);
			    }
			    	
			}
		}
	}

	@Override
	public PieChart getChart() {
	    PieChart chart = new PieChartBuilder().width(800).height(600).title("Statistika broja učenika po jezicima").build();
	 
	    Color[] sliceColors = new Color[] { new Color(32, 133, 236), new Color(10, 65, 122), new Color(132, 100, 160), new Color(206, 169, 188), new Color(50, 50, 50) , new Color(67, 78, 89)};
	    chart.getStyler().setSeriesColors(sliceColors);

	    
	    for(Jezik j: mapa.keySet()) {
	    	chart.addSeries(j.getNaziv(), mapa.get(j));
	    }
	 
	    return chart;
	}

	@Override
	public String getExampleChartName() {
		return "Statistika broja učenika po jezicima";
	}
}
