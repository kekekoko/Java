package com.Kerstin.reddit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Transpose{
	private static BufferedReader in;
	//#270 [Easy] Transpose the input text
	public static void main(String[] args) throws IOException{
		in = new BufferedReader(new FileReader("C:\\temp\\text.txt"));
		String line = "";
		ArrayList<char[]> original = new ArrayList<char[]>();
		int noOfLines = 0;
		int longestLine = 0;
		while ((line = in.readLine())!= null){
			
			if (line.length() > longestLine){
				longestLine = line.length();
			}
			original.add(noOfLines, line.toCharArray());
			noOfLines++;
		}
		String result = "";
		for (int j = 0; j < longestLine; j++){
			for (int i = 0; i < noOfLines; i++){
				if (j < original.get(i).length){
					result += original.get(i)[j];
				}
				else result += " ";
			}
			result += "\r\n";
		}
		System.out.print(result);
	}
}