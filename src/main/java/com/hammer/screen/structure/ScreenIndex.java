package com.hammer.screen.structure;

import java.awt.Point;
import java.io.Serializable;

public class ScreenIndex implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int xIdx = 0;
	
	int yIdx = 0;
	
	public ScreenIndex(int xIdx, int yIdx){
		this.xIdx = xIdx;
		this.yIdx = yIdx;
	}

	public int getxIdx() {
		return xIdx;
	}

	public void setxIdx(int xIdx) {
		this.xIdx = xIdx;
	}

	public int getyIdx() {
		return yIdx;
	}

	public void setyIdx(int yIdx) {
		this.yIdx = yIdx;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xIdx;
		result = prime * result + yIdx;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScreenIndex other = (ScreenIndex) obj;
		if (xIdx != other.xIdx)
			return false;
		if (yIdx != other.yIdx)
			return false;
		return true;
	}
	
}
