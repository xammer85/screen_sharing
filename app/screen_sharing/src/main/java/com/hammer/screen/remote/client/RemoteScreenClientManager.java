package com.hammer.screen.remote.client;

import java.awt.image.BufferedImage;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class RemoteScreenClientManager {

	private Set<RemoteScreenClientProcessor> clientProcessors;

	//private Set<RemoteClientEventProcessor> clienEventProcessors;
	
	public RemoteScreenClientManager(){
		clientProcessors = new HashSet<RemoteScreenClientProcessor>();
		//clienEventProcessors = new HashSet<RemoteClientEventProcessor>();
	}

	public void addClient(Socket clientSocket) {

		RemoteScreenClient client =new RemoteScreenClient(clientSocket);
		
		RemoteScreenClientProcessor clientProcessor = new RemoteScreenClientProcessor(client);
		RemoteClientEventProcessor clientEventProcessor = new RemoteClientEventProcessor(client);

		
		Thread processorThread1 = new Thread(clientProcessor);
		clientProcessors.add(clientProcessor);
		processorThread1.start();
		
		Thread processorThread2 = new Thread(clientEventProcessor);
		//clienEventProcessors.add(clientEventProcessor);
		processorThread2.start();
	}
	

	public void setCurrentScreenImg(BufferedImage currentScreenImg){
		for (RemoteScreenClientProcessor remoteScreenClientProcessor : clientProcessors) {
			
			if(remoteScreenClientProcessor.getClient().isHasError()){
				clientProcessors.remove(remoteScreenClientProcessor);
				continue;
			}
			
			remoteScreenClientProcessor.getClient().setScreenImage(currentScreenImg);
		}
	}

}
