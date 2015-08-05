package de.brainstormsoftworks.taloonerrl.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventBusTest {

	private static final int delay = 1000;
	static boolean dummyHandler = false;
	static int count = 0;
	IEventHandler dummy = new IEventHandler() {
		@Override
		public void processEvent(final Event event) {
			dummyHandler = true;
		}
	};
	IEventHandler countHandler = new IEventHandler() {
		@Override
		public void processEvent(final Event event) {
			count++;
		}
	};

	@Test
	public void testBasicFunctionality() throws InterruptedException {
		final long currentTimeMillis = System.currentTimeMillis();
		assertEquals("expect empty event queue", 0, EventBus.getInstance().size());
		EventBus.getInstance().addEvent(new Event(currentTimeMillis, delay));
		assertEquals("expect one entry in event queue", 1, EventBus.getInstance().size());
		EventBus.getInstance().processEvents();
		assertEquals("expect one entry in event queue", 1, EventBus.getInstance().size());
		EventBus.getInstance().registerEventHandler(dummy);
		EventBus.getInstance().registerEventHandler(countHandler);
		assertFalse("don't expect handler to be called jet", dummyHandler);
		assertEquals("don't expect handler to be called jet", 0, count);
		EventBus.getInstance().processEvents();
		assertEquals("expect one entry in event queue", 1, EventBus.getInstance().size());
		assertTrue("expect handler to have recieved event", dummyHandler);
		assertEquals("expect handler to have recieved event", 1, count);

		// remove count handler and test again
		EventBus.getInstance().unregisterEventHandler(countHandler);
		EventBus.getInstance().processEvents();
		assertEquals("expect one entry in event queue", 1, EventBus.getInstance().size());
		assertTrue("expect handler to have recieved event", dummyHandler);
		assertEquals("expect handler to not recieved event", 1, count);

		// re-add handler and test again
		EventBus.getInstance().registerEventHandler(countHandler);
		EventBus.getInstance().processEvents();
		assertEquals("expect one entry in event queue", 1, EventBus.getInstance().size());
		assertTrue("expect handler to have recieved event", dummyHandler);
		assertEquals("expect handler to have recieved event", 2, count);

		Thread.sleep(delay);
		EventBus.getInstance().processEvents();
		assertEquals("expect empty event queue", 0, EventBus.getInstance().size());
	}

}
