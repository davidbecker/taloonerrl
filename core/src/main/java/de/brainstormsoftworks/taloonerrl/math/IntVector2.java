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
package de.brainstormsoftworks.taloonerrl.math;

import java.io.Serializable;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * a reimplementation of {@link Vector2} to work with integer values
 *
 * @author David Becker
 *
 */
public final class IntVector2 implements Serializable, Vector<IntVector2> {

	private static final long serialVersionUID = 2371124841393014586L;
	private int x;
	private int y;

	public IntVector2(final int _x, final int _y) {
		x = _x;
		y = _y;
	}

	@Override
	public IntVector2 cpy() {
		return new IntVector2(x, y);
	}

	@Override
	public float len() {
		return (float) Math.sqrt(len2());
	}

	@Override
	public float len2() {
		return x * x + y * y;
	}

	@Override
	public IntVector2 limit(final float limit) {
		return limit2(limit * limit);
	}

	@Override
	public IntVector2 limit2(final float limit2) {
		final float len2 = len2();
		if (len2 > limit2) {
			return scl((float) Math.sqrt(limit2 / len2));
		}
		return this;
	}

	@Override
	public IntVector2 setLength(final float len) {
		return setLength2(len * len);
	}

	@Override
	public IntVector2 setLength2(final float len2) {
		final float oldLen2 = len2();
		return oldLen2 == 0 || oldLen2 == len2 ? this : scl((float) Math.sqrt(len2 / oldLen2));
	}

	@Override
	public IntVector2 clamp(final float min, final float max) {
		final float len2 = len2();
		if (len2 == 0f) {
			return this;
		}
		final float max2 = max * max;
		if (len2 > max2) {
			return scl((float) Math.sqrt(max2 / len2));
		}
		final float min2 = min * min;
		if (len2 < min2) {
			return scl((float) Math.sqrt(min2 / len2));
		}
		return this;
	}

	@Override
	public IntVector2 set(final IntVector2 v) {
		x = v.getX();
		y = v.getY();
		return this;
	}

	public IntVector2 set(final int _x, final int _y) {
		x = _x;
		y = _y;
		return this;
	}

	@Override
	public IntVector2 sub(final IntVector2 v) {
		x -= v.getX();
		y -= v.getY();
		return this;
	}

	@Override
	public IntVector2 nor() {
		final float len = len();
		if (len != 0) {
			x /= len;
			y /= len;
		}
		return this;
	}

	@Override
	public IntVector2 add(final IntVector2 v) {
		x += v.getX();
		y += v.getY();
		return this;
	}

	@Override
	public float dot(final IntVector2 v) {
		return x * v.getX() + y * v.getY();
	}

	@Override
	public IntVector2 scl(final float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	@Override
	public IntVector2 scl(final IntVector2 v) {
		x *= v.getX();
		y *= v.getY();
		return this;
	}

	@Override
	public float dst(final IntVector2 v) {
		return (float) Math.sqrt(dst2(v));
	}

	@Override
	public float dst2(final IntVector2 v) {
		final int dX = v.getX() - x;
		final int dY = v.getY() - y;
		return dX * dX + dY * dY;
	}

	@Override
	public IntVector2 lerp(final IntVector2 target, final float alpha) {
		final float invAlpha = 1.0f - alpha;
		x = (int) (x * invAlpha + target.getX() * alpha);
		y = (int) (y * invAlpha + target.getY() * alpha);
		return this;
	}

	@Override
	public IntVector2 interpolate(final IntVector2 target, final float alpha,
			final Interpolation interpolator) {
		return lerp(target, interpolator.apply(alpha));
	}

	@Override
	public boolean isUnit() {
		return isUnit(0.000000001f);
	}

	@Override
	public boolean isUnit(final float margin) {
		return Math.abs(len2() - 1f) < margin;
	}

	@Override
	public boolean isZero() {
		return x == 0 && y == 0;
	}

	@Override
	public boolean isZero(final float margin) {
		return len2() < margin;
	}

	@Override
	public boolean isOnLine(final IntVector2 other) {
		return MathUtils.isZero(x * other.y - y * other.x);
	}

	@Override
	public boolean isOnLine(final IntVector2 other, final float epsilon) {
		return MathUtils.isZero(x * other.y - y * other.x, epsilon);
	}

	@Override
	public boolean isCollinear(final IntVector2 other, final float epsilon) {
		return isOnLine(other, epsilon) && dot(other) > 0f;
	}

	@Override
	public boolean isCollinear(final IntVector2 other) {
		return isOnLine(other) && dot(other) > 0f;
	}

	@Override
	public boolean isCollinearOpposite(final IntVector2 other, final float epsilon) {
		return isOnLine(other, epsilon) && dot(other) < 0f;
	}

	@Override
	public boolean isCollinearOpposite(final IntVector2 other) {
		return isOnLine(other) && dot(other) < 0f;
	}

	@Override
	public boolean isPerpendicular(final IntVector2 vector) {
		return MathUtils.isZero(dot(vector));
	}

	@Override
	public boolean isPerpendicular(final IntVector2 vector, final float epsilon) {
		return MathUtils.isZero(dot(vector), epsilon);
	}

	@Override
	public boolean hasSameDirection(final IntVector2 vector) {
		return dot(vector) > 0;
	}

	@Override
	public boolean hasOppositeDirection(final IntVector2 vector) {
		return dot(vector) < 0;
	}

	@Override
	public boolean epsilonEquals(final IntVector2 other, final float epsilon) {
		if (other == null) {
			return false;
		}
		if (Math.abs(other.getX() - x) > epsilon) {
			return false;
		}
		if (Math.abs(other.getY() - y) > epsilon) {
			return false;
		}
		return true;
	}

	@Override
	public IntVector2 mulAdd(final IntVector2 v, final float scalar) {
		x += v.getX() * scalar;
		y += v.getY() * scalar;
		return this;
	}

	@Override
	public IntVector2 mulAdd(final IntVector2 v, final IntVector2 mulVec) {
		x += v.getX() * mulVec.getX();
		y += v.getY() * mulVec.getY();
		return this;
	}

	@Override
	public IntVector2 setZero() {
		x = 0;
		y = 0;
		return this;
	}

	/**
	 * getter for x
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * setter for x
	 *
	 * @param _x
	 *            the x to set
	 */
	public void setX(final int _x) {
		x = _x;
	}

	/**
	 * getter for y
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * setter for y
	 *
	 * @param y
	 *            the y to set
	 */
	public void setY(final int _y) {
		y = _y;
	}

	@Override
	public String toString() {
		return "{ " + x + ", " + y + "}";
	}

}
