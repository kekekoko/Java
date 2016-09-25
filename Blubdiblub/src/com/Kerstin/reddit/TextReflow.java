package com.Kerstin.reddit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextReflow{
	//#279 [Intermediate] Text Reflow
	public static void main (String[] args) throws IOException{
		new TextReflow("C:\\Temp\\mytext.txt", 40);
	}
	
	TextReflow(String path, int characters) throws IOException{
		String s = this.readFromFile(path);
		String formated = this.reflow(s, characters);
		System.out.println(formated);
	}
	
	private String readFromFile(String path) throws IOException{
		BufferedReader in = null;
		String result = "";
		in = new BufferedReader(new FileReader(path));
		String line;
		while ((line = in.readLine())!= null){
//			if (line != "\n"){
//				result += line + " ";
//			} else {
				result += line + " ";
//				if (line == ""){
//					result += "\r";
//				}
//				
//			}
		}
		in.close();
		return result;
	}
	
	private String reflow(String text, int characters){
		String result = "";
		while (text.length() > characters){
			if (text.charAt(characters - 1) == ' '){
				result += text.substring(0, characters) + "\n";
				text = text.substring(characters);
			} else {
				int lastPossibleIndex = text.substring(0, characters).lastIndexOf(" ");
				result += text.substring(0, lastPossibleIndex + 1) + "\n";
				text = text.substring(lastPossibleIndex + 1);
			}
		}
		result += text;
		return result;
	}
}