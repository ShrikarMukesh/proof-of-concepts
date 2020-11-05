package infometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManiPulation {
	
	public static void main(String[] args) {
	
		//String lineString = "\"1010 - SVB Operating\",1010,\"Singularity University\",=NA()*17342661.437575176,=NA()*13995687.431850009,=NA()*12691427.479066141,=NA()*13734613.595566161";
		
		String lineString = "Account Name,Account Code,Level Name,\"Workday Function\",\"Home Department\",\"Location\",01/2020,02/2020,03/2020,04/2020,05/2020,06/2020";
		StringBuilder sb = new StringBuilder();
		System.out.println(lineString);
		List<String> arrList = new ArrayList<>();
		
		for (int m = 0; m < lineString.length(); m++) {
			
			if(lineString.charAt(m) != ',') {			
				sb.append(lineString.charAt(m));
			}
			
			else {
                if(m -1 >= 0 && m+1 < lineString.length() -1) {
                	
                	if(lineString.charAt(m-1) != '"' && lineString.charAt(m+1) != '"' ) {
                		
    					sb.append(lineString.charAt(m));
    				}
    				else {
    					arrList.add(sb.toString());
    					sb = new StringBuilder();
    				}
                }
                				
			}
			if(m == lineString.length() -1) {
				
				String lastValue = sb.toString();
				lastValue = lastValue.replace("=NA()*", "");
				
				String[] lastArray = lastValue.split(",");
				arrList.addAll(Arrays.asList(lastArray));
			}
		}
		arrList.forEach((i)->System.out.print(i+"    "));
		
	}
}
