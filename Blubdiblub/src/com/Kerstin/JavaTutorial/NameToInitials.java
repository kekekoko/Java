package com.Kerstin.JavaTutorial;

import java.util.ArrayList;

public class NameToInitials{
	public static void main(String[] args){
		System.out.println(NameToInitials.extractInitials("ernst Georg Josef kohout"));
		System.out.println(NameToInitials.isAnagram(".% parliament", "partial men"));
	}
	public static String extractInitials(String fullName){
		String initials = "";
		String[] arrayOfNames = fullName.split(" ");
		for (String name: arrayOfNames){
			initials+=name.substring(0, 1);
		}
		initials = initials.toUpperCase();
		return initials;
	}
	public static boolean isAnagram(String fStr, String sStr){
		String firstString = fStr.replaceAll("\\W", "");
		String secondString = sStr.replaceAll("\\W", "");;
		if (firstString.length() == secondString.length()){
			boolean matches = true;
			int i= 0;
			while (matches && (i < firstString.length())){
				if (!secondString.contains(firstString.substring(i, (i+1)))){
					matches = false;
				} else {
					secondString = secondString.replaceFirst(firstString.substring(i, (i+1)), "");
					i+=1;
					}
			}
			return matches;
		} else return false;
	}
}