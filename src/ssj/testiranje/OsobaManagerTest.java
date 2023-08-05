package ssj.testiranje;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.OsobaManager;
import ssj.model.osoba.Pol;
import ssj.model.osoba.Ucenik;
import ssj.model.osoba.Uloga;

public class OsobaManagerTest {
	private static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaOsobe = testiranjePath + "osobeTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		OsobaManager.reset();
	}

	@Test
	public void testReset() {
		OsobaManager.getInstance();
		OsobaManager.reset();
		assertNull(OsobaManager.getOm(),
				"Menadžer je neuspešno resetovan");
	}

	@Test
	public void testUcitajPodatke() {
		OsobaManager.getInstance().ucitajPodatke(putanjaOsobe);
		assertTrue(OsobaManager.getInstance().getOsobe().size() > 0 && OsobaManager.getInstance().getOsobeMapa().keySet().size() > 0,
				"Podaci su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		OsobaManager.getInstance().ucitajPodatke(putanjaOsobe);
		Ucenik u = new Ucenik(657567, "Ivan", "Ivanović", "ivica", "ivicasifra", Uloga.UCENIK, Pol.muski, LocalDate.now(), "Ivana Ivanovića 15", "4345346545", 0);
		OsobaManager.getInstance().getOsobe().add(u);
		OsobaManager.getInstance().sacuvajPodatke(putanjaOsobe);
		OsobaManager.reset();
		OsobaManager.getInstance().ucitajPodatke(putanjaOsobe);
		assertTrue(OsobaManager.getInstance().getOsobe().contains(u) && OsobaManager.getInstance().getUcenici().contains(u), 
				"Osobe su neuspešno sačuvane");
		
		OsobaManager.getInstance(putanjaOsobe).getOsobe().remove(u);
		OsobaManager.getInstance().sacuvajPodatke(putanjaOsobe);
	}

}
