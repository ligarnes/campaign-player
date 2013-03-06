package net.alteiar.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {

	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("tearing down");

	}
}
