package ssj.charts;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;

import ssj.model.osoba.Ucenik;

public class IznosiChart implements ExampleChart<CategoryChart> {
	private List<Ucenik> ucenici;

	public IznosiChart(List<Ucenik> ucenici) {
		this.ucenici = ucenici;
	}
	
	  @Override
	  public CategoryChart getChart() {
	 
	    // Create Chart
	    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Grafikon ukupnog iznosa plaćanja učenika").build();
	 
	    // Customize Chart
	    chart.getStyler().setChartTitleVisible(true);
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Stick);
	 
	    // Series
	    List<String> xData = new ArrayList<String>();
	    List<Integer> yData = new ArrayList<Integer>();
	    for(Ucenik u: ucenici) {
	    	xData.add(u.getIme() + " " + u.getPrezime());
	    	yData.add((int)u.getUkupanIznos());
	    }
	    chart.addSeries("iznos", xData, yData);
	 
	    return chart;
	  }

	@Override
	public String getExampleChartName() {
		return "Grafikon ukupnog iznosa plaćanja učenika";
	}
}
