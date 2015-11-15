package ui;

import java.io.File;
import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		
		ArrayList<String> names = new ArrayList<>();

			File folder = new File("images/");
			File[] listOfFiles = folder.listFiles();
			

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			    	  names.add(listOfFiles[i].getName());  
			      }
			    }

			
			
			for (int i = 0; i < names.size(); i++) {
				System.out.println(names.get(i));
			}
	}

}
