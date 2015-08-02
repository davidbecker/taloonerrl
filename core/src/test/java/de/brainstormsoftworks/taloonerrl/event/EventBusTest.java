package de.brainstormsoftworks.taloonerrl.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventBusTest {

	private static final int delay = 100;
	static boolean dummyHandler = false;
	IEventHandler dummy = new IEventHandler() {
		@Override
		public void processEvent(final Event event) {
			dummyHandler = true;
		}
	};

	@Test
	public void testBasicFunctionality() throws InterruptedException {
		final long currentTimeMillis = System.currentTimeMillis();
		assertEquals("expect empty event queue", EventBus.getInstance().size(), 0);
		EventBus.getInstance().addEvent(new Event(currentTimeMillis, delay));
		assertEquals("expect one entry in event queue", EventBus.getInstance().size(), 1);
		EventBus.getInstance().processEvents();
		assertEquals("expect one entry in event queue", EventBus.getInstance().size(), 1);
		EventBus.getInstance().registerEventHandler(dummy);
		assertFalse("don't expect handler to be called jet", dummyHandler);
		EventBus.getInstance().processEvents();
		assertEquals("expect one entry in event queue", EventBus.getInstance().size(), 1);
		assertTrue("expect handler to have recieved event", dummyHandler);

		Thread.sleep(delay);
		EventBus.getInstance().processEvents();
		assertEquals("expect empty event queue", EventBus.getInstance().size(), 0);
	}

}
