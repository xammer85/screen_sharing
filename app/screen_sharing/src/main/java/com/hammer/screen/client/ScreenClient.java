package com.hammer.screen.client;

import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.hammer.screen.ScreenUtils;
import com.hammer.screen.structure.SplittedScreen;

public class ScreenClient {

	private Socket socket;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private SplittedScreen  serverScreen = null;
	
	private String host;
	private int port;

	public ScreenClient(String host, int port){
		this.host = host;
		this.port = port;

	}

	public boolean connect(){
		try {
			System.out.println("Try connect to remote screen server "+host+":"+port);
			this.socket = new Socket(host, port);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			return true;
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	public BufferedImage getRemoteScreen(){
		SplittedScreen recivedScreen = null;

		try {
			recivedScreen = (SplittedScreen) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(recivedScreen == null)
			return null;

		recivedScreen.restoreImageFromBytes();

		if(serverScreen == null)
			serverScreen = recivedScreen;
		else 
			ScreenUtils.applyDiff(serverScreen, recivedScreen);
		
		return ScreenUtils.merge(serverScreen);

	}
	
	public synchronized void sendLocalAction(InputEvent e){
		try {
			oos.writeObject(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
