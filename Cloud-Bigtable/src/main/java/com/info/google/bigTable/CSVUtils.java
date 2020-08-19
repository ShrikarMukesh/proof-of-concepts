package com.info.google.bigTable;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CSVUtils {

	private File csvFile;
	private String []headers;

	public CSVUtils (String filePath) throws Exception {

		String fileNotFoundExcep = "File Doesn't Exist";

		csvFile = new File(filePath);
		if (! csvFile.exists()) {
			throw new Exception(fileNotFoundExcep);
		}
		else if (! csvFile.isFile()) {
			throw new Exception(fileNotFoundExcep);
		}
		else if (! csvFile.getName().contains(".csv")) {
			throw new Exception(fileNotFoundExcep);
		}

		setHeaders();
	}


	private void setHeaders () throws Exception {
		Scanner fileScan = null;

		try {
			fileScan = new Scanner(csvFile);
			if (fileScan.hasNextLine()) {
				String headerLine = fileScan.nextLine();
				headers = headerLine.split(",");
			}
		}
		finally {
			if (fileScan != null) {
				fileScan.close();
			}
		}
	}

	public String[] getHeaders () {
		return this.headers;
	}


	public List<String []> getRows () throws Exception {
		Scanner fileScan = null;
		List<String []> rows = null;
		
		try {
			fileScan = new Scanner(csvFile);
			String headerLine = fileScan.nextLine();
			headers = headerLine.split(",");
			String row = null;
			String []columns = null;
			rows = new LinkedList<>();
			
			boolean isHeader = true;
			while (fileScan.hasNextLine()) {
				if (isHeader) {
					isHeader = false;
					continue;
				}

				row = fileScan.nextLine();
				columns = row.split(",");
				rows.add(columns);
			}
		}
		finally {
			if (fileScan != null) {
				fileScan.close();
			}
		}
		
		return rows;
	}
}
