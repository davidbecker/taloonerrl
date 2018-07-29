/*******************************************************************************
 * Copyright (c) 2018 David Becker.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     David Becker - initial API and implementation
 ******************************************************************************/
package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;

import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.system.util.PositionUtil;
import lombok.Getter;

/**
 * component to hold references to other entities that are "owned" by the entity
 * that has this component
 *
 * @author David Becker
 *
 */
public class InventoryComponent extends PooledComponent implements ISetAbleComponent<InventoryComponent> {

	private static final int DEFAULT_MAX_SIZE = 20;

	@EntityId
	private final IntBag entities = new IntBag(DEFAULT_MAX_SIZE);
	@Getter
	private int maxSize = DEFAULT_MAX_SIZE;

	@Override
	protected void reset() {
		entities.clear();
		maxSize = DEFAULT_MAX_SIZE;
	}

	/** {@inheritDoc} */
	@Override
	public void overrideComponent(final InventoryComponent _component) {
		entities.clear();
		entities.addAll(_component.entities);
		maxSize = _component.maxSize;
	}

	/**
	 * @param _maxSize
	 *            the maxSize to set
	 * @return false if there are currently tu much elements in the inventory
	 */
	public boolean setMaxSize(final int _maxSize) {
		if (maxSize > entities.size()) {
			Gdx.app.debug(InventoryComponent.class.getSimpleName(), "failed to reduce max size");
			return false;
		}
		maxSize = _maxSize;
		entities.setSize(_maxSize);
		return true;
	}

	/**
	 * tries to add an entity to the inventory
	 *
	 * @param _entityId
	 *            to add
	 * @return false if maximum elements have been reached
	 */
	public boolean addEntity(final int _entityId) {
		if (entities.size() < maxSize) {
			final PositionComponent position = ComponentMappers.getInstance().position.getSafe(_entityId);
			if (PositionUtil.isValidPosition(position)) {
				position.reset();
			}
			entities.add(_entityId);
		}
		return false;
	}

	/**
	 * check if there is room for more entities
	 *
	 * @return
	 */
	public boolean hasCapacity() {
		return entities.size() < maxSize;
	}
}
