package collection;

import java.util.*;


public class Hash {
	public static void main(String[] args) {

		/*Hashtable<String, Integer> ht = new Hashtable<>();
      ht.put(null, 78);
      ht.put(null, 89);
      System.out.println(ht);*/

		HashMap<String, Integer> hm = new HashMap<>();
		hm.put(null, 78);
		hm.put(null, 89);
		hm.put("Tyre", 66);
		hm.put("MRF", 6);
		System.out.println(hm);
      
		hm.entrySet().stream().forEach(ma -> System.out.println(ma.getKey()+" "+ma.getValue()));
       
	}
}
