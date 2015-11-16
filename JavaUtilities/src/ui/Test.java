package ui;

import java.io.File;
import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		
		String nome = "estate.pgm";
		
		String[] nomest = nome.split("\\.");
		
		for (int i = 0; i < nomest.length; i++) {
			System.out.println(nomest[i]);
			
		}
		
	}

}
