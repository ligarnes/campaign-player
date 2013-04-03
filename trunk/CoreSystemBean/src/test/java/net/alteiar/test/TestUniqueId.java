package net.alteiar.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.alteiar.shared.UniqueID;

import org.junit.Test;

public class TestUniqueId extends BasicTest {

	@Test
	public void testUniqueIdEquals() {
		UniqueID id1 = new UniqueID();
		UniqueID id2 = new UniqueID();

		assertTrue("Should be equals to itself", id1.equals(id1));
		assertTrue("Should be different to another id", !id1.equals(id2));
		assertTrue("Should be different to null", !id1.equals(null));
		assertTrue("Should be different to another object", !id1.equals(""));
	}

	@Test
	public void testSequenceUniqueId() {
		// TODO increase to have a better test but take long time
		int idCount = 100;
		UniqueID[] ids = new UniqueID[idCount];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = new UniqueID();
		}

		for (int i = 0; i < ids.length; i++) {
			for (int j = i + 1; j < ids.length; j++) {
				if (ids[i].equals(ids[j])) {
					fail("the id " + i + " is same as id " + j + " values:["
							+ ids[i] + " | " + ids[j]);
				}
			}
		}
	}

	@Test
	public void testParalleleUniqueId() {
		// TODO increase to have a better test but take long time
		int idCount = 25;
		final UniqueID[] ids1 = new UniqueID[idCount];
		final UniqueID[] ids2 = new UniqueID[idCount];
		final UniqueID[] ids3 = new UniqueID[idCount];
		final UniqueID[] ids4 = new UniqueID[idCount];

		UniqueID[] ids = new UniqueID[idCount * 4];

		Thread tr1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < ids1.length; i++) {
					ids1[i] = new UniqueID();
				}
			}
		});
		Thread tr2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < ids2.length; i++) {
					ids2[i] = new UniqueID();
				}
			}
		});
		Thread tr3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < ids3.length; i++) {
					ids3[i] = new UniqueID();
				}
			}
		});
		Thread tr4 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < ids4.length; i++) {
					ids4[i] = new UniqueID();
				}
			}
		});

		tr1.start();
		tr2.start();
		tr3.start();
		tr4.start();

		try {
			tr1.join();
			tr2.join();
			tr3.join();
			tr4.join();
		} catch (InterruptedException e) {
			fail("fail to join the threads");
		}

		for (int i = 0; i < ids1.length; i++) {
			ids[i + (idCount * 0)] = ids1[i];
		}
		for (int i = 0; i < ids2.length; i++) {
			ids[i + (idCount * 1)] = ids2[i];
		}
		for (int i = 0; i < ids3.length; i++) {
			ids[i + (idCount * 2)] = ids3[i];
		}
		for (int i = 0; i < ids4.length; i++) {
			ids[i + (idCount * 3)] = ids4[i];
		}

		for (int i = 0; i < ids.length; i++) {
			for (int j = i + 1; j < ids.length; j++) {
				if (ids[i].equals(ids[j])) {
					fail("the id " + i + " is same as id " + j + " values:["
							+ ids[i] + " | " + ids[j]);
				}
			}
		}
	}
}
