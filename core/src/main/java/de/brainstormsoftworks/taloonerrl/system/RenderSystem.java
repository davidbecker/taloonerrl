package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.SpriteComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;

public class RenderSystem extends EntityProcessingSystem {
	private final SpriteBatch batch;

	@SuppressWarnings("unchecked")
	public RenderSystem() {
		super(Aspect.all(PositionComponent.class, SpriteComponent.class));
		batch = new SpriteBatch();
	}

	@Override
	protected void process(final Entity e) {
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<SpriteComponent> spriteMapper = ComponentMappers.getInstance().sprite;
		// TODO implement

	}
}
