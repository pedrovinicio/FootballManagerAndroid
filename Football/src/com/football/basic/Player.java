package com.football.basic;

import com.football.R;

public class Player {

	public static enum Position {
		GOALKEEPER, DEFENDER, MIDFIELD, FORWARD
	};

	public static enum State {
		SELECTED, RESERVE, OUT
	};

	private String name;
	private Position position;
	private int positionAsStringId;
	private int strenght;

	private State state;

	public Player() {
		this.state = State.OUT;
	}

	public Player(String name, Position position, int strenght) {
		this.setName(name);
		this.setPosition(position);
		this.setStrenght(strenght);

		this.state = State.OUT;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPositionAsStringId() {
		return positionAsStringId;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;

		if (position == Position.GOALKEEPER) {
			this.positionAsStringId = R.string.goalkeeper;
		} else if (position == Position.DEFENDER) {
			this.positionAsStringId = R.string.defender;
		} else if (position == Position.MIDFIELD) {
			this.positionAsStringId = R.string.midfield;
		} else if (position == Position.FORWARD) {
			this.positionAsStringId = R.string.forward;
		} else {
			//TODO: throw exception
		}
	}

	public int getStrenght() {
		return strenght;
	}

	public void setStrenght(int strenght) {
		this.strenght = strenght;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
