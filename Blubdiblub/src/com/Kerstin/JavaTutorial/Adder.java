package com.Kerstin.JavaTutorial;


public class Adder{
	public static void main(String[] args){
		float result=0;
		if (args.length<2){
			System.out.print("Error: enter at least 2 integers");
		} else {
			for (String arg: args){
				result+=Float.parseFloat(arg);
			}
			System.out.format("%-10.2f%n", result);
		}
		
		
		//String[] array = new String[2];
		//array[0]="3";
		//array[1]="5";
		//Adder.test(array);
	}
	/*public static void test(String args[]){
		int result=0;
		for (String arg: args){
			result+=Integer.parseInt(arg);
		}
		System.out.print(result);
	}*/
}
