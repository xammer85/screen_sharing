package com.hammer.screen.remote.client;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.hammer.screen.ScreenRobot;

public class RemoteClientEventProcessor implements Runnable {

	private final RemoteScreenClient client;

	private boolean disconnect = false;
	private  Robot eventRobot;

	public RemoteClientEventProcessor(RemoteScreenClient client){
		try {
			this.eventRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		this.client = client;
	}

	public RemoteScreenClient getClient() {
		return client;
	}

	public void run() {

		while(!disconnect){

			if(client.isHasError()){
				// break on socket error
				break;
			}

			InputEvent e = client.readEvent();
			
			if(e instanceof MouseEvent){
				MouseEvent me = (MouseEvent) e;
				int meId = me.getID();
				int button = me.getButton();

				eventRobot.mouseMove(me.getX(), me.getY());


				
				if(meId == MouseEvent.MOUSE_PRESSED){
					
					if(button == MouseEvent.BUTTON1)
						eventRobot.mousePress(InputEvent.BUTTON1_MASK);
					
					if(button == MouseEvent.BUTTON2)
						eventRobot.mousePress(InputEvent.BUTTON2_MASK);
					
					if(button == MouseEvent.BUTTON3)
						eventRobot.mousePress(InputEvent.BUTTON3_MASK);
					
				} else if(meId == MouseEvent.MOUSE_RELEASED){
					if(button == MouseEvent.BUTTON1)
						eventRobot.mouseRelease(InputEvent.BUTTON1_MASK);
					
					if(button == MouseEvent.BUTTON2)
						eventRobot.mouseRelease(InputEvent.BUTTON2_MASK);
					
					if(button == MouseEvent.BUTTON2)
						eventRobot.mouseRelease(InputEvent.BUTTON2_MASK);
				}

			} else if( e instanceof KeyEvent){
				KeyEvent ke = (KeyEvent)e;

				int keId = ke.getID();
				int keyCode = ke.getKeyCode();

				if(keId == KeyEvent.KEY_PRESSED){
					eventRobot.keyPress(keyCode);
				}

				if(keId == KeyEvent.KEY_RELEASED){
					eventRobot.keyRelease(keyCode);
				}
			}

			System.out.println("Server has event: "+e);

		}

		try {
			System.out.println("Close remote connection "+client.getSocket().getRemoteSocketAddress());
			client.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect(){
		disconnect = true;
	}

}
