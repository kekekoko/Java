package com.Kerstin.reddit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OgreMaze {
	//#211 [Intermediate]
	public static void main(String[] args) throws FileNotFoundException, IOException{
		//new Maze(10, 10, ".@@.....O..@@.........O..O...........O.....O.......................O.O...O.O...........O...........$");
		//new Maze(3, 3);
		//new Maze(new FileInputStream("C:\\Temp\\ogre.txt"));
		//System.out.println("\r\n...O..O....\r\n".matches("\r\n(.|\r\n)*"));
		new Maze();
		}
}

class Maze {
	int width;
	int height;
	char[][] grid;
	char[][] tempGrid;
	Maze(int newWidth, int newHeight, String newContent){
		if (newWidth*newHeight != newContent.length() | newWidth < 3 | newHeight < 3){
			throw new IllegalArgumentException();
		}
		width = newWidth;
		height = newHeight;
		grid = new char[width][height];
		//fill grid
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				grid[j][i] = newContent.charAt(j + width*i);
			}
		}
		
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				System.out.print(grid[j][i]);
			}
			System.out.println();
		}
		solve();
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				System.out.print(grid[j][i]);
			}
			System.out.println();
		}
	}
	
	Maze(int newWidth, int newHeight){
		if (newWidth < 3 | newHeight < 3){
			throw new IllegalArgumentException();
		}
		String newContent="";
		List<Character> symbols = new ArrayList<Character>(newWidth*newHeight);
		//fill List with values
		for (int i = 0; i < (newWidth*newHeight); i++){
			symbols.add('.');
		}
		Random randomizer = new Random();
		//add gold
		symbols.set(randomizer.nextInt(newWidth*newHeight), '$');
		//add Ogre
		int ogrePosition;
		do {
			ogrePosition = randomizer.nextInt(newWidth*newHeight-newWidth-1);
		} while (symbols.get(ogrePosition) != '.' | ((ogrePosition/(newWidth-1)==0) && (ogrePosition!=0)));
		symbols.set(ogrePosition, '@');
		symbols.set(ogrePosition+1, '@');
		symbols.set(ogrePosition+newWidth, '@');
		symbols.set(ogrePosition+newWidth+1, '@');
		//add sinkholes
		int i = 0;
		while (i < Math.floor((newWidth*newHeight - 5)*0.15)){
			//generate random number
			int randomIndex = randomizer.nextInt(newWidth*newHeight);
			if (symbols.get(randomIndex) == '.'){
				symbols.set(randomIndex, 'O');
				i+=1;
			}
		}
		for (int j = 0; j < newWidth*newHeight; j++){
			newContent+=symbols.get(j);
		}
		new Maze(newWidth, newHeight, newContent);
	}
	
	Maze(FileInputStream newReader) throws IOException{
		byte c;
		ArrayList<Byte> byteList = new ArrayList<Byte>();
        while ((c = (byte) newReader.read()) != -1) {
        	byteList.add(c);
        }
        Byte[] newB = byteList.toArray(new Byte[1]);
        int length = newB.length;
        byte[] newba = new byte[length];
        int counter2 = 0;
        for (Byte b : newB){
        	newba[counter2] = b;
        	counter2++;
        }
        
        String newContent = new String(newba);
        System.out.println(newContent);
        int newWidth = newContent.indexOf("\r\n");
        int newHeight = 1;
        
        for (int i = 0; i < length; i++){
        	if (newContent.substring(i).matches("\r\n(.|\r\n)*")){
        		newHeight++;
        	}
        }
        
        newContent = newContent.replaceAll("\r\n", "");
        int temp = newContent.length();
        System.out.println(temp);
        System.out.println(newContent);
        new Maze(newWidth, newHeight, newContent);
        
	}
	
	Maze(){
		//generate random Maze with between 3 and 49 rows/columns
		Random newRandom = new Random();
		int newWidth = 0;
		int newHeight = 0;
		while (newWidth < 3 | newHeight < 3){
			if (newWidth < 3){
				newWidth = newRandom.nextInt(50);
			}
			if (newHeight < 3){
				newHeight = newRandom.nextInt(50);
			}
		}
		new Maze(newWidth, newHeight);
	}
	
	public void solve(){
		//get Ogre position
		boolean found = false;
		int i = 0;
		int j = 0;
		int startX = 0;
		int startY = 0;
		while (!found && (i < height)){
			while (!found && (j < width)){
				if (grid[j][i] == '@'){
					found = true;
					startX = j;
					startY = i;
				} else j+=1;
			}
			i+=1;
			j = 0;
		}
		System.out.println(startX);
		System.out.println(startY);
		
		System.out.println("Solution possible: " + move(startX, startY, grid));
		
		
	}
	
	public boolean move(int newStartX, int newStartY, char[][] newGrid){
		char[][] currentGrid = newGrid;
		boolean goldFound = false;
		
		
		System.out.println(newStartX + "..." + newStartY);
		
		//check for Gold
		
		if (newStartY - 1 >= 0){
			if (currentGrid[newStartX][newStartY-1] == '$' && currentGrid[newStartX+1][newStartY-1] == '.'){
				goldFound = true;
				currentGrid[newStartX][newStartY-1] = 'x';
				currentGrid[newStartX+1][newStartY-1] = '&';
				grid = currentGrid;
			} else if (currentGrid[newStartX][newStartY-1] == '.' && currentGrid[newStartX+1][newStartY-1] == '$'){
				goldFound = true;
				currentGrid[newStartX][newStartY-1] = '&';
				currentGrid[newStartX+1][newStartY-1] = 'x';
				grid = currentGrid;
			}
		} 
		if (newStartX + 2 < width){
			if (currentGrid[newStartX+2][newStartY] == '$' && currentGrid[newStartX+2][newStartY + 1] == '.'){
				goldFound = true;
				currentGrid[newStartX+2][newStartY] = 'x';
				currentGrid[newStartX+2][newStartY + 1] = '&';
			} else if (currentGrid[newStartX+2][newStartY] == '.' && currentGrid[newStartX+2][newStartY + 1] == '$'){
				goldFound = true;
				currentGrid[newStartX+2][newStartY] = '&';
				currentGrid[newStartX+2][newStartY + 1] = 'x';
			}
		}
		if (newStartY + 2 < height){
			if (currentGrid[newStartX][newStartY + 2] == '$' && currentGrid[newStartX + 1][newStartY + 2] == '.'){
				goldFound = true;
				currentGrid[newStartX][newStartY + 2] = 'x';
				currentGrid[newStartX + 1][newStartY + 2] = '&';
			} else if (currentGrid[newStartX][newStartY + 2] == '.' && currentGrid[newStartX + 1][newStartY + 2] == '$'){
				goldFound = true;
				currentGrid[newStartX][newStartY + 2] = '&';
				currentGrid[newStartX + 1][newStartY + 2] = 'x';
			}
		}
		if (newStartX -1 >= 0){
			if (currentGrid[newStartX-1][newStartY] == '$' && currentGrid[newStartX-1][newStartY + 1] == '.'){
				goldFound = true;
				currentGrid[newStartX-1][newStartY] = 'x';
				currentGrid[newStartX-1][newStartY + 1] = '&';
			} else if (currentGrid[newStartX-1][newStartY] == '.' && currentGrid[newStartX-1][newStartY + 1] == '$'){
				goldFound = true;
				currentGrid[newStartX-1][newStartY] = '&';
				currentGrid[newStartX-1][newStartY + 1] = 'x';
			} 
		}
		
		
		//check for next movement (up, right, down, left)
		if (newStartY - 1 >= 0 && currentGrid[newStartX][newStartY-1] == '.' && currentGrid[newStartX+1][newStartY-1] == '.' && !goldFound){			
			System.out.println("trying up");

				currentGrid[newStartX][newStartY-1] = '&';
				currentGrid[newStartX+1][newStartY-1] = '&';
				grid = currentGrid;
				goldFound = move(newStartX, newStartY-1, currentGrid);
				if (!goldFound){
					currentGrid[newStartX][newStartY-1] = '.';
					currentGrid[newStartX+1][newStartY-1] = '.';
					grid = currentGrid;
				}
		}
		if (newStartX + 2 < width && currentGrid[newStartX+2][newStartY] == '.' && currentGrid[newStartX+2][newStartY + 1] == '.' && !goldFound){
			System.out.println("trying right");
			
				currentGrid[newStartX+2][newStartY] = '&';
				currentGrid[newStartX+2][newStartY+1] = '&';
				grid = currentGrid;
				goldFound = move(newStartX + 1, newStartY, currentGrid);
				if (!goldFound){
					currentGrid[newStartX+2][newStartY] = '.';
					currentGrid[newStartX+2][newStartY+1] = '.';
					grid = currentGrid;
				}
				
		}
		if (newStartY + 2 < height && currentGrid[newStartX][newStartY + 2] == '.' && currentGrid[newStartX + 1][newStartY + 2] == '.' && !goldFound){
			 System.out.println("trying down");
			
				currentGrid[newStartX][newStartY + 2] = '&';
				currentGrid[newStartX + 1][newStartY + 2] = '&';
				grid = currentGrid;
				goldFound = move(newStartX, newStartY + 1, currentGrid);
				if (!goldFound){
					currentGrid[newStartX][newStartY + 2] = '.';
					currentGrid[newStartX + 1][newStartY + 2] = '.';
					grid = currentGrid;
				} 
		}
		if (newStartX -1 >= 0 && currentGrid[newStartX-1][newStartY] == '.' && currentGrid[newStartX-1][newStartY + 1] == '.' && !goldFound){
			System.out.println("trying left");
			
				currentGrid[newStartX-1][newStartY] = '&';
				currentGrid[newStartX-1][newStartY+1] = '&';
				grid = currentGrid;
				goldFound = move(newStartX - 1, newStartY, currentGrid);
				if (!goldFound){
					currentGrid[newStartX-1][newStartY] = '.';
					currentGrid[newStartX-1][newStartY+1] = '.';
					grid = currentGrid;
				}
		}
		
		
		return goldFound;
	}
	
}