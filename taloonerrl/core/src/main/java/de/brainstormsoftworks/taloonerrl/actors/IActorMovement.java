package de.brainstormsoftworks.taloonerrl.actors;

public interface IActorMovement {

	void move(int dX, int dY);

	int getXPosition();

	int getYPosition();

}