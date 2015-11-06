package com.hammer.screen.client;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

public class ClientLocalScreen implements IClientLocalScreen {

	
	private final JFrame jFrame;
	private final JPanel jPanel;
	
	
	private ImageIcon imgIcon = null;
	
	private final ScreenClient client;
	
	public ClientLocalScreen(ScreenClient client){
		
		this.client = client;
		
		jFrame = new JFrame("Remote screen");
		jFrame.addKeyListener(getKeyListener());
		jFrame.setLayout(new BorderLayout(10, 10));
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jPanel = new JPanel(new BorderLayout());

	}


	public void updateScreen(BufferedImage screenImg) {
		
		if(imgIcon == null){
			
			imgIcon = new ImageIcon(screenImg);
			JLabel jLabel = new JLabel(imgIcon);
			
			jLabel.addMouseListener(getMouseListener());
			
			jPanel.add(new JScrollPane(jLabel));
			
			jFrame.add(jPanel);
			jFrame.setLocationRelativeTo(null);
			jFrame.setVisible(true);
			jFrame.pack();
		} else 
			imgIcon.setImage(screenImg);
		
		
		jPanel.repaint();

	}
	
	private  MouseListener getMouseListener(){
		return new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				//System.out.println("mouseReleased "+e);
				client.sendLocalAction(e);
			}
			
			public void mousePressed(MouseEvent e) {
				//System.out.println("mousePressed "+e);
				client.sendLocalAction(e);
			}
			
			public void mouseExited(MouseEvent e) {
				//System.out.println("mouseExited "+e);
				client.sendLocalAction(e);
			}
			
			public void mouseEntered(MouseEvent e) {
				//System.out.println("mouseEntered "+e);
				client.sendLocalAction(e);
			}
			
			public void mouseClicked(MouseEvent e) {
				// here
				//System.out.println("mouseClicked "+e);
				client.sendLocalAction(e);
			}
		};
	}
	
	private KeyListener getKeyListener(){
		return new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				//here
				//System.out.println("keyTyped "+e);
				//client.sendLocalAction(e);
			}
			
			public void keyReleased(KeyEvent e) {
				client.sendLocalAction(e);
				//System.out.println("keyReleased "+e);
			}
			
			public void keyPressed(KeyEvent e) {
				client.sendLocalAction(e);
				//System.out.println("keyPressed "+e);
			}
		};
	}

}
