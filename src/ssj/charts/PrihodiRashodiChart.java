package ssj.charts;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import ssj.model.Isplata;
import ssj.model.Meseci;
import ssj.model.Racun;
import ssj.model.Transakcija;

public class PrihodiRashodiChart implements ExampleChart<PieChart>{
	List<Transakcija> transakcije;
	private Object[] posleDonje;
	private Meseci gornjaGranica;
	private HashMap<Meseci, Double> mapa;
	
	public PrihodiRashodiChart(List<Transakcija> transakcije, Object[] posleDonje, Meseci gornjaGranica) {
		this.transakcije = transakcije;
		this.posleDonje = posleDonje;
		this.gornjaGranica = gornjaGranica;
		
		mapa = new HashMap<Meseci, Double>();
		for(Object o:  posleDonje) {
			Meseci m = (Meseci) o;
			
			mapa.put(m, 0.0);
			if(m.equals(gornjaGranica))
				break;
			
		}
		
		for(Transakcija t: transakcije) {
			Meseci mesecNaplate = Meseci.values()[t.getVremeNaplate().getMonthValue()-1];
			double noviIznos = 0;
//			if(t instanceof Racun)
			noviIznos = mapa.get(mesecNaplate) + t.getIznos();
//			else if(t instanceof Isplata)
//				noviIznos = mapa.get(mesecNaplate) - t.getIznos();
			mapa.put(mesecNaplate,  noviIznos);
		}
		
	}

	@Override
	public PieChart getChart() {

	    PieChart chart = new PieChartBuilder().width(800).height(600).title("Statistika prihoda i rashoda").build();
	 
	    Color[] sliceColors = new Color[] { new Color(32, 133, 236), new Color(10, 65, 122), new Color(132, 100, 160), new Color(206, 169, 188),
	    		new Color(50, 50, 50), new Color(23, 60, 60), new Color(231, 12, 46), new Color(122, 123, 123), new Color(120,20,20), new Color(128, 244, 13), 
	    		new Color(60, 90, 255), new Color(147, 212, 230), new Color(70, 220, 70)};
	    chart.getStyler().setSeriesColors(sliceColors);
	    
	    for(Meseci m: mapa.keySet()) {
	    	if(mapa.get(m) < 0)
	    		chart.addSeries(m.naziv, 0);
	    	else
	    		chart.addSeries(m.naziv, mapa.get(m));
	    }
	 
	    return chart;
	}

	@Override
	public String getExampleChartName() {
		return "Grafikon ocena";
	}}
