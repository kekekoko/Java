package com.Kerstin.reddit;

import java.io.Console;

public class Recktangles{
	public static void main (String[] args){
		//System.out.println("Enter word, width and height");
		try{
			Console c = System.console();
			String myWord = c.readLine("Enter word: ");
			int myWidth = Integer.parseInt(c.readLine("Enter width: "));
			int myHeight = Integer.parseInt(c.readLine("Enter height: "));
			System.out.print(Recktangles.createRecktangle(myWord, myWidth, myHeight));
		} catch (NullPointerException f){
			System.out.println("You are to stupid to run this properly");
		}
		
		
		
	}
	
	public static String createRecktangle(String word, int width, int height){
		int wordLength = word.length();
		int gridWidth = width*wordLength - width + 1;
		int gridHeight = height* wordLength - height + 1;
		char[][] temp = new char[gridWidth][gridHeight];
		String result="";
		int i = 0;
		int j = 0;
		int currentPosition = 0;
		int currentYPosition = 0;
		boolean increasePossible = true;
		boolean increaseYPossible = true;
		int helper = 0;
		
		for (int outerJ = 0; outerJ < gridHeight; outerJ+=(wordLength - 1)){
			
			while (i < gridWidth){
				if (i%(wordLength - 1) == 0 && outerJ == 0){
					currentYPosition = currentPosition;
					increaseYPossible = increasePossible;
					while (j < gridHeight){
						try{
							temp[i][j] = word.charAt(currentYPosition);
							j++;
						} catch (IndexOutOfBoundsException e){
							if (increaseYPossible){
								increaseYPossible = false;
								currentYPosition--;
							} else {
								increaseYPossible = true;
								currentYPosition++;
							}
						}
						if (increaseYPossible){
							currentYPosition++;
						} else currentYPosition--;
					}
					j = 0;
				}
				
				try{
					temp[i][outerJ] = word.charAt(currentPosition);
					i++;
				} catch (IndexOutOfBoundsException e){
					if (increasePossible){
						increasePossible = false;
						currentPosition--;
					} else {
						increasePossible = true;
						currentPosition++;
					}
				}
				if (increasePossible){
					currentPosition++;
				} else currentPosition--;
				
			}
			i = 0;
			helper++;
			if (helper%2 != 0){
				currentPosition = wordLength - 1;
				increasePossible = false;
			} else {
				currentPosition = 0;
				increasePossible = true;
			}
		}
		
		for (int y = 0; y < gridHeight; y++){
			for (int x = 0; x < gridWidth; x++){
				result+= temp[x][y];
			}
			result+="\r\n";
		}
		
		return result;
	}
}