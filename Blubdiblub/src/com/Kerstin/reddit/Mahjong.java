package com.Kerstin.reddit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Mahjong{
	//Challenge #259 Intermediate
	public static void main (String[] args){
		new Mahjong().createHand();
	}
	
	
	public void createHand(){
		new Hand();
		
		new Hand(new Card(0,4), new Card(0,5), new Card(0,6), new Card(1,1), new Card(1,2), new Card(1,3), new Card(2,2), new Card(2,2), new Card(2,2), new Card(0,1), new Card(0,1), new Card(1,7), new Card(1,8), new Card(1,9));
		new Hand(new Card(0,4), new Card(1,1), new Card(0,5), new Card(1,2), new Card(2,2), new Card(1,3), new Card(2,2), new Card(0,6), new Card(2,2), new Card(0,1), new Card(1,8), new Card(0,1), new Card(1,7), new Card(1,9));
		new Hand(new Card(0,4), new Card(0,5), new Card(0,6), new Card(0,4), new Card(0,5), new Card(0,6), new Card(0,1), new Card(0,1), new Card(1,7), new Card(1,8), new Card(1,9), new Card(0,4), new Card(0,5), new Card(0,6));
		new Hand(0,4,0,4,2,5,2,5,0,9,0,9,0,5,0,5,0,7,0,7,0,8,0,8,0,8,0,8);
		new Hand(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		
		new Hand(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,1,1);
	}
	
	
	private class Card{
		//suit 1-3, rank 1-9
		private final Integer suit, rank;
		Card(int newSuit, int newRank){
			this.suit = newSuit;
			this.rank = newRank;
		}
		public void printCard(){
			System.out.println("Suit: " + this.suit + ", Rank: " + this.rank);
		}
		
	}
	
	private class Hand{
		private final int size = 14;
		private final Card[] cards = new Card[size];
		//random hand constructor
		Hand(){
			for (int i = 0; i < size; i++){
				this.cards[i] = new Card(new Random().nextInt((2) + 1), new Random().nextInt((8) + 1));
			}
			this.isWinning();
			
		}
		//specific hand constructor
		Hand(Card... newCards){
			int i = 0;
			for (Card newCard : newCards){
				if (i<this.size){
					this.cards[i] = newCard;
					i+=1;
				}
			}
			this.isWinning();
		}
		
		//hand made of integers constructor
		Hand(int... newInts){
			if (newInts.length == 28){
				for (int i = 0; i<28; i+=2){
					this.addCard(new Card(newInts[i], newInts[i+1]), i/2);
				}
				this.isWinning();
			}
		}
		
		public void printHand(){
			for (int i = 0; i < size; i++){
				this.cards[i].printCard();
			}
		}
		public void sortHand(){
			Arrays.sort(cards, new Comparator<Card>() {
		        public int compare(Card one, Card other) {
		        	if (one.suit.compareTo(other.suit) == 0){
		        		return one.rank.compareTo(other.rank);
		        	} else return one.suit.compareTo(other.suit);
		        };
			});
		}
		
		public void addCard(Card newCard, int index){
			this.cards[index] = newCard;
		}
		
		public void isWinning(){
			//sort suitwise then rankwise
			this.sortHand();
			this.printHand();
			//check for pairs
			boolean winning = true;
			//gives the option to step back in the is sequence part if the one before was a triple
			boolean lastWasTriple = false;
			int j = 0;
			while ((j < this.size-2) && winning){
				if (this.cards[j].suit == this.cards[j+1].suit){
					//is pair?
					if (this.cards[j].rank == this.cards[j+1].rank){
						winning = true;
						lastWasTriple = false;
						//is triple?
						if ((this.cards[j].suit == this.cards[j+2].suit) && (this.cards[j].rank == (this.cards[j+2].rank))){
							j+=3;
							lastWasTriple = true;
						} else j+=2;
					} //is sequence?
					else if ((this.cards[j].rank == (this.cards[j+1].rank - 1)) && (this.cards[j].suit == this.cards[j+2].suit) && (this.cards[j].rank == (this.cards[j+2].rank - 2))){
						winning = true;
						lastWasTriple = false;
						j+=3;
					} 
					else if (lastWasTriple == true){
						winning = true;
						lastWasTriple = false;
						j-=1;
					} else winning = false;
				} //if the combination before was a triple, step one card back and reconsider the third card of the triple for the next combination
				else if (lastWasTriple == true){
					winning = true;
					lastWasTriple = false;
					j-=1;
				}
				else winning = false;
			}
			while ((j < this.size-1) && winning){
				if (this.cards[j].suit == this.cards[j+1].suit){
					//is pair?
					if (this.cards[j].rank == this.cards[j+1].rank){
						winning = true;
						lastWasTriple = false;
						j+=2;
					}
					else if (lastWasTriple == true){
						winning = true;
						lastWasTriple = false;
						j-=1;
					} else winning = false;
				} else winning = false;
			}
			if (winning){
				System.out.println("This is a winning hand");
			} else System.out.println("This is not a winning hand");	
		}
	}
}