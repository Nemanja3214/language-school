package ssj.testiranje;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.IsplataManager;
import ssj.managers.OsobaManager;
import ssj.model.Isplata;

public class IsplataManagerTest {
	private static String testiranjePath = "data" + File.separator + "testiranje" + File.separator;
	private static String putanjaIsplate = testiranjePath + "isplateTest.txt";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ssj.managers.OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		IsplataManager.reset();
		ssj.managers.OsobaManager.reset();
	}
	

	@Test
	public void testUcitajPodatke() {
		IsplataManager.getInstance().ucitajPodatke(putanjaIsplate);
		assertTrue(IsplataManager.getInstance().getIsplate().size() > 0 && IsplataManager.getInstance().getIsplateMapa().keySet().size() > 0,
				"Podaci su neuspešno učitani");
	}

	@Test
	public void testSacuvajPodatke() {
		IsplataManager.getInstance().ucitajPodatke(putanjaIsplate);
		Isplata i = new Isplata(765453, LocalDateTime.now(), 40000, OsobaManager.getInstance().getZaposleni().get(0));
		IsplataManager.getInstance().getIsplate().add(i);
		IsplataManager.getInstance().sacuvajPodatke(putanjaIsplate);
		IsplataManager.reset();
		IsplataManager.getInstance().ucitajPodatke(putanjaIsplate);
		assertTrue(IsplataManager.getInstance().getIsplate().contains(i), "Isplate su neuspešno sačuvane");
		
		IsplataManager.getInstance(putanjaIsplate).getIsplate().remove(i);
		IsplataManager.getInstance().sacuvajPodatke(putanjaIsplate);
	}

	@Test
	public void testReset() {
		IsplataManager.getInstance();
		IsplataManager.reset();
		assertNull(IsplataManager.getIm(), "Menadžer neuspešno resetovan");
	}
	
	public static void main(String[] args) {
		ssj.managers.OsobaManager.getInstance(testiranjePath + "osobeTest.txt");
		IsplataManager.getInstance().ucitajPodatke(putanjaIsplate);
		assertTrue(IsplataManager.getInstance().getIsplate().size() > 0 && IsplataManager.getInstance().getIsplateMapa().keySet().size() > 0,
				"Podaci su neuspešno učitani");
	}

}
