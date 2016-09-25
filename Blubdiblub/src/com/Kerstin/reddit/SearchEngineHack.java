package com.Kerstin.reddit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchEngineHack{
	//#255 [Hard] Hacking a search engine
	public static void main (String[] args){
		new SearchEngineHack("C:\\temp\\morelines.txt");
	}
	
	SearchEngineHack(String filePath){
		if (filePath.matches(".*\\.txt$")){
			ArrayList<String> l = this.readFile(filePath);
			ArrayList<String> queries = this.findQueries(l);
			System.out.println(queries);
			System.out.println("Number of queries: " + queries.size());
		} else {
			System.out.println("Please enter a valid path to a txt file");
		}
	}
	
	private ArrayList<String> readFile(String filePath){
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = in.readLine())!= null){
//				line = line.replaceAll("[\\s.,?\\-\\(\\)\":;!]", "").toLowerCase();
				line = line.replaceAll("[^0-z]", "").toLowerCase();
//				line = line.replaceAll("[^A-z]", "").toLowerCase();
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Something went wrong while reading the file...");
		} finally {
			try {
				if (in != null){
					in.close();
				}
			} catch (IOException e) {
				System.out.println("The input stream thing could not be closed?");
			}
		}
		return lines;
	}
	
	private ArrayList<String> findQueries(ArrayList<String> lines){
		int wordLength = 5;
		//int currentLine = 0;
		ArrayList<String> result = new ArrayList<String>();
		while (!lines.isEmpty()){
			String currentLine = lines.get(0);
			int lineLength = currentLine.length();
			int mostMatches = 0;
			String mostMatchedWord = null;
			ArrayList<String> mostMatchedLines = new ArrayList<String>();
			for (int i = wordLength; i <= lineLength; i++){
				String currentWord = currentLine.substring(i-wordLength, i);
				int currentMatches = 0;
				ArrayList<String> currentMatchedLines = new ArrayList<String>();
				for (String line : lines){
					if (line.contains(currentWord)){
						currentMatchedLines.add(line);
						currentMatches++;
					}
				}
				if (currentMatches > mostMatches){
					mostMatches = currentMatches;
					mostMatchedWord = currentWord;
					mostMatchedLines = currentMatchedLines;
				}
			}
			lines.removeAll(mostMatchedLines);
			result.add(mostMatchedWord);
		}
		return result;
	}
}