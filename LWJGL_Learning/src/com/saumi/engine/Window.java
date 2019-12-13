package com.saumi.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.saumi.maths.Vector3f;

public class Window {
	
	private int WIDTH, HEIGHT;
	private int[] windowPosX = new int[1], windowPosY = new int[1];
	private String title;
	private Input input;
	private GLFWWindowSizeCallback sizeCallback;
	private boolean isResized, isFullscreen;
	
	private long window;
	
	private Vector3f RGB;
	
	public Window(int WIDTH, int HEIGHT, String title) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.title = title;
		
		RGB = new Vector3f();
	}
	
	public void create() {
		if (!GLFW.glfwInit()) 
			throw new RuntimeException("Failer to initialize GLFW...");
		
		input = new Input();
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		
		// TODO : do i need resizing?
		//GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, title, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
		
		if (window == GLFW.GLFW_FALSE) 
			throw new RuntimeException("Failed to create window...");
		
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		windowPosX[0] = (vidmode.width() - WIDTH) / 2;
		windowPosY[0] = (vidmode.height() - HEIGHT) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST); // TODO do i need?
		
		createCallbacks();
		
		GLFW.glfwSwapInterval(1);
		
		GLFW.glfwShowWindow(window);
	}
	
	private void createCallbacks() {
		sizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int w, int h) {
				WIDTH = w;
				HEIGHT = h;
				isResized = true;
			}
		};
		
		GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
		GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
		GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
	}

	double timer = 0;
	public void update(double delta) {
		if (isResized) {
			GL11.glViewport(0,  0,  WIDTH,  HEIGHT);
			isResized = false;
		}
		GL11.glClearColor(RGB.getX(), RGB.getY(), RGB.getZ(), 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GLFW.glfwPollEvents();
		timer += delta;
		if (timer >= 1) {
			GLFW.glfwSetWindowTitle(window, title + " | FPS: " + String.valueOf((int)(1/delta)));
			timer = 0;
		}
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public void destroy() {
		input.destroy();
		sizeCallback.free();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}
	
	public void setClearColor(float r, float g, float b) {
		RGB.set(r, g, b);
	}

	public boolean isFullscreen() {
		return isFullscreen;
	}

	public void setFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
		isResized = true;
		if (isFullscreen) {
			GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, WIDTH, HEIGHT, 0);
		} else {
			GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], WIDTH, HEIGHT, 0);
		}
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public String getTitle() {
		return title;
	}

	public long getWindow() {
		return window;
	}
	
}
