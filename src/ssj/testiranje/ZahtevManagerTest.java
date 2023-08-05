package ssj.testiranje;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import ssj.managers.ZahtevManager;
import ssj.model.zahtev.Kreiran;
import ssj.model.zahtev.Odbijen;
import ssj.model.zahtev.Prihvacen;
import ssj.model.zahtev.UObradi;
import ssj.model.zahtev.Zahtev;

public class ZahtevManagerTest {
	public static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaZahteva = testiranjePath + "zahteviTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		JezikManager.getInstance(testiranjePath + "jeziciTest.txt");
		JezikPredavacManager.getInstance(testiranjePath + "veze" + File.separator + "jezik_predavacTest.txt");
		KursManager.getInstance(testiranjePath + "kurseviTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ZahtevManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		OsobaManager.reset();
		JezikManager.reset();
	}

	@Test
	public void testUcitajPodatke() {
		ZahtevManager.getInstance().ucitajPodatke(putanjaZahteva);
		assertTrue(ZahtevManager.getInstance().getZahtevi().size() > 0 && ZahtevManager.getInstance().getZahteviMapa().keySet().size() > 0, 
				"Zahtevi su neuspešno učitani");
		
	}

	@Test
	public void testUzmiStanje() {
		assertEquals(ZahtevManager.uzmiStanje(1), Kreiran.getInstance());
		assertEquals(ZahtevManager.uzmiStanje(2), UObradi.getInstance());
		assertEquals(ZahtevManager.uzmiStanje(3), Odbijen.getInstance());
		assertEquals(ZahtevManager.uzmiStanje(4), Prihvacen.getInstance());
		assertNull(ZahtevManager.uzmiStanje(6));
	}

	@Test
	public void testIdStanja() {
		assertEquals(ZahtevManager.idStanja(Kreiran.getInstance()), 1);
		assertEquals(ZahtevManager.idStanja(UObradi.getInstance()), 2);
		assertEquals(ZahtevManager.idStanja(Odbijen.getInstance()), 3);
		assertEquals(ZahtevManager.idStanja(Prihvacen.getInstance()), 4);
	}

	@Test
	public void testSacuvajPodatke() {
		ZahtevManager.getInstance(putanjaZahteva);
		Zahtev z = new Zahtev(3423425, OsobaManager.getInstance().getUcenici().get(0), OsobaManager.getInstance().getSekretari().get(0),
				KursManager.getInstance().getKursevi().get(0), Kreiran.getInstance(), LocalDateTime.now());
		ZahtevManager.getInstance().getZahtevi().add(z);
		ZahtevManager.getInstance().sacuvajPodatke(putanjaZahteva);
		ZahtevManager.reset();
		ZahtevManager.getInstance(putanjaZahteva);
		assertTrue(ZahtevManager.getInstance().getZahtevi().contains(z), "Zahtevi su neuspešno sačuvani");
		
		ZahtevManager.getInstance(putanjaZahteva).getZahtevi().remove(0);
		ZahtevManager.getInstance().sacuvajPodatke(putanjaZahteva);
		
	}

	@Test
	public void testReset() {
		ZahtevManager.getInstance();
		ZahtevManager.reset();
		assertNull(ZahtevManager.getZm(), "Menadžer je neuspešno resetovan");
		
	}

}
