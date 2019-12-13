package com.saumi.graphics;

import com.saumi.maths.Vector3f;

public class Vertex {
	
	private Vector3f position;
	
	public Vertex(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}

	public Vector3f getPosition() {
		return position;
	}

}
