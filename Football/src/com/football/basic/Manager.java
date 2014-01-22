package com.football.basic;

public class Manager {
	private String name;

	public Manager() {
	}
	
	public Manager(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
