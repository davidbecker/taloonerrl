package de.brainstormsoftworks.taloonerrl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.brainstormsoftworks.taloonerrl.actors.ActorFactory;
import de.brainstormsoftworks.taloonerrl.actors.EActorTypes;

public class TestActorFactory {

	@Test
	public void test() {
		assertNotNull("player expected, but no actor created",
				ActorFactory.createActor(EActorTypes.PLAYER));
	}

}
