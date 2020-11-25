package infometry;

public class CDATAParse {
	public static void main(String[] args) {

		String str = "Nag Dinamani, \"Infometry INC, BTM\", Bengaluru, \"Karnataka\", India, 20 Employees";
		
		String regEx = "\""; 
		String strs[] = str.split(regEx);
		for (int i = 0; i < strs.length; i ++) {
			if (!strs[i].trim().isEmpty()) {
				strs[i].split("");
			}
			System.out.println(strs[i]);
		}
		
	}

}
