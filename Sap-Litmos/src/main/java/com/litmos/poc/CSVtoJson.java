package com.litmos.poc;

import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;

public class CSVtoJson {
	
	  public static void main(String[] args) throws Exception {
		  @SuppressWarnings("deprecation")
		String json = IOUtils.toString(new FileInputStream("D:\\litmos_ff\\convertcsv.json"));
		  System.out.println(json);
	}
}
	
