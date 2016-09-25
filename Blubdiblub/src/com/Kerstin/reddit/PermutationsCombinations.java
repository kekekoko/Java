package com.Kerstin.reddit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigInteger;


public class PermutationsCombinations{
	//#265 [Easy]
	public static void main (String[] args){
		
		for (int i = 1; i <= 35; i++){
			combine(i,4,7);
		}
		
		
		permutate(700000000,20);
		
		permutate(new BigInteger("836313165329095177704251551336018791641145678901235"),42);
		//permutate(new BigInteger("3"),3);
		
		/*
		for (int i = 1; i <= 120; i++){
			permutate(new BigInteger(Integer.toString(i)),5);
		}
		*/
		//permutate(new BigInteger("3240"),7);
		
	}
	
	private static void combine(int index, int k, int n){
			Integer[] base = new Integer[n];
			for (int i = 0; i <n; i++){
				base[i] = i;
			}
			
			List<Integer> baseList = new ArrayList<>(Arrays.asList(base));
			
			Integer[] resultArray = new Integer[k];			
			
			class Helper {
				Helper(int newIndex, int newK, int newN){
					fillResultArray(0, newIndex, newK, newN);
				}
				private void fillResultArray(int positionCounter, int newIndex, int newK, int newN){
					//calculate position positionCounter
					int newValue = 1;
					int tempResult = 0;
					int lastTempResult = 0;
					
					while (tempResult < newIndex){
						lastTempResult = tempResult;
						tempResult+= nChooseK(newN - newValue, newK - 1);
						newValue+=1;
					}
					
					
					//fill Result array
					resultArray[positionCounter] = baseList.get(newValue - 2);
					
					//remove all values smaller or equal to newValue
					baseList.removeAll(baseList.subList(0, newValue - 1));
					
					//move on to next position
					if (positionCounter < k - 1){
						fillResultArray(positionCounter + 1, newIndex - lastTempResult, newK - 1, baseList.size());
					}
					
				}
			}
			
			new Helper(index, k, n);
			System.out.println(Arrays.toString(resultArray));
		
	}
	
	public static int nChooseK (int n, int k){
		int result = factorize(n)/(factorize(k)*factorize(n-k));
		return result;
	}
	
	public static int factorize(int inputValue){
		if (inputValue >= 1){
			return inputValue*factorize(inputValue-1);
		} else return 1;
	}
	
	public static BigInteger factorize(BigInteger inputValue){
		BigInteger result = new BigInteger("1");
		
		for (int i = 1; i <= inputValue.intValue(); i++){
			result = result.multiply(new BigInteger(Integer.toString(i)));
		}
		
		if(inputValue.intValue() == 0){
			result = BigInteger.ZERO;
		}
		
		return result;
	}
	
	private static void permutate(BigInteger index, int number){
		BigInteger[] totalPermutations = new BigInteger[number];
		List<Integer> numbersLeft = new ArrayList<Integer>();
		Integer[] resultArray = new Integer[number];
		
		for (int i = 0; i < number; i++){
			numbersLeft.add(i);
			totalPermutations[i] = factorize(new BigInteger(Integer.toString(i)));
		}
		//System.out.println(Arrays.toString(totalPermutations));
		//System.out.println(numbersLeft.toString());
		
		class Helper{
			Helper(int resultIndex, BigInteger permIndex){
				fill(0, permIndex);
			}
			private void fill(int resultIndex, BigInteger permIndex){
				
				if (numbersLeft.size()>1){
					int helpMe = permIndex.subtract(new BigInteger("1")).divide(totalPermutations[number - resultIndex - 1]).intValue();
					resultArray[resultIndex] = numbersLeft.get(helpMe);
					numbersLeft.remove(helpMe);
					if (resultIndex < number - 1){
						fill(resultIndex+1, permIndex.subtract(new BigInteger("1")).remainder(totalPermutations[number - resultIndex - 1]).add(new BigInteger("1")));
					}
				} else resultArray[resultIndex] = numbersLeft.get(0);
				
				
			}
		}
		
		new Helper(0, index);
		System.out.println(Arrays.toString(resultArray));
	}
	
	private static void permutate (int index, int number){
		//eg. if number = 3, totalpermutations would be 1,2,6
		Integer[] totalPermutations = new Integer[number];
		Integer[] array = new Integer[number];
		Integer[] resultArray = new Integer[number];
		totalPermutations[0] = 1;
		for (int i = 0; i < number; i++){
			array[i] = i;
			if (i >= 1){
				totalPermutations[i] = totalPermutations[i-1]*(i+1);
			}
		}
		List<Integer> newList = new ArrayList<>(Arrays.asList(array));
		int j = 0;
		
		
		class Helper{
			Helper(int newSizeOfList, int newX, int newY){
				fill(newSizeOfList, newX, newY);
			}
			
			private void fill(int sizeOfList, int newX, int newY){
				int x = newX;
				int j = sizeOfList;
				int y = newY;
				if (j < number){
					if (j + 2 <= number){
						int helpMe;
						if (j > 0){
							helpMe = totalPermutations[number-j-1];
						} else {
							helpMe = 0;
							y = index - 1;
						}
						resultArray[j] = newList.get((y - (x*helpMe)) / totalPermutations[number-(j+2)]);
						y = y - (x*helpMe);
						x = newList.indexOf(resultArray[j]);
						newList.remove(x);
					}
					else {
						resultArray[j] = newList.get(0);
					}
					fill(j+1, x, y);
				}
			}
		}
		new Helper(j, 0, 0);
		System.out.println(Arrays.toString(resultArray));
	}

}