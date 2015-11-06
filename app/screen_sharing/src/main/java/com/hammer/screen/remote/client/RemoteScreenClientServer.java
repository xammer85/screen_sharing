package com.hammer.screen.remote.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteScreenClientServer implements Runnable {

	private ServerSocket ss;

	private boolean stop = false;

	private final RemoteScreenClientManager remoteClientManager;

	private final int port;


	public RemoteScreenClientServer(int port){
		this.port = port;
		remoteClientManager = new RemoteScreenClientManager();
	}

	public void run() {

		try {
			ss = new ServerSocket(port);
			System.out.println("Started remote screen client server at "+port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if(ss == null){
			return;
		}
		
		while(!stop){
			try {
				Socket clientSocket = ss.accept();
				remoteClientManager.addClient(clientSocket);
				System.out.println("Register new remote client "+clientSocket.getRemoteSocketAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public RemoteScreenClientManager getRemoteClientManager() {
		return remoteClientManager;
	}

	public void stop(){
		this.stop = true;
	}
}
