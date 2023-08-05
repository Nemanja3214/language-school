package ssj.testiranje;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.CenovnikManager;
import ssj.managers.JezikManager;
import ssj.managers.JezikPredavacManager;
import ssj.managers.KursManager;
import ssj.managers.OsobaManager;
import ssj.model.Cenovnik;

public class CenovnikManagerTest {
	private static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaCenovnika = testiranjePath + "cenovniciTest.txt";
	private static String putanjaCena = testiranjePath + "cene_i_popustiTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		CenovnikManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		CenovnikManager.getInstance().ucitajPodatke(putanjaCenovnika, putanjaCena);
		assertTrue(CenovnikManager.getInstance().getCenovnici().size() > 0 && CenovnikManager.getInstance().getCenovniciMapa().keySet().size() > 0,
				"Cenovnici su uspešno učitani");
	}
	
	@Test
	public void reset() {
		CenovnikManager.getInstance();
		CenovnikManager.reset();
		assertNull(CenovnikManager.getCm(), "Menadžer je neuspešno resetovan");
	}

	@Test
	public void testSacuvajPodatke() {
		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena);
		Cenovnik c = new Cenovnik(986743, LocalDate.of(2021, 5, 5), LocalDate.of(2022, 5, 5));
		CenovnikManager.getInstance().getCenovnici().add(c);
		CenovnikManager.getInstance().sacuvajPodatke(putanjaCenovnika, putanjaCena);
		CenovnikManager.reset();
		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena);
		assertTrue(CenovnikManager.getInstance().getCenovnici().contains(c), "Računi su neuspešno sačuvani");
		
		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena).getCenovnici().remove(c);
		CenovnikManager.getInstance().sacuvajPodatke(putanjaCenovnika, putanjaCena);
		
	}

	@Test
	public void testAzurirajTrenutni() {
		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena);
		Cenovnik c = new Cenovnik(986743, LocalDate.of(2021, 5, 5), LocalDate.of(2022, 5, 5));
		CenovnikManager.getInstance().getCenovnici().add(c);
		CenovnikManager.getInstance().azurirajTrenutni();
		assertEquals(Cenovnik.trenutni, c, "Ažuriranje nije uspelo");
		
		CenovnikManager.getInstance().getCenovnici().remove(c);
		CenovnikManager.getInstance().sacuvajPodatke(putanjaCenovnika, putanjaCena);
	}
	
//	public static void main(String[] args) {
//		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
//		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
//		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
//		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
//		
//		CenovnikManager.getInstance().ucitajPodatke(putanjaCenovnika, putanjaCena);
//		assertTrue(CenovnikManager.getInstance().getCenovnici().size() > 0 && CenovnikManager.getInstance().getCenovniciMapa().keySet().size() > 0,
//				"Cenovnici su uspešno učitani");
//		
//		CenovnikManager.reset();
//		JezikPredavacManager.reset();
//		KursManager.reset();
//		OsobaManager.reset();
//		JezikManager.reset();
//		
//		
//		
//		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
//		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
//		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
//		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
//		
//		CenovnikManager.getInstance();
//		CenovnikManager.reset();
//		assertNull(CenovnikManager.getCm(), "Menadžer je neuspešno resetovan");
//		
//		CenovnikManager.reset();
//		JezikPredavacManager.reset();
//		KursManager.reset();
//		OsobaManager.reset();
//		JezikManager.reset();
//		
//		
//		
//		
//		
//		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
//		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
//		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
//		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
//		
//		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena);
//		Cenovnik c = new Cenovnik(986743, LocalDate.of(2021, 5, 5), LocalDate.of(2022, 5, 5));
//		CenovnikManager.getInstance().getCenovnici().add(c);
//		CenovnikManager.getInstance().sacuvajPodatke(putanjaCenovnika, putanjaCena);
//		CenovnikManager.reset();
//		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena);
//		assertTrue(CenovnikManager.getInstance().getCenovnici().contains(c), "Računi su neuspešno sačuvani");
//		
//		CenovnikManager.getInstance(putanjaCenovnika, putanjaCena).getCenovnici().remove(c);
//		CenovnikManager.getInstance().sacuvajPodatke(putanjaCenovnika, putanjaCena);
//		
//		CenovnikManager.reset();
//		JezikPredavacManager.reset();
//		KursManager.reset();
//		OsobaManager.reset();
//		JezikManager.reset();
//		
//		
//	}

}
