package com.Kerstin.JavaTutorial;

public class ReverseString implements CharSequence{
	String inputString;
	String outputString;

	ReverseString(String s){
		this.inputString = s;
		System.out.print(this.toString());
	}
	
	@Override
	public char charAt(int arg0) {
		// TODO Auto-generated method stub
		return this.inputString.charAt(arg0);
	}

	@Override
	public int length() {
		// TODO Auto-generated method stub
		return this.inputString.length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		// TODO Auto-generated method stub
		return this.inputString.substring(start, end);
	}
	
	@Override
	//reverse string
	public String toString(){
		this.outputString = new String();
		for (int i=(this.length()-1);i>=0;i--){
			this.outputString+=this.charAt(i);
		}
		return this.outputString.toString();
	}
	
	public static void main(String[] args) {
		new ReverseString("hallo");
	}
}