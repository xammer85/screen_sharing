package com.hammer.screen.remote.client;

import java.io.IOException;
import java.net.SocketAddress;

public class RemoteScreenClientProcessor implements Runnable {

	private final RemoteScreenClient client;
	
	private boolean disconnect = false;
	
	public RemoteScreenClientProcessor(RemoteScreenClient client){
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
			
			client.sendScreen();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
