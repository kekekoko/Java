package com.Kerstin.reddit;

public class DogRace{
	public static void main (String[] args){
		//Challenge #267 Easy
		//arg1 .. dogs place, arg2 .. participants
		
		if (args.length!=2){
			System.out.println("invalid number of arguments - must be 2");
		} else {
			int yourPlace = Integer.valueOf(args[0]);
			int size = Integer.valueOf(args[1]);
			String[] places = new String[size];
			for (int i = 0; i < (size); i++){
				if (i != yourPlace-1){
					String currentValue;
					if (i<9){
						currentValue = " " + Integer.toString(i + 1);
					} else currentValue = Integer.toString(i + 1);
					
					
					if (currentValue.charAt(currentValue.length() - 2) != '1'){
						if (currentValue.charAt(currentValue.length()-1) == '1'){
							places[i] = currentValue + "st, ";
						} else if (currentValue.charAt(currentValue.length()-1) == '2'){
							places[i] = currentValue + "nd, ";
						} else if (currentValue.charAt(currentValue.length()-1) == '3'){
							places[i] = currentValue + "rd, ";
						} else places[i] = currentValue + "th, ";
					} else places[i] = currentValue + "th, ";
					System.out.print(places[i].trim());
				}
			}
		}
	}
}