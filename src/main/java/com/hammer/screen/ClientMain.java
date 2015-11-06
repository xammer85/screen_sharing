package com.hammer.screen;

import javax.swing.JFrame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.hammer.screen.client.ScreenClientProcessor;

public class ClientMain {

	public static void main(String[] args) {
		
		Options opt = new Options();
		opt.addOption("host", true, "Screen server host");
		opt.addOption("port", true, "Screen server port");
		
		CommandLineParser cliParser = new DefaultParser();
		CommandLine cmd=null;
		try {
			cmd = cliParser.parse(opt, args);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if(cmd == null) 
			return;
		
		String host = cmd.getOptionValue("host");
		int port = Integer.parseInt(cmd.getOptionValue("port"));
				
		ScreenClientProcessor scp = new ScreenClientProcessor(host, port);
		
		
//		JFrame frame = new JFrame("Remote screen");
//		
//		//2. Optional: What happens when the frame closes?
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		//3. Create components and put them in the frame.
//		//...create emptyLabel...
//		//frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
//
//		//4. Size the frame.
//		frame.pack();
//
//		
//		//5. Show it.
//		frame.setVisible(true);
		
		
		Thread scpThread = new Thread(scp);
		scpThread.start();
		try {
			scpThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
