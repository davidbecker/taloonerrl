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
package de.brainstormsoftworks.taloonerrl.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import de.brainstormsoftworks.taloonerrl.components.HighlightComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * rendering system that highlights the tile of the given entity
 *
 * @author David Becker
 *
 */
public class HighlightRenderSystem extends EntityProcessingSystem {

	private int x, y;
	private int cursorAnimationOffset;
	private PositionComponent positionComponent;
	private HighlightComponent highlight;

	public HighlightRenderSystem() {
		super(Aspect.all(PositionComponent.class, HighlightComponent.class));
	}

	@Override
	protected void process(final Entity e) {
		positionComponent = ComponentMappers.getInstance().position.get(e);
		highlight = ComponentMappers.getInstance().highlight.get(e);

		// TODO refactor getting/setting of offset
		switch ((int) GameEngine.getInstance().getStateTime() % 4) {
		case 0:
			cursorAnimationOffset = 0;
			break;
		case 1:
			cursorAnimationOffset = 1;
			break;
		case 2:
			cursorAnimationOffset = 2;
			break;
		case 3:
			cursorAnimationOffset = 1;
			break;
		default:
			cursorAnimationOffset = 0;
		}
		highlight.setCursorAnimationOffset(cursorAnimationOffset);

		x = positionComponent.getX();
		y = positionComponent.getY();
		Renderer.getInstance().renderOnTileWithOffset(HighlightComponent.getCursorTopLeft(), x, y,
				HighlightComponent.cursorTopLeftOffsetX - highlight.getCursorAnimationOffset(),
				HighlightComponent.cursorTopLeftOffsetY + highlight.getCursorAnimationOffset());
		Renderer.getInstance().renderOnTileWithOffset(HighlightComponent.getCursorTopRight(), x, y,
				HighlightComponent.cursorTopRightOffsetX + highlight.getCursorAnimationOffset(),
				+HighlightComponent.cursorTopRightOffsetY + highlight.getCursorAnimationOffset());
		Renderer.getInstance().renderOnTileWithOffset(HighlightComponent.getCursorBottomLeft(), x, y,
				HighlightComponent.cursorBottomLeftOffsetX - highlight.getCursorAnimationOffset(),
				HighlightComponent.cursorBottomLeftOffsetY - highlight.getCursorAnimationOffset());
		Renderer.getInstance().renderOnTileWithOffset(HighlightComponent.getCursorBottomRight(), x, y,
				HighlightComponent.cursorBottomRightOffsetX + highlight.getCursorAnimationOffset(),
				HighlightComponent.cursorBottomRightOffsetY - highlight.getCursorAnimationOffset());

	}
}
