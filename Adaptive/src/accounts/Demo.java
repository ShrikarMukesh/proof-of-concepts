package accounts;

public class Demo {
	public static void main(String[] args) {
		String s = "03 - Personnel Headcount, Personnel.Headcount, 10 - US, Analytics and Data Services, 277 - Product Dev - Predictive Analytics, Remote (CO, United States), 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0";
	   
		String[] arr = s.split(",");
	    System.out.println(arr.length);
	    for(String str: arr) {
	    	System.out.println(str);
	    }
	}
}
