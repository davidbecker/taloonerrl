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

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;

/**
 * collection of utilities methods useful for rendering
 *
 * @author David Becker
 *
 */
public final class RenderUtil {

	public static final float PERCENT_100 = 1.00f;
	public static final float PERCENT_75 = .75f;
	public static final float PERCENT_50 = .50f;
	public static final float PERCENT_25 = .25f;
	public static final float PERCENT_0 = .00f;

	/**
	 * central place to keep track of resources that should be disposed at the
	 * end of the application
	 */
	private static final Set<IDisposableInstance> toDispose = new HashSet<IDisposableInstance>();

	private RenderUtil() {
	}

	/**
	 * calculates how a GUI bar should be filled.<br/>
	 * filled states are expected to go up, so anything that is between 75% and
	 * 100% should be filled with 100%, down to anything between 0% and 25%
	 * should be filled 25%
	 *
	 * @param numberOfParts
	 *            in how many parts should the bar be divided? should be >= 1.
	 * @param percentFilled
	 *            the value that should be represented by the bar. expected to
	 *            be between {@value #PERCENT_100} and {@value #PERCENT_0}
	 * @return array will be <code>null</code> on invalid input, an array with
	 *         the dimension of numberOfParts and filled states otherwise
	 *         (sorted with the higher filled states first)
	 */
	public static EBarElementFilled[] calculateBarFill(final int numberOfParts, final float percentFilled) {
		if (numberOfParts < 1) {
			return null;
		}
		final EBarElementFilled[] fills = new EBarElementFilled[numberOfParts];
		// how much should be in a part for it to be considered full?
		final float filled100PercentPerPart = PERCENT_100 / numberOfParts;
		// the amount that is still to be distributed accross the parts
		float notDivided = percentFilled;
		for (int i = 0; i < numberOfParts; i++) {
			if (notDivided <= PERCENT_0) {
				fills[i] = getSingleFill(PERCENT_0);
			} else if (notDivided >= filled100PercentPerPart) {
				notDivided -= filled100PercentPerPart;
				fills[i] = getSingleFill(PERCENT_100);
			} else {
				float chunk = notDivided - filled100PercentPerPart;
				if (chunk <= 0) {
					chunk = notDivided;
				}
				fills[i] = getSingleFill(chunk / filled100PercentPerPart);
				notDivided -= chunk;
			}
		}
		return fills;
	}

	/**
	 * rounds the filled state up to the next standard one
	 *
	 * @param percentFilled
	 *            percentage to check against
	 * @return {@link #PERCENT_0}, {@link #PERCENT_25}, {@link #PERCENT_50},
	 *         {@link #PERCENT_75} or {@link #PERCENT_100}
	 */
	public static EBarElementFilled getSingleFill(final float percentFilled) {
		if (percentFilled <= PERCENT_0) {
			return EBarElementFilled.PERCENT_0;
		}
		if (percentFilled <= PERCENT_25) {
			return EBarElementFilled.PERCENT_25;
		}
		if (percentFilled <= PERCENT_50) {
			return EBarElementFilled.PERCENT_50;
		}
		if (percentFilled <= PERCENT_75) {
			return EBarElementFilled.PERCENT_75;
		}
		return EBarElementFilled.PERCENT_100;
	}

	/**
	 * retrieves the key frame for the given animation
	 *
	 * @param animation
	 *            to get the frame for
	 * @return texture region
	 */
	public static TextureRegion getKeyFrame(final Animation animation) {
		return animation.getKeyFrame(GameEngine.getInstance().getStateTime(), true);
	}

	/**
	 * add the {@link IDisposableInstance} to the list that should be disposed
	 * when the application is closed
	 *
	 * @param disposableInstance
	 */
	public static void addToDisposeList(final IDisposableInstance disposableInstance) {
		toDispose.add(disposableInstance);
	}

	/**
	 * dispose all the things!
	 */
	public static void disposeInstances() {
		for (final IDisposableInstance iDisposableInstance : toDispose) {
			iDisposableInstance.disposeInstance();
		}

	}
}
