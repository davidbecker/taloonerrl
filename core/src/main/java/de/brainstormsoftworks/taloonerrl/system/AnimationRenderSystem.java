package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.components.AnimationComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.render.RenderUtil;

public class AnimationRenderSystem extends EntityProcessingSystem {
	private final SpriteBatch batch;
	private static final float scale = 16f;

	@SuppressWarnings("unchecked")
	public AnimationRenderSystem() {
		super(Aspect.all(PositionComponent.class, AnimationComponent.class));
		batch = new SpriteBatch();
	}

	@Override
	protected void process(final Entity e) {
		final ComponentMapper<PositionComponent> posMapper = ComponentMappers.getInstance().position;
		final ComponentMapper<AnimationComponent> spriteMapper = ComponentMappers.getInstance().sprite;
		final PositionComponent positionComponent = posMapper.get(e);
		final AnimationComponent spriteComponent = spriteMapper.get(e);
		final Animation animation = spriteComponent.getAnimation();
		if (animation != null) {
			batch.begin();
			batch.draw(RenderUtil.getKeyFrame(animation), positionComponent.getX() * scale,
					positionComponent.getY() * scale);
			batch.end();
		}
	}
}
