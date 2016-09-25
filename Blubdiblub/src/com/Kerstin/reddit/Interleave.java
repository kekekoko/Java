package com.Kerstin.reddit;

public class Interleave{
	//#278 [Easy/Med] Weave insert Part 1
	public static void main (String[] args){
		Integer[] Array1 = new Integer[]{11, 12, 13};
		Integer[] Array2 = new Integer[]{0, 1, 2, 3, 4, 5, 6};
		Object[] bla = Interleave.weave(Array1, Array2);
		
//		Object[] Array1 = new Object[]{"*"};
// 		Object[] Array2 = new Object[]{"(2+3)", "(4-5)", "(6+7)"};
// 		Object[] bla = Interleave.weave(Array1, Array2);
 		for (Object blub : bla){
 			System.out.println(blub.toString());
 		}
 		String[] String1 = new String[]{"2+3", "4-5", "6+7"};
 		String[] String2 = new String[]{"()"};
 		String[] umpf = Interleave.bracket(String1, String2);
 		for (String umpfi : umpf){
 			System.out.println(umpfi);
 		}
 		
	}
	
	private static Object[] weave (Object[] first, Object[] second){
		int length = second.length;
		int resultLength = length * 2 - 1;
		Object[] result = new Object[resultLength];
		int firstI = 0;
		int secondI = 0;
		for (int i = 0; i < resultLength; i++){
			if (i%2 == 0){
				result[i] = second[secondI];
				secondI++;
			} else {
				try {
					result[i] = first[firstI];
				} catch (IndexOutOfBoundsException e){
//					e.printStackTrace();
					firstI = 0;
					result[i] = first[firstI];
				}
				firstI++;
			}
		}
		return result;
	}
	
	private static String[] bracket (String[] first, String[] second){
		String[] newFirst;
		String[] newSecond;
		if (first.length==1){
			newFirst = first[0].split("");
		} else newFirst = first;
		if (second.length==1){
			newSecond = second[0].split("");
		} else newSecond = second;
		int firstLength = newFirst.length;
		int secondLength = newSecond.length;
		int resultLength;
		if (firstLength >= secondLength/2){
			resultLength = firstLength;
		} else resultLength = secondLength/2;
		String[] result = new String[resultLength];
		int firstI = 0;
		int secondI = 0;
		for (int i = 0; i < resultLength; i++){
			if (firstI >= firstLength){
				firstI = 0;
			}
			if (secondI >= secondLength){
				secondI = 0;
			}
			result[i] = newSecond[secondI] + newFirst[firstI] + newSecond[secondI+1];
			firstI++;
			secondI+=2;
		}
		return result;
	}
}