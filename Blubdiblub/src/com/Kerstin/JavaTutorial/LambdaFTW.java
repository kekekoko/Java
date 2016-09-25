package com.Kerstin.JavaTutorial;


public class LambdaFTW {
	public static void main(String[] args) {
		Numbers stupidArray = new Numbers(5);
		stupidArray.printArray();
        System.out.println("Print even numbers with anonymous class:");
        //print(new LambdaIterator iterator);
        stupidArray.print(new Numbers.LambdaIterator() {
			
			@Override
			public Boolean apply(Integer stupidInteger) {
				// TODO Auto-generated method stub
				if ((stupidInteger % 2) == 0){
					return true;
				}
				else return false;
			}
		});
        
        System.out.println("Print even numbers with lambda expression:");
        stupidArray.print(otherStupidInt -> ((otherStupidInt % 2) == 0));
        System.out.println("Print odd numbers with lambda expression:");
        stupidArray.print(otherStupidInt -> ((otherStupidInt % 2) != 0));
        
        //method references
        stupidArray.print(Numbers::isEvenIndex);
        stupidArray.print(Numbers::isOddIndex);
	}
}


class Numbers {
	private final int size;
	private final int[] Array;
	
	//constructor - fill array
	Numbers(int newSize){
		this.size = newSize;
		this.Array = new int[size];
		for (int i=0;i<size;i++){
			this.Array[i]=i;
		}
	}
	
	//print content of Array
	public void printArray(){
		for (int i=0;i<size;i++){
			System.out.print(this.Array[i]);
		}
		System.out.println();
	}
	
	//weird stuff
	interface LambdaIterator extends java.util.function.Function<Integer, Boolean> {}
	
	public void print(LambdaIterator iterator){
			for (int i=0;i<this.size;i++){
				if (iterator.apply(this.Array[i])){
					System.out.print(this.Array[i]);
				}
			}
			System.out.println();
	}
	
	public static boolean isEvenIndex(int integer){
		if ((integer%2)==0){
			return true;
		}
		else return false;
	}
	
	public static boolean isOddIndex(int integer){
		if ((integer%2)==0){
			return false;
		}
		else return true;
	}
}
