package ssj.testiranje;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.JezikManager;
import ssj.managers.JezikPredavacManager;
import ssj.managers.KursManager;
import ssj.managers.OsobaManager;
import ssj.managers.RacunManager;
import ssj.managers.TerminManager;
import ssj.managers.TestManager;
import ssj.model.Racun;

public class RacunManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaRacuna = testiranjePath + "racuniTest.txt";

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
		RacunManager.reset();
		TerminManager.reset();
		TestManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		RacunManager.getInstance().ucitajPodatke(putanjaRacuna);
		assertTrue(RacunManager.getInstance().getRacuni().size() > 0 && RacunManager.getInstance().getRacuniMapa().keySet().size() > 0,
				"Računi su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		RacunManager.getInstance(putanjaRacuna);
		Racun r = new Racun(4345, LocalDateTime.now(), 10000, TerminManager.getInstance().getTermini().get(0), OsobaManager.getInstance().getUcenici().get(0));
		RacunManager.getInstance().getRacuni().add(r);
		RacunManager.getInstance().sacuvajPodatke(putanjaRacuna);
		RacunManager.reset();
		RacunManager.getInstance(putanjaRacuna);
		assertTrue(RacunManager.getInstance().getRacuni().contains(r), "Računi su neuspešno sačuvani");
		
		RacunManager.getInstance(putanjaRacuna).getRacuni().remove(r);
		RacunManager.getInstance().sacuvajPodatke(putanjaRacuna);
		
		
	}

	@Test
	public void testReset() {
		RacunManager.getInstance();
		RacunManager.reset();
		assertNull(RacunManager.getRm(), "Menadžer je neuspešno resetovan");
	}

}
