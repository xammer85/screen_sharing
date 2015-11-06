package com.hammer.screen.structure;

import java.io.Serializable;

public class ScreenPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScreenIndex idx;
	
	private int x;
	private int y;
	
	public ScreenPosition(int x, int y, ScreenIndex idx){
		this.x = x;
		this.y = y;
		this.idx=idx;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ScreenIndex getIdx() {
		return idx;
	}
}
