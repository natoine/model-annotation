/*
 * Copyright 2010 Antoine Seilles (Natoine)
 *   This file is part of model-annotation.

    model-annotation is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    model-annotation is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with model-annotation.  If not, see <http://www.gnu.org/licenses/>.

 */
package fr.natoine.model_annotation;

public class Cardinalite 
{
	private int value ;
	private boolean max ;
	private boolean infinite ;
	/**
	 * Gets the int value of the cardinalite
	 * @return
	 */
	public int getValue() {
		return value;
	}
	/**
	 * Sets the int value of the cardinalite
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * Gets the boolean value of max
	 * If max is true it means you can have at most "value" objects
	 * If max is false, it means you must have exactly "value" objects
	 * @return
	 */
	public boolean isMax() {
		return max;
	}
	/**
	 * Sets the boolean value of max
	 * If max is true it means you can have at most "value" objects
	 * If max is false, it means you must have exactly "value" objects
	 * @param max
	 */
	public void setMax(boolean max) {
		this.max = max;
	}
	/**
	 * Gets the boolean value of infinite.
	 * If true it means that other information are irrelevant.
	 * You can have an infinity of objects
	 * @return
	 */
	public boolean isInfinite() {
		return infinite;
	}
	/**
	 * Sets the boolean value of infinite.
	 * If true it means that other information are irrelevant.
	 * You can have an infinity of objects
	 * @param infinite
	 */
	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
}
