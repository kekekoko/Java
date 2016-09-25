package com.Kerstin.JavaTutorial;

import java.io.IOException;

public class Lalala {

	public static void main(String[] args) throws IOException {
		
		/*System.out.println("Yaay!");
		cat myCat = new cat("Charon");
		System.out.println(myCat.name);
		cat myOtherCat = new cat("Tycho");
		System.out.println(myCat.name + myOtherCat.name);
		food myFood = new food(60);
		System.out.println(myFood.amount);
		food myEmptyFood = new food();
		myEmptyFood.addFood(10);
		System.out.println(myEmptyFood.amount);
		myCat.eat(myFood);
		*/
		//overFeedKill(myCat, myFood);
		CatPack myCatPack = new CatPack(5, "test");
		food myFood = new food(61);
		/*
		not possible anymore because these were set to private
		
		myCatPack.catArray[1].currentFull=99;
		myCatPack.catArray[3].currentFull=95;
		myCatPack.catArray[3].maxFull=102;
		myCatPack.catArray[3].tooMuchFoodCounter=4;
		*/
		
		
		
		myCatPack.listCats();
		myCatPack.eat(myFood);
		overFeedKill(myCatPack.catArray[1], myFood);
		myCatPack.listCats();
		System.out.println(myFood.getAmount());
		System.out.print(myCatPack.catArray[1].getClass());
		
		//new Letters();

	}
	
	static void overFeedKill(cat dyingCat, food deadlyFood){
		if (dyingCat.isAlive()){
			do {
				deadlyFood.addFood(10);
				dyingCat.eat(deadlyFood);
			} while (dyingCat.isAlive());
			System.out.println("You just killed " + dyingCat.name + " on purpose you sadist.");
		}
		else{
			System.out.println(dyingCat.name + " is dead already");
		}
	}
}


class cat implements ModifyMe, FoodProcessor{
	String name;
	private int maxFull = 100;
	private int currentFull = maxFull/2;
	private int tooMuchFoodCounter = 0;
	private boolean alive = true;
	cat(String startName){
		this.changeName(startName);
	}
	public void changeName(String newValue){
		this.name = newValue;
	}
	public void eat(food newFood){
		System.out.println("Feeding " + this.name);
		if (this.alive) {
			if (this.currentFull + newFood.getAmount() <= maxFull){
				this.currentFull+=newFood.getAmount();
				newFood.clearAmount();
			}
			else {
				newFood.addFood(-(this.maxFull-this.currentFull));
				this.currentFull = maxFull;
				this.tooMuchFoodCounter += 1;
				if (this.tooMuchFoodCounter>=5){
					maxFull+=1;
					System.out.println(this.name + " just got fatter");
					if (this.maxFull>=103){
						this.alive = false;
						System.out.println(this.name + " has died. Oops");
					}
				}
				if (this.alive) {
					System.out.println(this.name + " just threw up. Fuck.");
				}
			}
		}
		else {
			System.out.println(this.name + " is dead. Feeding won't help now.");
		}
	}
	
	public void showCat(){
		System.out.print(this.name + " " + this.currentFull + " " + this.alive);
		if (this.tooMuchFoodCounter>=4 && this.maxFull>=102 && this.alive){
			System.out.println(" very fat, will die if fed more than " + (this.maxFull-this.currentFull) + " food");
		}
		else {
			System.out.println();
		}
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	//override methods from object
	
	public String toString(){
		return this.name;
	}
}


interface ModifyMe{
	void changeName(String newValue);
}

interface FoodProcessor{
	void eat(food newFood);
}


class food {
	private int amount=0;
	food(int newAmount){
		this.amount = newAmount;
	}
	food(){
	}
	void addFood(int moreFood){
		this.amount+=moreFood;
	}
	int getAmount(){
		return this.amount;
	}
	void clearAmount(){
		this.amount = 0;
	}
}

class CatPack implements FoodProcessor{
	private final int size;
	cat[] catArray;
	String baseName;
	CatPack(int newSize, String newName){
		size = newSize;
		baseName = newName;
		catArray = new cat[size];
		fillPack(size, baseName, catArray);
		listCats();
	}
	
	void fillPack(int newSize, String newName, cat[] newArray){
		for (int i=0; i<size; i++){
			newName=this.baseName+i;
			newArray[i] = new cat(newName);
		}
	}
	
	public void eat(food newFood){
		//divide food in portions
		int amountPerCat = newFood.getAmount()/this.size;
		int remainder = newFood.getAmount()%this.size;
		food[] foodPortions = new food[size];
		for (int i=0; i<size; i++){
			foodPortions[i]=new food(amountPerCat);
		}
		
		//feed portions
		for (int i=0; i<this.size;i++){
			this.catArray[i].eat(foodPortions[i]);
		}
		
		//calculate remaining food
		newFood.clearAmount();
		newFood.addFood(remainder);
		for (int i=0; i<this.size;i++){
			newFood.addFood(foodPortions[i].getAmount());
		}
	}
	
	void listCats(){
		System.out.println("Your pack of cats:");
		for (int i=0; i<size; i++){
			catArray[i].showCat();
		}
	}

}


