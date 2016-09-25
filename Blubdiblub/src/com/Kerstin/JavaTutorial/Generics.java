package com.Kerstin.JavaTutorial;

import java.util.*;
import java.util.function.Predicate;



public class Generics{
	public static void main(String[] args){
		Helper test = new Helper();
		System.out.println(test.countElements(Arrays.asList(1, 2, 3, 4), p -> (p.intValue()%2 == 0)));
		System.out.println(test.countElements(Arrays.asList('c', 'i', 'v', 'i', 'e'), p -> (p == 'a' || p == 'e' || p == 'i' || p == 'o' || p == 'u')));
		System.out.println(Arrays.toString(test.reverse(new Integer[]{1, 2, 654, 9}, 5, 10)));
		System.out.println(test.findMax(Arrays.asList(2, 5, 1, 9, 25, 3, 19), (c1, c2) -> c1.compareTo(c2)));
		System.out.println(test.findMax(new ArrayList<Integer>(), (c1, c2) -> c1.compareTo(c2)));
	}

}

class Helper{
	public <T> int countElements(Collection<T> newCollection, Predicate<T> newPredicate){
		int counter = 0;
		for (T element : newCollection){
			if (newPredicate.test(element) == true){
				counter+=1;
			}
		}
		return counter;
	}
	
	public <T> T[] reverse(T[] myArray, int a, int b){
		T[] reversedArray = myArray.clone();
		try{
			reversedArray[a] = myArray[b];
			reversedArray[b] = myArray[a];
		} catch (IndexOutOfBoundsException e){
			System.out.println("One or both specified indices are larger than the size of the array. There won't be any modifications.");
		}
		return reversedArray;
	}
	
	public <T> T findMax(List<T> myList, Comparator<T> myComparator){
		try{
			T newMax = myList.get(0);
			for (T element : myList){
				if (myComparator.compare(element, newMax) == 1){
					newMax = element;
				}
			}
			return newMax;
		} catch (IndexOutOfBoundsException|NullPointerException e){
			return null;
		}
	}
}


