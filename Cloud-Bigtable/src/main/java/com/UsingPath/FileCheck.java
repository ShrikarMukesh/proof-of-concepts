package com.UsingPath;

import java.io.FileReader;

public class FileCheck {
	public static void main(String args[])throws Exception{    
        FileReader fr=new FileReader("D:\\Java\\info2019-1d36bbada351.json");    
        int i;    
        while((i=fr.read())!=-1)    
        System.out.print((char)i);    
        fr.close();    
  }    
}
