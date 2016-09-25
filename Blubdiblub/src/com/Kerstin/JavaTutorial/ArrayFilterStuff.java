package com.Kerstin.JavaTutorial;


public class ArrayFilterStuff {
	public static void main(String[] args) {
		
		//create array
		Letters ds = new Letters(5, 'a');
		
		System.out.println("Print array:");
        ds.printVocals();
        
        System.out.println("Print vocals with an instance of VocalIterator class:");
        ds.print(ds.new VocalIterator());
                
        //print consonants
        System.out.println("Print consonants with anonymous class:");
        ds.print(new Letters.DataStructureIterator(){

        	private int nextIndex = 0;

    		public boolean isConsonant(char charToTest){
    			if (charToTest != 'a' && charToTest != 'e' && charToTest != 'i' && charToTest != 'o' && charToTest != 'u'){
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    		
    		@Override
    		public boolean hasNext() {
    			return (nextIndex < (ds.size - 1));
    		}

    		@Override
    		public Integer next() {
    			Integer retValue;
    			
    			while (!isConsonant(ds.LetterArray[nextIndex]) && this.hasNext()){
    				nextIndex++;
    			}
    			// deal with end of array
    			if (!this.hasNext()){
    				if (!isConsonant(ds.LetterArray[nextIndex])){
    					retValue = -1;
    				}
    				else {
    					retValue = Integer.valueOf(nextIndex);
    				}
    			}
    			else{
    				retValue = Integer.valueOf(nextIndex);
    				nextIndex++;
    			}
    			return retValue;
    		}
        });
        

        
	}
}


class Letters{
	final int size;
	char[] LetterArray;
	char startChar;
	public Letters(int newSize, char newStartChar){
		if (newSize > 0){
			this.size = newSize;
		}
		else {
			System.out.println("Array size must be bigger than 0");
			
			this.size = 1;
		}		
		createArray(this.size, newStartChar);
	}
	
	public Letters(int newSize){
		if (newSize > 0){
			this.size = newSize;
		}
		else {
			System.out.println("Array size must be bigger than 0");
			
			this.size = 1;
		}
		createArray(this.size, 'a');
	}
	
	private void createArray(int newSize, char newStartChar){
		if (newStartChar>='a' && newStartChar<='z'){
			startChar = newStartChar;
		}
		else {
			System.out.println("Array must only contain lower case letters");
			startChar = 'a';
		}
		LetterArray = new char[newSize];
		for (int i = 0; i<newSize; i++){
			//System.out.print(i);
			//System.out.println(a);
			LetterArray[i] = startChar;
			if (startChar < 'z'){
				startChar++;
			}
			else startChar = 'a';
		}
		for (int j = 0; j < newSize; j++){
			System.out.print(LetterArray[j]);
		}
		System.out.println();
	}
	
	public void printVocals(){
		DataStructureIterator iterator = this.new VocalIterator();
		System.out.println("Vocals:");
		do {
			int foundIndex = iterator.next();
			//make sure end of array has not been reached
			if (foundIndex!=(-1)){
				System.out.print(LetterArray[foundIndex] + " ");
			}
			System.out.println();
		} while (iterator.hasNext());
	}
	
	public void print(DataStructureIterator iterator){
		do {
			int foundIndex = iterator.next();
			//make sure end of array has not been reached
			if (foundIndex!=(-1)){
				System.out.print(LetterArray[foundIndex] + " ");
			}
			System.out.println();
		} while (iterator.hasNext());
	}
	

	
	interface DataStructureIterator extends java.util.Iterator<Integer> { } 
	
	class VocalIterator implements DataStructureIterator{
		private int nextIndex = 0;

		public boolean isVocal(char charToTest){
			if (charToTest == 'a' || charToTest == 'e' || charToTest == 'i' || charToTest == 'o' || charToTest == 'u'){
				return true;
			}
			else {
				return false;
			}
		}
		
		@Override
		public boolean hasNext() {
			return (nextIndex < (Letters.this.size - 1));
		}

		@Override
		public Integer next() {
			Integer retValue;
			
			while (!isVocal(LetterArray[nextIndex]) && this.hasNext()){
				nextIndex++;
			}
			// deal with end of array
			if (!this.hasNext()){
				if (!isVocal(LetterArray[nextIndex])){
					retValue = -1;
				}
				else {
					retValue = Integer.valueOf(nextIndex);
				}
			}
			else{
				retValue = Integer.valueOf(nextIndex);
				nextIndex++;
			}
			return retValue;
		}
	}
}