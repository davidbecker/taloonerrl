package de.brainstormsoftworks.taloonerrl.actors;

import java.util.ArrayList;
import java.util.List;

import de.brainstormsoftworks.taloonerrl.internal.actors.Player;

public final class ActorFactory {
	private static List<IActor> actors = new ArrayList<IActor>();

	private ActorFactory() {
	}

	public static IActor createActor(EActorTypes type) {
		IActor newActor = null;
		switch (type) {
		case PLAYER:
			newActor = new Player();
			break;
		default:
			break;
		}
		actors.add(newActor);
		return newActor;
	}
}
