package com.hammer.screen.markup;

import com.hammer.screen.structure.ScreenPosition;

public class ScreenMarkupItem {

	private ScreenPosition screenPosition;
	private int xRange;
	private int yRange;
	
	
	public ScreenMarkupItem(ScreenPosition screenPosition, int xRange, int yRange){
		this.screenPosition = screenPosition;
		this.xRange = xRange;
		this.yRange = yRange;
	}
	
	public ScreenPosition getScreenPosition() {
		return screenPosition;
	}
	
	public int getxRange() {
		return xRange;
	}
	public int getyRange() {
		return yRange;
	}
}
