package com.info.google.bigTable;

abstract class Person{
	int id ;
	String name;
	int age;
	String address;
	
	public Person(int id, String name, int age, String address) {
		
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
}
class Student extends Person{
 
	String student;
	public Student(int id, String name, int age, String address,String student) {
		super(id, name, age, address);
	    this.student=student;	
	}
	@Override
	public String toString() {
		return "Student [student=" + student + ", id=" + id + ", name=" + name + ", age=" + age + ", address=" + address
				+ "]";
	}
	
	
}
class Teacher extends Person{
	 
	String teacher;
	public Teacher(int id, String name, int age, String address,String teacher) {
		super(id, name, age, address);
		this.teacher =teacher;
	}
	@Override
	public String toString() {
		return "Teacher [teacher=" + teacher + ", id=" + id + ", name=" + name + ", age=" + age + ", address=" + address
				+ "]";
	}
	

}

public class Practice {
	public static void main(String[] args) {
        Student s = new Student(1, "Rohan", 22, "BTM", "ABC");
        Teacher t = new Teacher(2, "Shrikar", 227, "jj", "AhBC");
        System.out.println(s);
        System.out.println(t);
	}

	
}
