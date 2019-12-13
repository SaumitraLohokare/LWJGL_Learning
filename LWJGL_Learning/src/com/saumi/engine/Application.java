package com.saumi.engine;

import org.lwjgl.glfw.GLFW;

import com.saumi.graphics.Mesh;
import com.saumi.graphics.Renderer;
import com.saumi.graphics.Shader;
import com.saumi.graphics.Vertex;

public class Application implements Runnable {
	
	private Window window;
	private final int WIDTH = 1280, HEIGHT = 760;
	
	private Shader shader;
	private Mesh rect = new Mesh(new Vertex[] {
			new Vertex(-0.5f, 0.5f, 0.0f),
			new Vertex(0.5f, 0.5f, 0.0f),
			new Vertex(0.5f, -0.5f, 0.0f),
			new Vertex(-0.5f, -0.5f, 0.0f)
	}, new int[] {
			0, 1, 2,
			0, 3, 2
	});
	
	private Renderer renderer;

	@Override
	public void run() {
		init();
		
		long last = System.nanoTime(), now;
		double delta = 0d;
		while(!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			{
				now = System.nanoTime();
				delta = (now - last) / 1000000000d;
				last = now;
			}
			
			update(delta);
			render();
			
			//if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
			//	window.setFullscreen(!window.isFullscreen());
			//}
		}
		
		destroy();
	}
	
	public void init() {
		window = new Window(WIDTH, HEIGHT, "Game");		
		window.create();
		
		shader = new Shader("/shaders/MainVertex.glsl", "/shaders/MainFragment.glsl");
		shader.create();
		
		renderer = new Renderer(shader);
		window.setClearColor(0.9f, 0.9f, 0.9f);
		
		rect.create();
	}
	
	public void update(double delta) {
		window.update(delta);
	
		if (Input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
			System.out.println("X : " + Input.getMouseX() + "\tY : " + Input.getMouseY());
		}
		
	}
	
	public void render() {
		renderer.renderMesh(rect);
		
		window.swapBuffers();
	}
	
	public void destroy() {
		shader.destroy();
		window.destroy();
	}
}
