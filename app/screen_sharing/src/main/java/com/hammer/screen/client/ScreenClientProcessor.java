package com.hammer.screen.client;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hammer.screen.ScreenUtils;

public class ScreenClientProcessor implements Runnable {

	private ScreenClient client;

	private int frame=0;
	
	private final IClientLocalScreen localScreen;

	public ScreenClientProcessor(String host, int port){
		client = new ScreenClient(host, port);
		localScreen = new ClientLocalScreen(client);
	}

	public void run() {

		if(!client.connect())
			return;

		while(true){
			BufferedImage remoteImg = client.getRemoteScreen();

			if(remoteImg != null)
				localScreen.updateScreen(remoteImg);


			frame=frame+1;
		}
	}

}
