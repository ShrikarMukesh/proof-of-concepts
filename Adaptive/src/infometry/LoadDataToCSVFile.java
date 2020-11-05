package infometry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class LoadDataToCSVFile {
	public static void main(String[] args) {
		
		File file = new File("E:\\Adaptivedata\\Demo.csv");
		String path = null;
		boolean result;  
		try  { 

			result = file.createNewFile();  
			if(result)   {  
				System.out.println("file created "+file.getCanonicalPath());  
				path = file.getCanonicalPath();
			}  else  {  
				System.out.println("File already exist at location: "+file.getCanonicalPath());  
			}  
		}catch (IOException e){  
			e.printStackTrace();
		}     
		writeDataLineByLine(path);
	}
	public static void writeDataLineByLine(String filePath) { 

		File file = new File(filePath); 
		try { 			
			FileWriter outputfile = new FileWriter(file); 

			CSVWriter writer = new CSVWriter(outputfile); 

			
			String output ="Account Name,Account Code,Level Name,01/2020,02/2020,03/2020,04/2020,05/2020,06/2020,07/2020";
			String[] hArray = output.split(",");
			writer.writeNext(hArray); 

			String data1 ="\"Speaker Bureau\",C_Billings_Speaker_Bureau,\"1001 - Speaker's Bureau\",187500.0,250000.0,250000.0,250000.0,250000.0,250000.0,250000.0";
			String[] OArray1 = data1.split(",");
			writer.writeNext(OArray1); 
			
			String data2 = "\"GSP / Co-horts\",C_Billings_GSP_Co_Horts,\"1100 - GSP-Global Solutions Program (Inactive)\",200000.0,0.0,200000.0,0.0,0.0,0.0,200000.0";
			String[] OArray2 = data2.split(",");
			writer.writeNext(OArray2); 
			
			String data3 = "\"Speaker Bureau\",C_Bookings_Speaker_Bureau,\"1001 - Speaker's Bureau\",187500.0,250000.0,250000.0,250000.0,250000.0,250000.0,250000.0";
			String[] OArray3 = data3.split(",");
			writer.writeNext(OArray3); 
			
			String data4 = "\"GSP / Co-horts\",C_Bookings_GSP_Co_Horts,\"1100 - GSP-Global Solutions Program (Inactive)\",200000.0,0.0,200000.0,0.0,0.0,0.0,200000.0";
			String[] OArray4 = data4.split(",");
			writer.writeNext(OArray4); 
			
			String data5 = "\"Global Summit\",C_Billings_Global_Summit,\"1150 - US Summits & Events\",0.0,0.0,0.0,3690000.0,0.0,0.0,2833600.0";
			String[] OArray5 = data5.split(",");
			writer.writeNext(OArray5); 
			
			String data6 = "\"Global Summit\",C_Bookings_Global_Summit,\"1150 - US Summits & Events\",0.0,0.0,0.0,3690000.0,0.0,0.0,2833600.0";
			String[] OArray6 = data6.split(",");
			writer.writeNext(OArray6); 
			
			String data7 = "\"International Summits\",C_Billings_International_Summits,\"1160 - International Summits\",200000.0,0.0,100000.0,100000.0,0.0,400000.0,100000.0";
			String[] OArray7 = data7.split(",");
			writer.writeNext(OArray7);
			
			String data9 = "\"Digital online courses (Billed upon Bookings)\",C_Billings_Digital_Online_Courses,\"1500 - Online Courses\",50000.0,50000.0,50000.0,50000.0,50000.0,100000.0,100000.0";
			String[] OArray9 = data9.split(",");
			writer.writeNext(OArray9); 
			
			String data10 = "\"Digital online courses (Booked upon Billings)\",C_Bookings_Digital_Online_Courses,\"1500 - Online Courses\",50000.0,50000.0,50000.0,50000.0,50000.0,100000.0,100000.0";
			String[] OArray10 = data10.split(",");
			writer.writeNext(OArray10); 
			
			String data11 = "\"Uncommon_Partners\",C_Billings_Uncommon_Partners,\"1900 - Uncommon Partners\",200000.0,200000.0,200000.0,200000.0,200000.0,200000.0,200000.0";
			String[] OArray11 = data11.split(",");
			writer.writeNext(OArray11); 
			
			writer.close(); 
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
		} 
	} 
}
