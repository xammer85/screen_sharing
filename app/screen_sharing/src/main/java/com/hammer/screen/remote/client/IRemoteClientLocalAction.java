package com.hammer.screen.remote.client;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface IRemoteClientLocalAction {

	void onKeyType(KeyEvent e);
	
	void onMouseClick(MouseEvent e);
}
