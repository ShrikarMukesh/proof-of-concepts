package com.hubspot;
import java.io.File;
import java.io.IOException;

public class FileCheck {

	public static void main(String[] args) {  
		try {  
			File myObj = new File("D:\\filename.txt");  
			if (myObj.createNewFile()) {  
				System.out.println("File created: " + myObj.getName());  
				System.out.println("Absolute path: " + myObj.getAbsolutePath());  
			} else {  
				System.out.println("File already exists.");  
			}  
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();  
		}  
	} 
}
