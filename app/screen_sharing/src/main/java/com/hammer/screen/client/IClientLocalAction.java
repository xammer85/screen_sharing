package com.hammer.screen.client;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface IClientLocalAction {

	void onKeyType(KeyEvent e);
	
	void onMouseClick(MouseEvent e);
}
