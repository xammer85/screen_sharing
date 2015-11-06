package com.hammer.screen;

public class ScreenSystemSettings {

	private static volatile ScreenSystemSettings instance;
	
	public static ScreenSystemSettings getInstance() {
		return instance;
	}
}
