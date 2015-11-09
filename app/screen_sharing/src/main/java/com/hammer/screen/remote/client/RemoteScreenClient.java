package com.hammer.screen.remote.client;

import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;

import com.hammer.screen.ScreenUtils;
import com.hammer.screen.structure.SplittedScreen;

public class RemoteScreenClient {

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private SplittedScreen remoteScreen;

	private BufferedImage currScreenImg;
	
	private AtomicBoolean screenChanged =new AtomicBoolean(false);
	
	private boolean hasError = false;
	

	public RemoteScreenClient(Socket socket){
		currScreenImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.socket = socket;
		remoteScreen = null;

		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setScreenImage(BufferedImage screenImage){
		synchronized (currScreenImg) {
			currScreenImg = screenImage;
		}
		screenChanged.set(true);
	}

	public Socket getSocket() {
		return socket;
	}
	
	public boolean isHasError() {
		return hasError;
	}
	
	public InputEvent readEvent(){
		InputEvent ret = null;
		try {
			ret = (InputEvent) ois.readObject();
		} catch (Exception e){
			e.printStackTrace();
			error();
		}
		return ret;
	}

	public void sendScreen(){

		if(!screenChanged.get()){
			//System.out.println("Screen not changed. Nothing to send.");
			return;
		}
		
		SplittedScreen currentScreen = null;
		
		synchronized(currScreenImg){
			currentScreen = ScreenUtils.split(currScreenImg);
		}
		
		SplittedScreen screenToSend = currentScreen;
		
		if(remoteScreen != null) 
			screenToSend = ScreenUtils.diff(remoteScreen, currentScreen);
		
		remoteScreen = currentScreen;
		screenChanged.set(false);

		if(!screenToSend.isEmpty()){
			
			screenToSend.fillImageBytes();
			
			try {
				oos.writeObject(screenToSend);
			} catch (IOException e) {
				e.printStackTrace();
				error();
			}
		}
	}
	
	private void error(){
		hasError = true;
		IOUtils.closeQuietly(ois);
		IOUtils.closeQuietly(oos);
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
