package net.alteiar;

import net.alteiar.battle.TestBattle;
import net.alteiar.character.TestCharacter;
import net.alteiar.character.TestMonster;
import net.alteiar.server.shared.campaign.ServerCampaign;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBattle.class, TestCharacter.class, TestMonster.class })
public class AllTests {
	@BeforeClass
	public static void setUp() {
		System.out.println("Setting up test");
		String address = "127.0.0.1";
		String port = "1099";
		ServerCampaign.startServer(address, Integer.valueOf(port));
	}

	@AfterClass
	public static void tearDown() {
		System.out.println("tearing down");
	}
}
