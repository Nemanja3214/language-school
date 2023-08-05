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
import ssj.managers.TerminManager;
import ssj.managers.TestManager;
import ssj.model.Termin;

public class TerminManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaTermina = testiranjePath + "terminiTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
		TestManager.getInstance(testiranjePath + "testoviTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		TerminManager.reset();
		TestManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		TerminManager.getInstance(putanjaTermina);
		assertTrue(TerminManager.getInstance().getTermini().size() > 0, "Podaci su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		TerminManager.getInstance(putanjaTermina);
		Termin t = new Termin(534634, LocalDateTime.now(), TestManager.getInstance().getTestovi().get(0));
		TerminManager.getInstance().getTermini().add(t);
		TerminManager.getInstance().sacuvajPodatke(putanjaTermina);
		TerminManager.reset();
		TerminManager.getInstance(putanjaTermina);
		assertTrue(TerminManager.getInstance().getTermini().contains(t), "Termini su neuspešno sačuvani");
		
		TerminManager.getInstance(putanjaTermina).getTermini().remove(t);
		TerminManager.getInstance().sacuvajPodatke(putanjaTermina);
	}

	@Test
	public void testReset() {
		TerminManager.getInstance();
		TerminManager.reset();
		assertNull(TerminManager.getTm(), "Menadžer je neuspešno resetovan");
		
		TerminManager.getInstance(putanjaTermina);
		TerminManager.reset();
		assertNull(TerminManager.getTm(), "Menadžer je neuspešno resetovan");
	}
	
//	public static void main(String[] args) {
//		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
//		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
//		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
//		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
//		TestManager.getInstance(testiranjePath + "testoviTest.txt");
//		TerminManager.getInstance(putanjaTermina);
//		TerminManager.reset();
//		assertNull(TerminManager.getTm(), "Menadžer je uspešno resetovan");
//	}

}
