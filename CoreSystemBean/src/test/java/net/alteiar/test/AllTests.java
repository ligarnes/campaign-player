package net.alteiar.test;

import net.alteiar.test.beans.TestAuthorizableBasicBeans;
import net.alteiar.test.beans.TestChat;
import net.alteiar.test.beans.TestPlayer;
import net.alteiar.test.beans.map.TestMap;
import net.alteiar.test.beans.map.TestMapElement;
import net.alteiar.test.beans.map.TestNoise;
import net.alteiar.test.saveable.TestDeleteBeans;
import net.alteiar.test.saveable.TestLoad;
import net.alteiar.test.saveable.TestSave;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestChat.class, TestPlayer.class, TestImageUtils.class,
		TestMap.class, TestMapElement.class, TestNoise.class,
		TestAuthorizableBasicBeans.class, TestUniqueId.class,
		TestDeleteBeans.class, TestSave.class, TestLoad.class,
		TestBenchmark.class })
public class AllTests {

}
