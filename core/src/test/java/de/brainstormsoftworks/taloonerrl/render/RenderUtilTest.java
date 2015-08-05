/*******************************************************************************
 * Copyright (c) 2015 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.render;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class RenderUtilTest {

	@Test
	public void testSingleBar() {
		final int length = 1;
		final EBarElementFilled[] calculateBarFill0Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_0);
		final EBarElementFilled[] calculateBarFill25Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_25);
		final EBarElementFilled[] calculateBarFill50Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_50);
		final EBarElementFilled[] calculateBarFill75Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_75);
		final EBarElementFilled[] calculateBarFill100Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_100);

		assertNotNull("excpected to get an array of float", calculateBarFill0Percent);
		assertNotNull("excpected to get an array of float", calculateBarFill25Percent);
		assertNotNull("excpected to get an array of float", calculateBarFill50Percent);
		assertNotNull("excpected to get an array of float", calculateBarFill75Percent);
		assertNotNull("excpected to get an array of float", calculateBarFill100Percent);

		assertEquals("single bar filled - checking size of array", length, calculateBarFill0Percent.length);
		assertEquals("single bar filled - checking size of array", length, calculateBarFill25Percent.length);
		assertEquals("single bar filled - checking size of array", length, calculateBarFill50Percent.length);
		assertEquals("single bar filled - checking size of array", length, calculateBarFill75Percent.length);
		assertEquals("single bar filled - checking size of array", length, calculateBarFill100Percent.length);

		assertEquals("single bar filled", EBarElementFilled.PERCENT_0, calculateBarFill0Percent[0]);
		assertEquals("single bar filled", EBarElementFilled.PERCENT_25, calculateBarFill25Percent[0]);
		assertEquals("single bar filled", EBarElementFilled.PERCENT_50, calculateBarFill50Percent[0]);
		assertEquals("single bar filled", EBarElementFilled.PERCENT_75, calculateBarFill75Percent[0]);
		assertEquals("single bar filled", EBarElementFilled.PERCENT_100, calculateBarFill100Percent[0]);

	}

	@Test
	public void testDoubleBar() {
		final int length = 2;

		final EBarElementFilled[] calculateBarFill0Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_0);
		assertNotNull("excpected to get an array of float", calculateBarFill0Percent);
		assertEquals("double bar filled - checking size of array", length, calculateBarFill0Percent.length);
		assertEquals("double bar filled - checking first value", EBarElementFilled.PERCENT_0,
				calculateBarFill0Percent[0]);
		assertEquals("double bar filled - checking second value", EBarElementFilled.PERCENT_0,
				calculateBarFill0Percent[1]);

		final EBarElementFilled[] calculateBarFill25Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_25);
		assertNotNull("excpected to get an array of float", calculateBarFill25Percent);
		assertEquals("double bar filled - checking size of array", length, calculateBarFill25Percent.length);
		assertEquals("double bar filled - checking first value", EBarElementFilled.PERCENT_50,
				calculateBarFill25Percent[0]);
		assertEquals("double bar filled - checking second value", EBarElementFilled.PERCENT_0,
				calculateBarFill25Percent[1]);

		final EBarElementFilled[] calculateBarFill50Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_50);
		assertNotNull("excpected to get an array of float", calculateBarFill50Percent);
		assertEquals("double bar filled - checking size of array", length, calculateBarFill50Percent.length);
		assertEquals("double bar filled - checking first value", EBarElementFilled.PERCENT_100,
				calculateBarFill50Percent[0]);
		assertEquals("double bar filled - checking second value", EBarElementFilled.PERCENT_0,
				calculateBarFill50Percent[1]);

		final EBarElementFilled[] calculateBarFill75Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_75);
		assertNotNull("excpected to get an array of float", calculateBarFill75Percent);
		assertEquals("double bar filled - checking size of array", length, calculateBarFill75Percent.length);
		assertEquals("double bar filled - checking first value", EBarElementFilled.PERCENT_100,
				calculateBarFill75Percent[0]);
		assertEquals("double bar filled - checking second value", EBarElementFilled.PERCENT_50,
				calculateBarFill75Percent[1]);

		final EBarElementFilled[] calculateBarFill100Percent = RenderUtil.calculateBarFill(length,
				RenderUtil.PERCENT_100);
		assertNotNull("excpected to get an array of float", calculateBarFill100Percent);
		assertEquals("double bar filled - checking size of array", length, calculateBarFill100Percent.length);
		assertEquals("double bar filled - checking first value", EBarElementFilled.PERCENT_100,
				calculateBarFill100Percent[0]);
		assertEquals("double bar filled - checking second value", EBarElementFilled.PERCENT_100,
				calculateBarFill100Percent[1]);
	}
}
