package com.UsingPath;

interface v {
	int g =90;
}
class Parent implements v{
    int x =90;
	public Parent(String name) {
		System.out.println("p");
	}
    
	void add() {
		System.out.println(10);
	    
	}
}
class Child extends Parent implements v{

	int x = 33;
	public Child(String name) {
		super(name);
		System.out.println("c");
		super.add();
		System.out.println(super.x);
		
	}
    void add() {
    	System.out.println("afc");
    	System.out.println(super.x);
    }
}
public class Practice {
	public static void main(String[] args) {
        new Child("kk");
	}
}
