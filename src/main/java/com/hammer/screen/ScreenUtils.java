package com.hammer.screen;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.hammer.screen.markup.ScreenMarkup;
import com.hammer.screen.markup.ScreenMarkupItem;
import com.hammer.screen.structure.ScreenIndex;
import com.hammer.screen.structure.ScreenPart;
import com.hammer.screen.structure.ScreenPartImage;
import com.hammer.screen.structure.ScreenPosition;
import com.hammer.screen.structure.SplittedScreen;

public class ScreenUtils {

	//private static final String DIGEST_ALGORITHM="MD5";

	public static final String JPEG_FORMAT="JPEG";
	public static final String PNG_FORMAT="PNG";
	public static final String GIF_FORMAT="GIF";

	/**
	 * By default value is {@link ScreenUtils#JPEG_FORMAT}
	 * @return
	 */
	public static String getDefaultImgFormat(){
		return JPEG_FORMAT;
	}

	/**
	 * By default format is {@link ScreenUtils#getDefaultImgFormat()}
	 * @param img
	 * @return
	 */
	public static int calculateImgHash(BufferedImage img) {
		return calculateHash(toBytes(img));
	}

	public static int calculateImgHash(BufferedImage img, String format) {
		return calculateHash(toBytes(img, format));
	}

	public static int calculateHash(byte[] data) {
		return Arrays.hashCode(data);
	}

	public static byte[] toBytes(BufferedImage img) {
		return toBytes(img, getDefaultImgFormat());
	}
	
	public static byte[] toBytes(BufferedImage img, String format) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ImageIO.write(img, format, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] ret = baos.toByteArray();

		IOUtils.closeQuietly(baos);

		return ret;
	}
	
	public static void writeToFile(BufferedImage img, String path) {
		try {
			ImageIO.write(img, getDefaultImgFormat(), new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage fromBytes(byte[] data){
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		BufferedImage ret = null;
		try {
			ret = ImageIO.read(bais);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static ScreenMarkup markup(BufferedImage screenImg, int xRange, int yRange){
		long initTime = System.currentTimeMillis();
		
		int totalWith = screenImg.getWidth();
		int totalHeight = screenImg.getHeight();

		ScreenMarkup ret = new ScreenMarkup(screenImg);

		int xIdx = 0;
		int yIdx = 0;

		int currY=0;

		int curXRange = xRange;
		int curYRange = yRange;

		while(currY < totalHeight){

			int currX=0;

			int yDiff = totalHeight-currY;
			if(yDiff < yRange)
				curYRange = yDiff;
			else
				curYRange = yRange;

			while(currX < totalWith){

				int xDiff = totalWith-currX;
				if(xDiff < xRange)
					curXRange = xDiff;
				else
					curXRange=xRange;

				
				ScreenIndex sIdx = new ScreenIndex(xIdx, yIdx);
				ScreenPosition screePos = new ScreenPosition(currX, currY, sIdx);
				
				ScreenMarkupItem markupItem = new ScreenMarkupItem(screePos, curXRange, curYRange);
				
				ret.add(markupItem);
				
				currX=currX+curXRange;

				xIdx++;
			}

			currY=currY+curYRange;
			yIdx++;
		}
		
		long finishTime = System.currentTimeMillis();
		//System.out.println("Markup time: "+(finishTime-initTime)+" ms.");
		
		return ret;
	}
	
	/**
	 * Optimal frame size for full hd resolution 100x100 px.
	 * @param screenImg
	 * @return
	 */
	public static SplittedScreen split(BufferedImage screenImg){
		return split(screenImg, 100, 100);
	}
	
	public static SplittedScreen split(BufferedImage screenImg, int xRange, int yRange){
		long initTime = System.currentTimeMillis();
		
		int totalWith = screenImg.getWidth();
		int totalHeight = screenImg.getHeight();

		SplittedScreen ret = new SplittedScreen(totalWith, totalHeight, screenImg.getType());

		int xIdx = 0;
		int yIdx = 0;

		int currY=0;

		int curXRange = xRange;
		int curYRange = yRange;

		while(currY < totalHeight){

			int currX=0;

			int yDiff = totalHeight-currY;
			if(yDiff < yRange)
				curYRange = yDiff;
			else
				curYRange = yRange;

			while(currX < totalWith){

				int xDiff = totalWith-currX;
				if(xDiff < xRange)
					curXRange = xDiff;
				else
					curXRange=xRange;

				BufferedImage screePartImg = screenImg.getSubimage(currX, currY, curXRange, curYRange);

				ScreenIndex sIdx = new ScreenIndex(xIdx, yIdx);
				ScreenPosition screePos = new ScreenPosition(currX, currY, sIdx);

				ScreenPartImage spi = new ScreenPartImage(screePartImg, calculateImgHash(screePartImg));
				ScreenPart screenPart = new ScreenPart(screePos, spi);
				ret.set(screenPart);

				currX=currX+curXRange;

				xIdx++;
			}

			currY=currY+curYRange;
			yIdx++;
		}
		
		long finishTime = System.currentTimeMillis();
		//System.out.println("Split time: "+(finishTime-initTime)+" ms.");
		
		return ret;
	}

	public static SplittedScreen diff(SplittedScreen screen1, SplittedScreen screen2){
		long initTime = System.currentTimeMillis();
		
		SplittedScreen ret = new SplittedScreen(screen1.getWidth(), screen1.getHeight(), screen1.getImageType());

		for (ScreenPart newScreenPart : screen2.getAllParts()) {
			ScreenPart existedScreenPart = screen1.getByIndex(newScreenPart.getScreenPosition().getIdx());
			if(!existedScreenPart.getImage().equals(newScreenPart.getImage()))
				ret.set(newScreenPart);
		}
		
		long finishTime = System.currentTimeMillis();
		//System.out.println("Diff time: "+(finishTime-initTime)+" ms.");
		
		return ret;
	}
	
	public static void applyDiff(SplittedScreen screen, SplittedScreen diffScreen) {
		for (ScreenPart changedPart : diffScreen.getAllParts()) {
			screen.set(changedPart);
		}
	}

	public static BufferedImage merge(SplittedScreen screen) {
		long initTime = System.currentTimeMillis();
		
		BufferedImage ret = new BufferedImage(screen.getWidth(), screen.getHeight(), screen.getImageType());

		Graphics graphics = ret.getGraphics();

		for (ScreenPart screenPart : screen.getAllParts()) {
			
			graphics.drawImage(
					screenPart.getImage().getImg(),
					screenPart.getScreenPosition().getX(),
					screenPart.getScreenPosition().getY(), null
					);
			
		}
		
		ret.flush();
		
		long finishTime = System.currentTimeMillis();
		//System.out.println("Merge time: "+(finishTime-initTime)+" ms.");
		
		return ret;
	}

}
