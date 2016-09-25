package com.Kerstin.JavaTutorial;

public class Accessories{
	
	private static class Bed{
		private final Color COLOR;
		Bed(Color newColor){
			this.COLOR=newColor;
		}
		public void getColor(){
			System.out.println("Color: " + this.COLOR);
		}
	}
	
	private static class Catalogue{
		Catalogue(){
			for (Color c : Color.values()){
				Bed oneBed = new Bed(c);
				oneBed.getColor();
			}
		}
		
	}
	
	@EnhancementRequest(id = 1235, synopsis = "gnsthsaehg")
	
	public static void main(String[] args){
	Bed newBed = new Bed(Color.RED);
	newBed.getColor();
	@SuppressWarnings("unused")
	Catalogue newCatalogue = new Catalogue();
	}
}