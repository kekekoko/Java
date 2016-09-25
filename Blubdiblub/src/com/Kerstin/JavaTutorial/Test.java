package com.Kerstin.JavaTutorial;

import java.util.ArrayList;

public class Test{
	public static void main(String[] args){
		int arraySize = 0;
		for (String arg: args){
			arraySize+=arg.length();
		}
		char[] charArray = new char[arraySize];
		int position = 0;
		for (String arg: args){
			charArray[position]=arg.charAt(0);
			if (arg.length()>1){
				position+=1;
				charArray[position]=arg.charAt(1);
			}
			position+=1;
		}
		for(int i=0;i<arraySize;i++){
			//System.out.println(charArray[i]);
		}
		String anotherString = new String(charArray);
		//System.out.println(anotherString);
		float a = Float.parseFloat(args[0]);
		//System.out.println(a);
		String text = "Hallo du Doofie";
		//System.out.println(text.lastIndexOf("oo"));
		String newString = text.replace('o', 'x');
		//System.out.println(newString);
		ArrayList<Integer> varArray = new ArrayList<>();
		for (int j=0; j<100; j++){
			varArray.add(j);
		}
		//System.out.print(varArray);
		StringBuilder sb = new StringBuilder("Able was I ere I saw Elba.");
		System.out.println(sb.length());
	}
}