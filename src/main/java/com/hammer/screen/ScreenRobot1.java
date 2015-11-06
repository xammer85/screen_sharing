package com.hammer.screen;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.hammer.screen.structure.SplittedScreen;

public class ScreenRobot1 implements Runnable {

	
	private static final int X_RANGE=100;
	private static final int Y_RANGE=100;
	
	private boolean stop = false;
	private int frameCount = 0;
	
	
	private ObjectOutputStream oos;
	
	public ScreenRobot1(OutputStream os){
		try {
			oos = new ObjectOutputStream(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			Robot robot = new Robot();
			Dimension currentScreen = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle curScreenRect = new Rectangle(currentScreen);

			SplittedScreen lastScreen = null;

			while (!stop) {
				long initTime = System.currentTimeMillis();

				BufferedImage screenImg = robot.createScreenCapture(curScreenRect);

				SplittedScreen resultScreen = null;
				
				SplittedScreen currScreen = ScreenUtils.split(screenImg, X_RANGE, Y_RANGE);
//				
//				ScreenMarkup markup = ScreenUtils.markup(screenImg, X_RANGE, Y_RANGE);
//				ScreenMarkupProcessor markupProcessor = new ScreenMarkupProcessor(markup);
//				currScreen = markupProcessor.split(30);
				 
				//BufferedImage result = screenImg;

				if(lastScreen !=null){
					resultScreen = ScreenUtils.diff(lastScreen, currScreen);
					//result = ScreenUtils.merge(diff);
				} else {
					resultScreen = currScreen;
				}

				//String path = "/home/hammer/Work/tmp/scr/"+frameCount+".jpeg";

				//ScreenUtils.writeToFile(result, path);


				frameCount=frameCount+1;
				lastScreen = currScreen;

				oos.writeObject(resultScreen);
				
				long finishTime = System.currentTimeMillis();

				System.out.println("Rander frame "+frameCount+" time: "+(finishTime-initTime)+" ms.");

				//Thread.sleep(500);
			}
			
			System.out.println("Screen robot work is finished. Rendered frames count: "+frameCount);

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public synchronized void stop(){
		stop=true;
	}
}
