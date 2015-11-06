package com.hammer.screen;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.hammer.screen.remote.client.RemoteScreenClientManager;
import com.hammer.screen.remote.client.RemoteScreenClientServer;
import com.hammer.screen.structure.SplittedScreen;

public class ScreenRobot implements Runnable {

	
	private static final int X_RANGE=100;
	private static final int Y_RANGE=100;
	
	private boolean stop = false;
	private int frameCount = 0;
	
	private Thread clientServerThread;

	private Robot robot;
	
	private final RemoteScreenClientServer clientServer;
	private Rectangle curScreenRect;
	
	public ScreenRobot(int serverPort){
		clientServer = new RemoteScreenClientServer(serverPort);
		clientServerThread = new Thread(clientServer);
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		curScreenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
	public Robot getRobot() {
		return robot;
	}
	
	
	public void run() {
		try {
			while (!stop) {
				long initTime = System.currentTimeMillis();

				BufferedImage screenImg = robot.createScreenCapture(curScreenRect);

				clientServer.getRemoteClientManager().setCurrentScreenImg(screenImg);
				
				frameCount=frameCount+1;
				
				long finishTime = System.currentTimeMillis();
				//System.out.println("Rander frame "+frameCount+" time: "+(finishTime-initTime)+" ms.");

			}
			
			System.out.println("Screen robot work is finished. Rendered frames count: "+frameCount);

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void startNetworkServer(){
		clientServerThread.start();
	}
	
	public synchronized void stop(){
		stop=true;
	}
}
