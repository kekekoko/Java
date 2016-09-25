package com.Kerstin.reddit;

public class GarageDoor{
	//Challenge #260 Easy
	public static void main(String[] args){
		String[] newCommands = new String[8];
		newCommands[0]="button_clicked";
		newCommands[1]="cycle_complete";
		newCommands[2]="button_clicked";
		newCommands[3]="button_clicked";
		newCommands[4]="button_clicked";
		newCommands[5]="button_clicked";
		newCommands[6]="button_clicked";
		newCommands[7]="cycle_complete";
		GarageDoor.enterCommands(newCommands);
	}
	
	private static State state = State.CLOSED;
	private static void enterCommands(String[] commands){
		for (String command: commands){
			GarageDoor.processCommand(command);
		}	
	}
	private static void processCommand(String command){
		if (command == "button_clicked"){
			System.out.println("> Button was clicked.");
			switch (state){
				case OPENING:
					state = State.STOPPED_WHILE_OPENING;
					break;
				case OPEN: case STOPPED_WHILE_OPENING:
					state = State.CLOSING;
					break;
				case STOPPED_WHILE_CLOSING: case CLOSED:
					state = State.OPENING;
					break;
				case CLOSING:
					state = State.STOPPED_WHILE_CLOSING;
					break;
			}
		}
		else if (command == "cycle_complete"){
			System.out.println("> Cycle complete.");
			switch (state){
				case OPENING:
					state = State.OPEN;
					break;
				case CLOSING:
					state = State.CLOSED;
					break;
				default:
					System.out.println("Invalid command. The door's current state is " + state + ". Please click the button.");
			}
		}
		else System.out.println("Invalid command.");
		System.out.println("Door: " + state);
	}
}

enum State {
	OPEN, CLOSED, OPENING, CLOSING, STOPPED_WHILE_OPENING, STOPPED_WHILE_CLOSING
}