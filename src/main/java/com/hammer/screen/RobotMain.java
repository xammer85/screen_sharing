package com.hammer.screen;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class RobotMain {


	public static void main(String[] args) {
		
		Options opt = new Options();
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
		
		int port = Integer.parseInt(cmd.getOptionValue("port"));
		
		ScreenRobot robot = new ScreenRobot(port);
		
		robot.startNetworkServer();
		
		Thread robotThread = new Thread(robot);
		robotThread.start();
		try {
			robotThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
