package main;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import ui.FileList;
import ui.FileMenu;
import ui.MainPanel;

public class CVProject {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500,400);
		frame.setTitle("Image Filtering - Computer Vision Project 2015");
		
		FileList list = new FileList();
	    FileMenu menu = new FileMenu(list);
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		
		frame.setJMenuBar(bar);

		JPanel mainPanel = new MainPanel(list);
		
		frame.getContentPane().add(mainPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
