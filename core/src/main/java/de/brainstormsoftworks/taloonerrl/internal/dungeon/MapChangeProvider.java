/**
 *
 */
package de.brainstormsoftworks.taloonerrl.internal.dungeon;

import java.util.HashSet;
import java.util.Set;

import de.brainstormsoftworks.taloonerrl.dungeon.IMap;
import de.brainstormsoftworks.taloonerrl.dungeon.IMapChangeListener;
import de.brainstormsoftworks.taloonerrl.dungeon.IMapChangeProvider;

/**
 * propagator for a changed map to listeners
 *
 * @author David Becker
 *
 */
public final class MapChangeProvider implements IMapChangeProvider {

	private static final Set<IMapChangeListener> changeListener = new HashSet<IMapChangeListener>();

	/**
	 * the map that was build last
	 */
	private static IMap lastCreatedMap = null;

	private static final MapChangeProvider instance = new MapChangeProvider();

	/**
	 * Constructor. private on purpose
	 */
	private MapChangeProvider() {
	}

	/**
	 * get an instance of this singleton
	 *
	 * @return instance
	 */
	public static MapChangeProvider getInstance() {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public void registerListener(final IMapChangeListener listener) {
		changeListener.add(listener);
		// try to set the map in the new listener
		listener.setMap(lastCreatedMap);

	}

	/** {@inheritDoc} */
	@Override
	public void removeListener(final IMapChangeListener listener) {
		changeListener.remove(listener);
	}

	/** {@inheritDoc} */
	@Override
	public void propagateMap(final IMap _map) {
		lastCreatedMap = _map;
		for (final IMapChangeListener listener : changeListener) {
			listener.setMap(lastCreatedMap);
		}

	}

}
