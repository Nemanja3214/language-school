package ssj.testiranje;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ssj.managers.CenovnikManager;
import ssj.managers.Cuvanje;
import ssj.managers.IsplataManager;
import ssj.managers.JezikManager;
import ssj.managers.JezikPredavacManager;
import ssj.managers.KursManager;
import ssj.managers.Manager;
import ssj.managers.NotifikacijeManager;
import ssj.managers.OsobaManager;
import ssj.managers.PohadjanjeManager;
import ssj.managers.RacunManager;
import ssj.managers.RezultatManager;
import ssj.managers.TerminManager;
import ssj.managers.TestManager;
import ssj.managers.ZahtevManager;
import ssj.model.RacunSkole;

public class CuvanjeTest {
	public static String testiranjePath = "data" + File.separator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Cuvanje.ucitajSve();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		JezikManager.reset();
		OsobaManager.reset();
		JezikPredavacManager.reset();
		KursManager.reset();
		TestManager.reset();
		TerminManager.reset();
		RacunManager.reset();
		RezultatManager.reset();
		ZahtevManager.reset();
		PohadjanjeManager.reset();
		IsplataManager.reset();
		NotifikacijeManager.reset();
	}

	@Test
	public void testSacuvaj() {
		Cuvanje.sacuvaj();
		Cuvanje.ucitajSve();
		
		assertTrue(JezikManager.getInstance().getJezici().size()>0 && OsobaManager.getInstance().getOsobe().size()>0 && KursManager.getInstance().getKursevi().size()>0
				&& TestManager.getInstance().getTestovi().size()>0 && TerminManager.getInstance().getTermini().size()>0 && RacunManager.getInstance().getRacuni().size()>0
				&& RezultatManager.getInstance().getRezultati().size()>0 && ZahtevManager.getInstance().getZahtevi().size()>0 &&
				PohadjanjeManager.getInstance().getPohadjanja().size()>0 && IsplataManager.getInstance().getIsplate().size()>0 &&
				NotifikacijeManager.getInstance().getNotifikacije().size()>0);
	}
//	
//	public static void main(String[] args) {
//		ucitajSve();
//		System.out.println(JezikManager.getInstance().getJezici().size()>0);
//		System.out.println(OsobaManager.getInstance().getOsobe().size()>0);
//		System.out.println(KursManager.getInstance().getKursevi().size()>0);
//		System.out.println(TestManager.getInstance().getTestovi().size()>0);
//		System.out.println(TerminManager.getInstance().getTermini().size()>0);
//		System.out.println(RacunManager.getInstance().getRacuni().size()>0);
//		System.out.println(RezultatManager.getInstance().getRezultati().size()>0);
//		System.out.println(ZahtevManager.getInstance().getZahtevi().size()>0);
//		System.out.println(PohadjanjeManager.getInstance().getPohadjanja().size()>0);
//		System.out.println(IsplataManager.getInstance().getIsplate().size()>0);
//		System.out.println(NotifikacijeManager.getInstance().getNotifikacije().size()>0);
//	}

}
