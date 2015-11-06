package com.hammer.screen.structure;

import java.io.Serializable;

public class ScreenPart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScreenPosition screenPosition;

	private ScreenPartImage image;

	public ScreenPart(ScreenPosition screenPosition, ScreenPartImage image) {
		this.image = image;
		this.screenPosition = screenPosition;
	}

	public ScreenPosition getScreenPosition() {
		return screenPosition;
	}

	public ScreenPartImage getImage() {
		return image;
	}	
}
