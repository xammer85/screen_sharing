package com.hammer.screen.structure;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hammer.screen.ScreenUtils;

public class SplittedScreen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int width;
	private final int height;
	private final int imageType;
	
	private final Map<ScreenIndex, ScreenPart> screenMap;
	
	public SplittedScreen(int width, int height, int imageType) {
		this.screenMap = new HashMap<ScreenIndex, ScreenPart>();
		this.width = width;
		this.height = height;
		this.imageType = imageType;
	}
	
	public SplittedScreen(BufferedImage screenImg) {
		this(screenImg.getWidth(), screenImg.getHeight(), screenImg.getType());
	}
	
	public boolean isEmpty(){
		return screenMap.isEmpty();
	}
	
	public void set(ScreenPart sPart) {
		screenMap.put(sPart.getScreenPosition().getIdx(), sPart);
	}

	
	public ScreenPart getByIndex(ScreenIndex idx){
		return screenMap.get(idx);
	}
	
	public Collection<ScreenPart> getAllParts(){
		return screenMap.values();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getImageType() {
		return imageType;
	}
	
	public void fillImageBytes(){
		for (ScreenPart screenPart : screenMap.values()) {
			ScreenPartImage spi = screenPart.getImage();
			
			spi.setImgBytes(
					ScreenUtils.toBytes(spi.getImg())
					);
		}
	}
	
	public void restoreImageFromBytes(){
		for (ScreenPart screenPart : screenMap.values()) {
			ScreenPartImage spi = screenPart.getImage();
			
			spi.setImg(ScreenUtils.fromBytes(spi.getImgBytes()));

		}
	}
}
