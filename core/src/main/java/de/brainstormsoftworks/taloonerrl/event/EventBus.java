package de.brainstormsoftworks.taloonerrl.event;

/**
 * manager class for events
 *
 * @author David Becker
 *
 */
public final class EventBus {
	private static final EventBus instance = new EventBus();
	// Set<Event> events = new SynchronizedSortedSet<Event>();

	/**
	 * constructor.<br/>
	 * private on purpose
	 */
	private EventBus() {
	}

	/**
	 * getter for an instance
	 *
	 * @return {@link #instance}
	 */
	public static EventBus getInstance() {
		return instance;
	}

}
