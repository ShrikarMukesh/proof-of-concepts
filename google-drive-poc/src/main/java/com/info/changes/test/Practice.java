package com.info.changes.test;
class P{
	public void m1() {
		System.out.println("p");
	}
	public void m2() {
		System.out.println("mk2");
	}
}
class C extends P{
	
	public void m1() {
		System.out.println("C");
	}
	public void m2() {
		System.out.println("sffgdf");
	}
}
public class Practice {
	
	public static void main(String[] args) {
       P p = new C();
       p.m1();
       p.m2();
	}
}
