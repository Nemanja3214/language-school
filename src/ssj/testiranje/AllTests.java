package ssj.testiranje;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IsplataManagerTest.class, JezikManagerTest.class, JezikPredavacManagerTest.class, KursManagerTest.class,
		OsobaManagerTest.class, NotifikacijeManagerTest.class, PohadjanjeManagerTest.class, RacunManagerTest.class, 
		TerminManagerTest.class, TestManagerTest.class, RezultatManagerTest.class, ZahtevManagerTest.class, CuvanjeTest.class, CenovnikManagerTest.class})
public class AllTests {

}
