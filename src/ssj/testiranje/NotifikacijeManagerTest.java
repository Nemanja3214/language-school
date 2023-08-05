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
import ssj.managers.NotifikacijeManager;
import ssj.managers.OsobaManager;
import ssj.model.Kurs;
import ssj.notifikacije.Notifikacija;

public class NotifikacijeManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaNotifikacija = testiranjePath + "notifikacijeTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		NotifikacijeManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		NotifikacijeManager.getInstance();
		NotifikacijeManager.getInstance().ucitajPodatke(putanjaNotifikacija);
		assertTrue(NotifikacijeManager.getInstance().getNotifikacije().size() > 0 && NotifikacijeManager.getInstance().getNotifikacijeMapa().keySet().size() > 0, 
				"Podaci su neuspešno učitani");
		
	}

	@Test
	public void testSacuvajPodatke() {
		NotifikacijeManager.getInstance(putanjaNotifikacija);
		Kurs k = KursManager.getInstance().getKursevi().get(0);
		Notifikacija n = new Notifikacija(5646, "Naslov", "Neki tekst", k);
		NotifikacijeManager.getInstance().getNotifikacije().add(n);
		NotifikacijeManager.getInstance().sacuvajPodatke(putanjaNotifikacija);
		NotifikacijeManager.reset();
		NotifikacijeManager.getInstance(putanjaNotifikacija);
		assertTrue(NotifikacijeManager.getInstance().getNotifikacije().contains(n), "Podaci su neuspešno sačuvani");
	
		NotifikacijeManager.getInstance(putanjaNotifikacija).getNotifikacije().remove(n);
		NotifikacijeManager.getInstance().sacuvajPodatke(putanjaNotifikacija);
	}

	@Test
	public void testReset() {
		NotifikacijeManager.getInstance();
		NotifikacijeManager.reset();
		assertNull(NotifikacijeManager.getNm(), "Menadžer je neuspešno resetovan");
	}
	
//	public static void main(String[] args) {
//		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
//		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
//		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
//		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
//		NotifikacijeManager.getInstance(putanjaNotifikacija);
//		Kurs k = KursManager.getInstance().getKursevi().get(0);
//		Notifikacija n = new Notifikacija(5646, "Naslov", "Neki tekst", k);
//		NotifikacijeManager.getInstance().getNotifikacije().add(n);
//		NotifikacijeManager.getInstance().sacuvajPodatke(putanjaNotifikacija);
//		NotifikacijeManager.reset();
//		NotifikacijeManager.getInstance(putanjaNotifikacija);
//		assertTrue(NotifikacijeManager.getInstance().getNotifikacije().contains(n), "Podaci su uspešno sačuvani");
//	
//		NotifikacijeManager.getInstance(putanjaNotifikacija).getNotifikacije().remove(n);
//		NotifikacijeManager.getInstance().sacuvajPodatke(putanjaNotifikacija);
//	}

}
