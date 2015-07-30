package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.brainstormsoftworks.taloonerrl.components.ControllerComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

public class MovementSystem extends EntityProcessingSystem {

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.all(PositionComponent.class, ControllerComponent.class));
	}

	@Override
	protected void process(final Entity entity) {
		// TODO implement collision
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<ControllerComponent> controllerMapper = ComponentMappers.getInstance().controller;
		final PositionComponent position = posMapper.get(entity);
		final ControllerComponent controllerComponent = controllerMapper.get(entity);
		// adds the vector to the position
		position.setX(position.getX() + controllerComponent.getMoveVector().getX());
		position.setY(position.getY() + controllerComponent.getMoveVector().getY());
		// resets controller vector
		controllerComponent.getMoveVector().setZero();
	}

}
