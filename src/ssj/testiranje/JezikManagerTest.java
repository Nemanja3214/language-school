package ssj.testiranje;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.JezikManager;
import ssj.model.Jezik;

public class JezikManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaJezika = testiranjePath + "jeziciTest.txt";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		JezikManager.reset();
	}

	@Test
	public void testReset() {
		JezikManager.getInstance();
		JezikManager.reset();
		assertNull(JezikManager.getJm(),
				"Menadžer je neuspešno resetovan");
	}

	@Test
	public void testUcitajPodatke() {
		JezikManager.getInstance().ucitajPodatke(putanjaJezika);
		assertTrue(JezikManager.getInstance().getJezici().size() > 0 && JezikManager.getInstance().getJeziciMapa().keySet().size() > 0,
				"Podaci su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		JezikManager.getInstance().ucitajPodatke(putanjaJezika);
		Jezik j = new Jezik(434521, "bugarski");
		JezikManager.getInstance().getJezici().add(j);
		JezikManager.getInstance().sacuvajPodatke(putanjaJezika);
		JezikManager.reset();
		JezikManager.getInstance().ucitajPodatke(putanjaJezika);
		assertTrue(JezikManager.getInstance().getJezici().contains(j), "Jezici su neuspešno sačuvani");
		
		JezikManager.getInstance(putanjaJezika).getJezici().remove(j);
		JezikManager.getInstance().sacuvajPodatke(putanjaJezika);
	}

}
