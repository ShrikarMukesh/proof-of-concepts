package com.metadata.programs;

abstract class ReturnData
{
	public ReturnData() {
		// TODO Auto-generated constructor stub
	}
	double getData(byte a, double z)
	{
		return (short)a/z * 10;
	}
}
public class Practice {

	/*	int i[] = {0};
	public static void main(String args[]) 
	{
		int i[] = {1};
		change_i(i);
		System.out.println(i[0]);
	}
	public static void change_i(int i[]) 
	{
		int j[] = {2};
		i = j;
	}*/
	public static void main(String args[]) {
		int a =5 , b=6, c =7;
		System.out.println("Value is "+ b +c);
		System.out.println(a + b +c);
		System.out.println("String "+(b+c));
	}
}

