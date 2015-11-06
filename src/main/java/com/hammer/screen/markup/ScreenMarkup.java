package com.hammer.screen.markup;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ScreenMarkup {

	private final List<ScreenMarkupItem> markupData;
	private BufferedImage screenImg;
	
	private int fromIdx=0;
	
	private boolean done = false;
	
	public ScreenMarkup(BufferedImage screenImg){
		markupData = new ArrayList<ScreenMarkupItem>();
		this.screenImg = screenImg;
	}
	
	public BufferedImage getScreenImg() {
		return screenImg;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public List<ScreenMarkupItem> getNextPortion(int count){
		
		if(isDone())
			return new ArrayList<ScreenMarkupItem>();
		
		int toIndex = fromIdx+count;
		
		if(toIndex >= markupData.size()){
			toIndex = markupData.size();
			done = true;
		}
		
		List<ScreenMarkupItem> ret= markupData.subList(fromIdx, toIndex);
		
		fromIdx=toIndex;
		
		return ret;
	}
	
	public void add(ScreenMarkupItem item) {
		markupData.add(item);
	}
	
	public void resetIndex() {
		fromIdx = 0;
		done = false;
	}
}
