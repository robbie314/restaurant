package org.launchcode.blogz.models;

public abstract class Entity {

	private final int uid;
	private static int nextId = 1;
	
	public Entity() {
		this.uid = nextId;
		nextId++;
	}
	
	public int getUid() {
		return this.uid;
	}
	
}
