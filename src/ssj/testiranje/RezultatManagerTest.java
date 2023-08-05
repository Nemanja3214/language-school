package ssj.testiranje;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.JezikManager;
import ssj.managers.JezikPredavacManager;
import ssj.managers.KursManager;
import ssj.managers.OsobaManager;
import ssj.managers.RezultatManager;
import ssj.managers.TerminManager;
import ssj.managers.TestManager;
import ssj.model.Rezultat;

public class RezultatManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaRezultata = testiranjePath + "rezultatiTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
		TestManager.getInstance(testiranjePath + "testoviTest.txt");
		TerminManager.getInstance(testiranjePath + "terminiTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RezultatManager.reset();
		TerminManager.reset();
		TestManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		RezultatManager.getInstance().ucitajPodatke(putanjaRezultata);
		assertTrue(RezultatManager.getInstance().getRezultati().size() > 0 && RezultatManager.getInstance().getRezultatiMapa().keySet().size() > 0, 
				"Rezultati su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		RezultatManager.getInstance(putanjaRezultata);
		Rezultat r = new Rezultat(435212, 60, 2, true, TerminManager.getInstance().getTermini().get(0), OsobaManager.getInstance().getUcenici().get(0));
		RezultatManager.getInstance().getRezultati().add(r);
		RezultatManager.getInstance().sacuvajPodatke(putanjaRezultata);
		RezultatManager.reset();
		RezultatManager.getInstance(putanjaRezultata);
		assertTrue(RezultatManager.getInstance().getRezultati().contains(r), "Rezultati su neuspešno učitani");
		
		RezultatManager.getInstance(putanjaRezultata).getRezultati().remove(r);
		RezultatManager.getInstance().sacuvajPodatke(putanjaRezultata);
	}

	@Test
	public void testReset() {
		RezultatManager.getInstance();
		RezultatManager.reset();
		assertNull(RezultatManager.getRm(), "Menadžer je neuspešno resetovan");
		
	}

}
