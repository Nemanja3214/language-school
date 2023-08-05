package ssj.gui.izvestaji;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.demo.charts.ExampleChart;

import ssj.charts.PrihodiRashodiChart;
import ssj.gui.administrator.AdministratorFrm;
import ssj.managers.IsplataManager;
import ssj.managers.RacunManager;
import ssj.managers.TerminManager;
import ssj.model.Isplata;
import ssj.model.Meseci;
import ssj.model.Par;
import ssj.model.Racun;
import ssj.model.Rezultat;
import ssj.model.Termin;
import ssj.model.Transakcija;

public class IzvestajiFrm extends JDialog{
	private static final long serialVersionUID = -2807313343934121325L;
	private JButton btnPrihodi;
	private JPanel pnlCentar;
	private JButton btnTestoviPredavaca;
	private JButton btnZahtevi;
	private JButton btnUcenik;
	private JButton btnZahteviSekretara;
	private JButton btnIzvestajPrihodiRashodiChart;
	private JButton btnPrihodiRashodiPoDatumimaChart;
	private JButton btnKretanjeRezultata;
	
	public IzvestajiFrm(AdministratorFrm roditelj) {
		super(roditelj);
		
		setSize(600,400);
		setTitle("Izveštaji");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pnlCentar = new JPanel();
		
		btnPrihodi = new JButton("Izveštaj prihoda i rashoda");
		btnPrihodi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PrihodiRashodiIzvestaj(IzvestajiFrm.this);
			}
		});
		pnlCentar.add(btnPrihodi);
		
		btnTestoviPredavaca = new JButton("Izveštaj o testovima predavača");
		btnTestoviPredavaca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TestoviPredavacaIzvestaj(IzvestajiFrm.this);
			}
		});
		pnlCentar.add(btnTestoviPredavaca);
		
		btnZahteviSekretara = new JButton("Izveštaj o zahtevima sekretara");
		btnZahteviSekretara.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ZahteviSekretara(IzvestajiFrm.this);
			}
		});
		pnlCentar.add(btnZahteviSekretara);
		
		btnZahtevi = new JButton("Izveštaj o zahtevima");
		btnZahtevi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ZahteviIzvestaj(IzvestajiFrm.this);
			}
		});
		pnlCentar.add(btnZahtevi);
		
		btnUcenik = new JButton("Izveštaj o učenicima");
		btnUcenik.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UceniciKurseviIzvestaj(IzvestajiFrm.this);
			}
		});
		pnlCentar.add(btnUcenik);
		
		
		btnIzvestajPrihodiRashodiChart = new JButton("Grafički izveštaj o prihodima i rashodima ");
		btnIzvestajPrihodiRashodiChart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				Meseci donjaGranica = (Meseci)JOptionPane.showInputDialog(null, "Odaberite donju granicu: ", "Odabir meseca", 
						JOptionPane.PLAIN_MESSAGE, null, Meseci.values(), Meseci.JANUAR);
				
				
				Object[] posleDonje = new Object[12-donjaGranica.ordinal()];
				int i = 0;
				for(Meseci m: Meseci.values()){
					if(m.ordinal() >= donjaGranica.ordinal())
						posleDonje[i++] = m;
				}
				Meseci gornjaGranica = (Meseci)JOptionPane.showInputDialog(null, "Odaberite gornju granicu: ", "Odabir meseca",
						JOptionPane.PLAIN_MESSAGE, null, posleDonje, Meseci.JANUAR);
				
				
				
				
				
				List<Transakcija> isplate = new ArrayList<Transakcija>();
				for(Isplata isplata: IsplataManager.getInstance().getIsplate()) {
					if(isplata.getVremeNaplate().getMonthValue() >= (donjaGranica.ordinal() + 1) &&
							isplata.getVremeNaplate().getMonthValue() <= (gornjaGranica.ordinal() + 1) &&
							isplata.getVremeNaplate().getYear() == LocalDateTime.now().getYear()
							)
					{
						isplate.add(isplata);
					}
				}
				List<Transakcija> racuni = new ArrayList<Transakcija>();
				for(Racun r: RacunManager.getInstance().getRacuni()) {
					if(r.getVremeNaplate().getMonthValue() >= (donjaGranica.ordinal() + 1) &&
							r.getVremeNaplate().getMonthValue() <= (gornjaGranica.ordinal() + 1) &&
							r.getVremeNaplate().getYear() == LocalDateTime.now().getYear())
					{
						racuni.add(r);
					}
				}
				
				String[] opcije = {"Prihodi", "Rashodi"};
				JPanel panel = new JPanel();
		        panel.add(new JLabel("Odaberite da li želite da vidite grafikon prihoda ili rashoda"));
				int rezultat = JOptionPane.showOptionDialog(null, panel, "Enter a Number",
		                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                null, (Object[]) opcije, null);
				
				
				PieChart pieChart;
				if(rezultat == JOptionPane.YES_OPTION) {
					ExampleChart<PieChart> chart1 = new PrihodiRashodiChart(racuni, posleDonje, gornjaGranica);
	            	pieChart = chart1.getChart();
	            	pieChart.setTitle("Statistika prihoda");
				}
				else {
					ExampleChart<PieChart> chart2 = new PrihodiRashodiChart(isplate, posleDonje, gornjaGranica);
	            	pieChart = chart2.getChart();
	            	pieChart.setTitle("Statistika rashoda");
				}

				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
        			    new SwingWrapper<PieChart>(pieChart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
				}
				catch(NullPointerException ex) {
					
				}
				
			}
		});
		pnlCentar.add(btnIzvestajPrihodiRashodiChart);
		
		
		btnPrihodiRashodiPoDatumimaChart = new JButton("Prihodi i rashodi po datumima");
		btnPrihodiRashodiPoDatumimaChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<LocalDate, Double> prihodiPoDatumu = new HashMap<LocalDate, Double>();
				Map<LocalDate, Double> rashodiPoDatumu = new HashMap<LocalDate, Double>();
            	for(Racun r: RacunManager.getInstance().getRacuni()) {
            		LocalDate datum = r.getVremeNaplate().toLocalDate();
            		if(prihodiPoDatumu.get(datum) != null)
            			prihodiPoDatumu.put(datum, prihodiPoDatumu.get(datum) + r.getIznos());
            		else
            			prihodiPoDatumu.put(datum, r.getIznos());
            	}
            	for(Isplata i: IsplataManager.getInstance().getIsplate()) {
            		LocalDate datum = i.getVremeNaplate().toLocalDate();
            		if(rashodiPoDatumu.get(datum) != null)
            			rashodiPoDatumu.put(datum, rashodiPoDatumu.get(datum) + i.getIznos());
            		else
            			rashodiPoDatumu.put(datum, i.getIznos());
            	}
            	
            	List<Integer> xData = new ArrayList<Integer>();
            	List<Double> yData = new ArrayList<Double>();
            	List<Double> y1Data = new ArrayList<Double>();
            	
            	LocalDate ld = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            	while(ld.getYear() < (LocalDate.now().getYear()+1)) {
            		xData.add(ld.getDayOfYear());
            		if(prihodiPoDatumu.get(ld) == null)
            			yData.add(0.0);
            		else
            			yData.add(prihodiPoDatumu.get(ld));
            		
            		if(rashodiPoDatumu.get(ld) == null)
            			y1Data.add(0.0);
            		else
            			y1Data.add(rashodiPoDatumu.get(ld));
            		
            		ld = ld.plusDays(1);
            	}
				Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	XYChart chart = QuickChart.getChart("Prihodi/rashodi u zavisnosti od datuma", "Dan u godini", "Iznos", "Prhodi tog dana", xData, yData);
                    	chart.addSeries("Rashodi tog dana", xData, y1Data);
        			    new SwingWrapper<XYChart>(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
				
			}
		});
		pnlCentar.add(btnPrihodiRashodiPoDatumimaChart);
		
		
		btnKretanjeRezultata = new JButton("Kretanje rezultata testova");
		btnKretanjeRezultata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Map<LocalDate, Par> rezultatiPoDatumu = new HashMap<LocalDate, Par>();
				for(Termin t: TerminManager.getInstance().getTermini()) {
					LocalDate datum = t.getVremeOdrzavanja().toLocalDate();
					int suma = 0;
					for(Rezultat r: t.getRezultati()) {
						suma += r.getOcena();
					}
            		if(rezultatiPoDatumu.get(datum) != null)
            			rezultatiPoDatumu.put(datum, new Par((rezultatiPoDatumu.get(datum).getPrvi() + suma), t.getRezultati().size() ));
            		else
            			rezultatiPoDatumu.put(datum, new Par(suma, t.getRezultati().size()));
				}
				
				List<Integer> xData = new ArrayList<Integer>();
            	List<Double> yData = new ArrayList<Double>();
            	
            	LocalDate ld = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            	while(ld.getYear() < (LocalDate.now().getYear()+1)) {
            		xData.add(ld.getDayOfYear());
            		if(rezultatiPoDatumu.get(ld) == null || rezultatiPoDatumu.get(ld).getDrugi() == 0)
            			yData.add(0.0);
            		else
            			yData.add((double) (rezultatiPoDatumu.get(ld).getPrvi()/rezultatiPoDatumu.get(ld).getDrugi()));

            		
            		
            		ld = ld.plusDays(1);
            	}
            	Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	XYChart chart = QuickChart.getChart("Rezultati testova po datumima", "Dan u godini", "Ocena", "Prosečna ocena tog dana", xData, yData);
        			    new SwingWrapper<XYChart>(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                });
                t.start();
			}
		});
		pnlCentar.add(btnKretanjeRezultata);
		
		
		getContentPane().add(pnlCentar);
		
		
		setVisible(true);
		
	}
}
