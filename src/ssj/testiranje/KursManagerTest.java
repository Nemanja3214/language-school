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
import ssj.model.Jezik;
import ssj.model.Kurs;

public class KursManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaKursa = testiranjePath + "kurseviTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		KursManager.getInstance().ucitajPodatke(putanjaKursa);
		assertTrue(KursManager.getInstance().getKursevi().size() > 0 && KursManager.getInstance().getKurseviMapa().keySet().size() > 0, 
				"Kursevi su neuspešno učitani");	}

	@Test
	public void testSacuvajPodatke() {
		KursManager.getInstance(putanjaKursa);
		Jezik j = JezikManager.getInstance().getJezici().get(0);
		Kurs k = new Kurs(7867111, "srednji kurs", j, j.getPredavaci().get(0));
		KursManager.getInstance().getKursevi().add(k);
		KursManager.getInstance().sacuvajPodatke(putanjaKursa);
		KursManager.reset();
		KursManager.getInstance().ucitajPodatke(putanjaKursa);
		assertTrue(KursManager.getInstance().getKursevi().contains(k), "Kursevi su neuspešno sačuvani");
		
		KursManager.getInstance().getKursevi().remove(k);
		KursManager.getInstance().sacuvajPodatke(putanjaKursa);
	}

	@Test
	public void testReset() {
		KursManager.getInstance();
		KursManager.reset();
		assertNull(KursManager.getKm(), "Menadžer je neuspešno resetovan");
	}
//	public static void main(String[] args) {
//		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
//		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
//		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
//		KursManager.getInstance().ucitajPodatke(putanjaKursa);
//		assertTrue(KursManager.getInstance().getKursevi().size() > 0 && KursManager.getInstance().getKurseviMapa().keySet().size() > 0, 
//				"Kursevi su uspešno učitani");	
//	}

}
