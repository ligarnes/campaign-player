package net.alteiar.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {

	public static String getPlayerName() {
		return "player-name";
	}

	@BeforeClass
	public static void setUp() {
	}

	@AfterClass
	public static void tearDown() {
	}
}
