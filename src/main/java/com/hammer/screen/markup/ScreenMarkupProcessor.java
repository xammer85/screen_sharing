package com.hammer.screen.markup;

import java.awt.image.BufferedImage;
import java.lang.Thread.State;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hammer.screen.ScreenUtils;
import com.hammer.screen.structure.ScreenPart;
import com.hammer.screen.structure.ScreenPartImage;
import com.hammer.screen.structure.ScreenPosition;
import com.hammer.screen.structure.SplittedScreen;

public class ScreenMarkupProcessor {

	private final ScreenMarkup markup;

	public ScreenMarkupProcessor(ScreenMarkup markup){
		this.markup = markup;
	}

	public SplittedScreen split(int postionSize) {
		long initTime = System.currentTimeMillis();
		final SplittedScreen ret = new SplittedScreen(markup.getScreenImg());

		int threadCount = 0;
		while(!markup.isDone()){
			
			final List<ScreenMarkupItem> portion = markup.getNextPortion(postionSize);

			Runnable processor = new Runnable() {

				public void run() {
					
					long initTime = System.currentTimeMillis();
					
					BufferedImage screenImg = markup.getScreenImg();
					for (ScreenMarkupItem screenMarkupItem : portion) {

						ScreenPosition screenPos = screenMarkupItem.getScreenPosition();

						BufferedImage screePartImg = screenImg.getSubimage(screenPos.getX(),
								screenPos.getY(),
								screenMarkupItem.getxRange(), 
								screenMarkupItem.getyRange());



						ScreenPartImage spi = new ScreenPartImage(screePartImg, ScreenUtils.calculateImgHash(screePartImg));
						ScreenPart screenPart = new ScreenPart(screenPos, spi);
						ret.set(screenPart);
						
						long finishTime = System.currentTimeMillis();
						//System.out.println("Process markup time: "+(finishTime-initTime)+" ms.");
					}
				}
			};
			
			Thread processThread = new Thread(processor);
			processThread.start();
			
			threadCount=threadCount+1;
			
			try {
				processThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

		long finishTime = System.currentTimeMillis();
		System.out.println("Process all markup time: "+(finishTime-initTime)+" ms Threads: "+threadCount);
		
		return ret;
	}
	

}
