package ssj.charts;

import java.awt.Color;
import java.util.List;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.utils.KorisneFunkcije;

public class OceneChart implements ExampleChart<PieChart>{
	List<Rezultat> rezultati;
	private Termin termin;
	
	public OceneChart(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}

	@Override
	public PieChart getChart() {
		if(rezultati.size() > 0) {
			this.termin = rezultati.get(0).getTermin();
		}
	    PieChart chart = new PieChartBuilder().width(800).height(600).title("Test "+ this.termin.getTest().getNaziv() + ", radjen " + this.termin.getVremeOdrzavanja().format(KorisneFunkcije.dtf1)).build();
	 
	    Color[] sliceColors = new Color[] { new Color(32, 133, 236), new Color(10, 65, 122), new Color(132, 100, 160), new Color(206, 169, 188), new Color(50, 50, 50) };
	    chart.getStyler().setSeriesColors(sliceColors);
	    
	    int[] brojOcena = new int[5];
	    for(Rezultat r: rezultati) {
	    	brojOcena[r.getOcena() - 1] ++;
	    }
	 
	    chart.addSeries("5", brojOcena[4]);
	    chart.addSeries("4", brojOcena[3]);
	    chart.addSeries("3", brojOcena[2]);
	    chart.addSeries("2", brojOcena[1]);
	    chart.addSeries("1", brojOcena[0]);
	 
	    return chart;
	}

	@Override
	public String getExampleChartName() {
		return "Grafikon ocena";
	}

}
