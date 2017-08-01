/*******************************************************************************
 * Copyright (c) 2015, 2017 David Becker.
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
import com.artemis.systems.IteratingSystem;

import de.brainstormsoftworks.taloonerrl.components.HighlightAbleComponent;
import de.brainstormsoftworks.taloonerrl.components.PositionComponent;
import de.brainstormsoftworks.taloonerrl.core.engine.ComponentMappers;
import de.brainstormsoftworks.taloonerrl.core.engine.GameEngine;
import de.brainstormsoftworks.taloonerrl.dungeon.MapManager;
import de.brainstormsoftworks.taloonerrl.render.FovWrapper;
import de.brainstormsoftworks.taloonerrl.render.Renderer;

/**
 * rendering system that highlights the tile of the given entity
 *
 * @author David Becker
 *
 */
public class HighlightRenderSystem extends IteratingSystem {

	private int x, y;
	private int cursorAnimationOffset;
	private PositionComponent positionComponent;
	private HighlightAbleComponent highlight;

	public HighlightRenderSystem() {
		super(Aspect.all(PositionComponent.class, HighlightAbleComponent.class));
	}

	@Override
	protected void process(final int _entityId) {
		positionComponent = ComponentMappers.getInstance().position.get(_entityId);
		highlight = ComponentMappers.getInstance().highlight.get(_entityId);
		if (highlight.isHighlightStyleNone() || !highlight.isHighlightActive()) {
			return;
		}
		if (highlight.isHighlightStyleBlinking()) {

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
		}

		x = positionComponent.getX();
		y = positionComponent.getY();
		if ((highlight.isHighlightOutsideFOV() || FovWrapper.getInstance().isLit(x, y))
				&& (highlight.isHighlightUnvisited() || MapManager.getInstance().getMap().isVisited(x, y))) {
			Renderer.getInstance().renderOnTile(HighlightAbleComponent.getCursorTopLeft(), x, y,
					HighlightAbleComponent.cursorTopLeftOffsetX - highlight.getCursorAnimationOffset(),
					HighlightAbleComponent.cursorTopLeftOffsetY + highlight.getCursorAnimationOffset());
			Renderer.getInstance().renderOnTile(HighlightAbleComponent.getCursorTopRight(), x, y,
					HighlightAbleComponent.cursorTopRightOffsetX + highlight.getCursorAnimationOffset(),
					+HighlightAbleComponent.cursorTopRightOffsetY + highlight.getCursorAnimationOffset());
			Renderer.getInstance().renderOnTile(HighlightAbleComponent.getCursorBottomLeft(), x, y,
					HighlightAbleComponent.cursorBottomLeftOffsetX - highlight.getCursorAnimationOffset(),
					HighlightAbleComponent.cursorBottomLeftOffsetY - highlight.getCursorAnimationOffset());
			Renderer.getInstance().renderOnTile(HighlightAbleComponent.getCursorBottomRight(), x, y,
					HighlightAbleComponent.cursorBottomRightOffsetX + highlight.getCursorAnimationOffset(),
					HighlightAbleComponent.cursorBottomRightOffsetY - highlight.getCursorAnimationOffset());
		}
	}
}
