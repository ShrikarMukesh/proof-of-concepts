package basic;

import java.util.ArrayList;
import java.util.List;

public class GetContacts {
	public static void main(String[] args) {
		
		String name = String.join(" ", "my", "name","is","shrikar");
      	System.out.println(name);
      	
      	List<String> list = new ArrayList<String>();
      	list.add("my");
      	list.add("name is");
      	list.add("shrikar");
      	System.out.println(list);
      	String o = String.join(" ",list);
      	System.out.println(o);     	
	}
}
