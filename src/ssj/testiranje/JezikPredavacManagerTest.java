package ssj.testiranje;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.JezikManager;
import ssj.managers.JezikPredavacManager;
import ssj.managers.OsobaManager;
import ssj.model.Jezik;
import ssj.model.osoba.Predavac;

public class JezikPredavacManagerTest {
	public static String testiranjePath = JezikManagerTest.testiranjePath;
	private static String putanja =  testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		JezikPredavacManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		JezikPredavacManager.getInstance().ucitajPodatke(putanja);
		assertTrue(OsobaManager.getInstance().getPredavaciMapa().get(768678).getJezici().size() > 0);
	}

	@Test
	public void testSacuvajPodatke() {
		JezikPredavacManager.getInstance(putanja);
		Predavac p = OsobaManager.getInstance().getPredavaci().get(0);
		Jezik j = JezikManager.getInstance().getJezici().get(0);
		p.getJezici().add(j);
		j.getPredavaci().add(p);
		
		JezikPredavacManager.getInstance().sacuvajPodatke(putanja);
		JezikPredavacManager.reset();
		JezikPredavacManager.getInstance().ucitajPodatke(putanja);
		assertTrue(p.getJezici().contains(j), "Predavači i jezici su neuspešno spojeni");
		
		p.getJezici().remove(j);
		j.getPredavaci().remove(p);
		JezikPredavacManager.getInstance().sacuvajPodatke(putanja);
	}

	@Test
	public void testReset() {
		JezikPredavacManager.getInstance();
		JezikPredavacManager.reset();
		assertNull(JezikPredavacManager.getJpm(), "Menadžer je neuspešno resetovan");
	}

}
