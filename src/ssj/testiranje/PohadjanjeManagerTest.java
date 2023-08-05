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
import ssj.managers.PohadjanjeManager;
import ssj.model.Pohadjanje;

public class PohadjanjeManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaPohadjanja = testiranjePath + "pohadjanjaTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PohadjanjeManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		PohadjanjeManager.getInstance().ucitajPodatke(putanjaPohadjanja);
		assertTrue(PohadjanjeManager.getInstance().getPohadjanja().size() > 0 && PohadjanjeManager.getInstance().getPohadjanjaMapa().keySet().size() > 0, 
				"Podaci su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		PohadjanjeManager.getInstance(putanjaPohadjanja);
		Pohadjanje p = new Pohadjanje(65723, LocalDateTime.now(), false, KursManager.getInstance().getKursevi().get(0),
				OsobaManager.getInstance().getUcenici().get(0));
		PohadjanjeManager.getInstance().getPohadjanja().add(p);
		PohadjanjeManager.getInstance().sacuvajPodatke(putanjaPohadjanja);
		PohadjanjeManager.reset();
		PohadjanjeManager.getInstance(putanjaPohadjanja);
		assertTrue(PohadjanjeManager.getInstance().getPohadjanja().contains(p), "Pohadjanja su neuspešno sačuvana");
		
		PohadjanjeManager.getInstance(putanjaPohadjanja).getPohadjanja().remove(p);
		PohadjanjeManager.getInstance().sacuvajPodatke(putanjaPohadjanja);
		
	}

	@Test
	public void testReset() {
		PohadjanjeManager.getInstance();
		PohadjanjeManager.reset();
		assertNull(PohadjanjeManager.getPm(), "Menadžer je neuspešno resetovan");
	}

}
