package net.alteiar.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import net.alteiar.shared.Vector2D;

import org.junit.Test;

public class TestVector2d extends BasicTest {

	@Test(timeout = 5000)
	public void testVector2DEquals() {
		Vector2D v1 = new Vector2D(5f, 5f);
		Vector2D v2 = new Vector2D(10f, 10f);
		Vector2D v3 = v1.clone();

		assertTrue("v1 and v2 must not be equals", !v1.equals(v2));
		assertTrue("v1 and v2 must not be equals",
				!Integer.valueOf(v1.hashCode()).equals(v2.hashCode()));

		//
		assertTrue("v1 and v1 must be equals", v1.equals(v1));
		assertTrue("v1 and v1 must be equals", Integer.valueOf(v1.hashCode())
				.equals(v1.hashCode()));

		assertTrue("v1 and null must not be equals", !v1.equals(null));

		//
		assertTrue("v1 and v3 must be equals", v1.equals(v3));
		assertTrue("v1 and v3 must be equals", Integer.valueOf(v1.hashCode())
				.equals(v3.hashCode()));

		assertTrue("v1 and other must not be equals", !v1.equals(new Point()));

		v1.setX(null);
		assertTrue("v1 and v3 must not be equals", !v1.equals(v3));

		v1.setX(v3.getX());
		v1.setY(null);
		assertTrue("v1 and v3 must not be equals", !v1.equals(v3));

		v1.setY(v3.getY() + 5);
		assertTrue("v1 and v3 must not be equals", !v1.equals(v3));
	}

	@Test(timeout = 5000)
	public void testVector2D() {
		Float expectedX1 = 5.0f;
		Float expectedY1 = 5.0f;

		Float expectedX2 = 5.0f;
		Float expectedY2 = 10.0f;

		Float expectedDistance = 5.0f;

		Vector2D v1 = new Vector2D(expectedX1, expectedY1);
		Vector2D v2 = new Vector2D(expectedX2, expectedY2);

		assertEquals("X should be same as expected", expectedX1, v1.getX());
		assertEquals("Y should be same as expected", expectedY1, v1.getY());

		assertEquals("X should be same as expected", expectedX2, v2.getX());
		assertEquals("Y should be same as expected", expectedY2, v2.getY());

		assertEquals("The distance should be same as expected",
				expectedDistance, v1.getDistance(v2));

		assertEquals("The squared distance should be same as expected",
				Float.valueOf(Double.valueOf(Math.pow(expectedDistance, 2))
						.floatValue()), v1.getSquaredDistance(v2));

		Vector2D v1Clone = v1.clone();
		assertTrue("v1 and v1Clone should be equals", v1.equals(v1Clone));

		Float expectedX3 = 10.0f;
		Float expectedY3 = 10.0f;
		Vector2D v3 = v1.add(v1Clone);
		assertEquals("X should be same as expected", expectedX3, v3.getX());
		assertEquals("Y should be same as expected", expectedY3, v3.getY());

		expectedX2 = 10.0f;
		expectedY2 = 15.0f;
		v2.addX(5f);
		v2.addY(5f);
		assertEquals("X should be same as expected", expectedX2, v2.getX());
		assertEquals("Y should be same as expected", expectedY2, v2.getY());

		Float expectedX4 = 10.0f;
		Float expectedY4 = 10.0f;
		Vector2D v4 = v1.multiply(2.0f);
		assertEquals("X should be same as expected", expectedX4, v4.getX());
		assertEquals("Y should be same as expected", expectedY4, v4.getY());

		Float expectedX5 = 0.0f;
		Float expectedY5 = 0.0f;
		Vector2D v5 = v1.sub(v1);
		assertEquals("X should be same as expected", expectedX5, v5.getX());
		assertEquals("Y should be same as expected", expectedY5, v5.getY());

		Float magnitude = 50.0f;
		assertEquals("The squared magnitude should be same as expected",
				magnitude, v1.getSquaredMagnitude());
		assertEquals(
				"The squared magnitude should be same as expected",
				Float.valueOf(Double.valueOf(Math.sqrt(magnitude)).floatValue()),
				v1.getMagnitude());

	}
}
