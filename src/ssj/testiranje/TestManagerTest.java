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
import ssj.managers.PohadjanjeManager;
import ssj.managers.TestManager;

public class TestManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaTestova = testiranjePath + "testoviTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TestManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		TestManager.getInstance().ucitajPodatke(putanjaTestova);
		assertTrue(TestManager.getInstance().getTestovi().size() > 0 && TestManager.getInstance().getTestoviMapa().keySet().size() > 0,
				"Podaci su neuspešno učitani");
		
	}

	@Test
	public void testSacuvajPodatke() {
		TestManager.getInstance(putanjaTestova);
		ssj.model.Test t = new ssj.model.Test(543232, "Neki test", "Neki opis", KursManager.getInstance().getKursevi().get(0));
		TestManager.getInstance().getTestovi().add(t);
		TestManager.getInstance().sacuvajPodatke(putanjaTestova);
		TestManager.reset();
		TestManager.getInstance(putanjaTestova);
		assertTrue(TestManager.getInstance().getTestovi().contains(t), "Testovi su neuspešno sačuvani");
		
		TestManager.getInstance(putanjaTestova).getTestovi().remove(t);
		TestManager.getInstance().sacuvajPodatke(putanjaTestova);
		
		
	}

	@Test
	public void testReset() {
		TestManager.getInstance();
		TestManager.reset();
		assertNull(TestManager.getTm(), "Menadžer je nespešno resetovan");
		
	}

}
