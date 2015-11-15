package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

public class FileList extends Observable{
	
	private ArrayList<String> names = new ArrayList<>();
	private File folder = new File("images/");
	private String fileName = "images/";
	
	public FileList() {
		
		generateList();
	}

	private void generateList() {
		File[] listOfFiles = folder.listFiles();
	
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  names.add(listOfFiles[i].getName());  
		      }
		    }
	}
	
	public void clearList(){
		this.names.clear();
		generateList();
		
		notifyObservers();
	}
	
	public ArrayList<String> getNames() {
		return names;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
		notifyObservers();
	}
	
	public String getFileName() {
		return fileName;
	}
	

}
