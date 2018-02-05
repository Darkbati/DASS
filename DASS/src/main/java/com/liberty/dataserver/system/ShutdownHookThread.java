package com.liberty.dataserver.system;

public class ShutdownHookThread extends Thread {

	public void run() {
		System.out.println("Program Shutdown");
	}

}
