package com.saumi.engine;

public class Launcher {
	
	private Application application;
	private Thread mainThread;
	private boolean running = false;
	
	public synchronized void start() {
		if (this.running) return;
		this.running = true;
		
		this.mainThread  = new Thread(this.application, "main_thread");
		this.mainThread.start();		
	}
	
	public synchronized void stop() {
		if (!running) return;
		this.running = false;
		
		try {
			this.mainThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Launcher launcher = new Launcher();
		
		launcher.application = new Application();
		
		launcher.start();
	}
	
}