package com.Kerstin.reddit;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
@SuppressWarnings("serial")
public class EscapeTrolls extends JFrame implements KeyListener, ActionListener{
	//[Weekly #25] Escape the trolls
    String maze;
    JLabel mazeGUI;
    int width, height; //without html tags
    int currentOrientation = 37;
    String myCharacter = "(";
    boolean caught = false;
    EscapeTrolls frame;
    ArrayList<Character> trolls;
    int noOfTrolls;
    
    private final String resetCommand = "ResetButton";
    
    public static void main(String[] args){
    	EscapeTrolls frame = new EscapeTrolls("Escape the trolls");
    	frame.requestFocus();
    }
    EscapeTrolls(String title){
    	@SuppressWarnings("unused")
		class trollButton extends JButton implements ActionListener{
    		public void actionPerformed(ActionEvent e) {
    			caught = false;
    			EscapeTrolls temp = new EscapeTrolls();
    			maze = temp.readMaze("C:\\temp\\maze.txt");
    			temp.dispose();
    			mazeGUI.setText(maze);
    			this.getParent().revalidate();
    			SwingUtilities.getRoot(this).requestFocus();
    			currentOrientation = 37;
    		    myCharacter = "(";
    		}
        }
    	class TrollThread implements Runnable{
    		public void run() {
    			int[] d = new int[noOfTrolls];
    			for (int i = 0; i < noOfTrolls; i++){
    				d[i] = 0;
    			}
    			while (true){
    				if (!caught){
    					for (int i = 0; i < noOfTrolls; i++){
    						d[i] = this.moveTrolls(d[i], trolls.get(i));
    	    			}
    					mazeGUI.setText(maze);
        				revalidate();
    					this.isCaught();
    				}
    				
    				try {
    					Thread.sleep(300);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}    				
    			}
    		}
    		
    		private void isCaught(){
    			for (int i = 0; i < noOfTrolls; i++){
    				int trollPos = maze.indexOf(trolls.get(i));
        	    	int charPos = maze.indexOf(myCharacter);
        	    	if((charPos - 1 == trollPos) || (charPos - (width+4) == trollPos) || (charPos + 1 == trollPos) || (charPos + (width + 4) == trollPos)){
        	    		caught = true;
        	    		JOptionPane.showMessageDialog(frame,"You got caught!");
        	    	}
    			}
    	    	
    		}
    		
    		private int moveTrolls(int currentDirection, char currentTroll){
    	    	boolean lP = true, uP = true, rP = true, dP = true;
    	    	boolean trollMoved = false;
    	    	StringBuilder buildMaze = new StringBuilder(maze);
    	    	int currentTrollPosition = maze.indexOf(currentTroll);
    	    	Random trollPlacer = new Random();
    	    	int trollDirection = currentDirection;
    	    	while (!trollMoved && (lP || uP || rP || dP)){
    	    		try{
    	            	switch (trollDirection){
    	            	case 0: if(maze.charAt(currentTrollPosition - 1) == ' '){
    	            		buildMaze.replace(currentTrollPosition - 1, currentTrollPosition, String.valueOf(currentTroll));
    	            		buildMaze.replace(currentTrollPosition, currentTrollPosition+ 1, " ");
    	            		trollMoved = true;
    	            	} else {
    	            		lP = false;
    	            		trollDirection = trollPlacer.nextInt(4);
    	            	}
    	            	break;
    	            	case 1: if(maze.charAt(currentTrollPosition-(width+4)) == ' '){
    	            		buildMaze.replace(currentTrollPosition-(width+4), currentTrollPosition-(width+4) + 1, String.valueOf(currentTroll));
    	            		buildMaze.replace(currentTrollPosition, currentTrollPosition+ 1, " ");
    	            		trollMoved = true;
    	            	} else {
    	            		uP = false;
    	            		trollDirection = trollPlacer.nextInt(4);
    	            	}
    	            	break;
    	            	case 2: if(maze.charAt(currentTrollPosition + 1) == ' '){
    	            		buildMaze.replace(currentTrollPosition + 1, currentTrollPosition + 2, String.valueOf(currentTroll));
    	            		buildMaze.replace(currentTrollPosition, currentTrollPosition+ 1, " ");
    	            		trollMoved = true;
    	            	} else {
    	            		rP = false;
    	            		trollDirection = trollPlacer.nextInt(4);
    	            	}
    	            	break;
    	            	case 3: if(maze.charAt(currentTrollPosition +(width+4)) == ' '){
    	            		buildMaze.replace(currentTrollPosition +(width+4), currentTrollPosition +(width+4) + 1, String.valueOf(currentTroll));
    	            		buildMaze.replace(currentTrollPosition, currentTrollPosition+ 1, " ");
    	            		trollMoved = true;
    	            	} else {
    	            		dP = false;
    	            		trollDirection = trollPlacer.nextInt(4);
    	            	}
    	            	break;
    	            	}
    	    		} catch (IndexOutOfBoundsException e){
    	    			
    	    		}
    	    	}
    	    	maze = buildMaze.toString();
				return trollDirection;
    	    }
        	
        }
    	
    	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setLayout(new FlowLayout());
        maze=this.readMaze("C:\\temp\\maze.txt");
        mazeGUI = new JLabel(maze);
        this.add(mazeGUI);
        this.addKeyListener(this);
//        trollButton reset = new trollButton();
        JButton reset = new JButton();
        reset.setText("Refresh");
        reset.setSize(100, 50);
        reset.addActionListener(this);
        reset.setActionCommand(resetCommand);
        this.add(reset);
        this.pack();
        this.setVisible(true);
        (new Thread(new TrollThread())).start();
        this.requestFocus();
    }
    EscapeTrolls(){
    }
    
    private String readMaze(String filePath){
    	Scanner scanner = null;
    	Scanner s = null;
    	String newMaze = "";
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
            s = scanner.useDelimiter("\\Z");
            while (s.hasNext()){
                newMaze = s.next();
            }
        } catch (FileNotFoundException e) {
            newMaze = "File not found!!";
        } finally {
            if (s != null) {
                s.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
        newMaze = newMaze.replaceAll("\r\n", "<br>");
        this.width = newMaze.indexOf("<br>");
        this.height = (newMaze.lastIndexOf("<br>")+4)/(width+4)+1;
        Random rd = new Random();
        boolean trollsPlaced = false;
        this.trolls = new ArrayList<Character>(Arrays.asList('ß', 'ä', 'w', '%', 'ö', '§', '&', '@'));
        this.noOfTrolls = trolls.size();
        int newTrollIndex;
        for (int i = 0; i < noOfTrolls; i++){
        	trollsPlaced = false;
        	while (!trollsPlaced){
                newTrollIndex = rd.nextInt(newMaze.length());
                if (newMaze.charAt(newTrollIndex) == ' '){
                    newMaze = newMaze.substring(0, newTrollIndex) + trolls.get(i) + newMaze.substring(newTrollIndex+1, newMaze.length());
                    trollsPlaced=true;
                }
            }
        }
        
        newMaze = "<html><body><pre>"+newMaze+"</pre></body></html>";
        return newMaze;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals(resetCommand)){
			caught = false;
			EscapeTrolls temp = new EscapeTrolls();
			maze = temp.readMaze("C:\\temp\\maze.txt");
			temp.dispose();
			mazeGUI.setText(maze);
			this.repaint();
//			this.getParent().revalidate();
			SwingUtilities.getRoot(this).requestFocus();
			currentOrientation = 37;
		    myCharacter = "(";
    	}
    	
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    	if(!caught){
    		this.move(e.getKeyCode());
            mazeGUI.setText(maze);
            this.revalidate();
    	} else JOptionPane.showMessageDialog(this,"You got caught, press refresh to restart!");
        
    }
    @Override
    public void keyReleased(KeyEvent e) {        
    }
    
    private void move(int direction){
        ArrayList<Integer> implementedKeys = new ArrayList<Integer>(Arrays.asList(37, 38, 39, 40));
        if (implementedKeys.contains(direction) && this.movePossible(direction)){
            StringBuilder buildMaze = new StringBuilder(maze);
            int currentPosition=maze.indexOf(myCharacter);
            switch (direction){
            case 37:
                if (currentOrientation == direction){
                    if (maze.charAt(currentPosition-1) == '#'){
                        buildMaze.replace(currentPosition-2, currentPosition-1, "#");
                    }
                    buildMaze.replace(currentPosition-1, currentPosition, "(");
                    buildMaze.replace(currentPosition, currentPosition+1, " ");
                    if (maze.charAt(currentPosition-1) == 'X'){
                        JOptionPane.showMessageDialog(this,"You found the exit!");
                    }
                } else buildMaze.replace(currentPosition, currentPosition+1, "(");
                currentOrientation = direction;
                myCharacter = "(";
                break;
            case 38:
                if (currentOrientation == direction){
                    if (maze.charAt(currentPosition-(this.width+4)) == '#'){
                        buildMaze.replace(currentPosition-(this.width+4)*2, currentPosition-(this.width+4)*2 +1, "#");
                    }
                    buildMaze.replace(currentPosition-(this.width+4), currentPosition-(this.width+4) + 1, "n");
                    buildMaze.replace(currentPosition, currentPosition+1, " ");
                    if (maze.charAt(currentPosition-(this.width+4)) == 'X'){
                        JOptionPane.showMessageDialog(this,"You found the exit!");
                    }
                } else buildMaze.replace(currentPosition, currentPosition+1, "n");           
                currentOrientation = direction;
                myCharacter = "n";
                
                break;
            case 39:
                if (currentOrientation == direction){
                    if(maze.charAt(currentPosition+1) == '#'){
                        buildMaze.replace(currentPosition+2, currentPosition+3, "#");
                    }
                    buildMaze.replace(currentPosition+1, currentPosition+2, ")");
                    buildMaze.replace(currentPosition, currentPosition+1, " ");
                    if (maze.charAt(currentPosition+1) == 'X'){
                        JOptionPane.showMessageDialog(this,"You found the exit!");
                    }
                } else buildMaze.replace(currentPosition, currentPosition+1, ")");        
                myCharacter = ")";
                currentOrientation = direction;
                
                break;
            case 40:
                if (currentOrientation == direction){
                    if (maze.charAt(currentPosition+(this.width+4)) == '#'){
                        buildMaze.replace(currentPosition+(this.width+4)*2, currentPosition+(this.width+4)*2+1, "#");
                    }
                    buildMaze.replace(currentPosition+(this.width+4), currentPosition+(this.width+4)+1, "u");
                    buildMaze.replace(currentPosition, currentPosition+1, " ");
                    if (maze.charAt(currentPosition+(this.width+4)) == 'X'){
                        JOptionPane.showMessageDialog(this,"You found the exit!");
                    }
                } else buildMaze.replace(currentPosition, currentPosition+1, "u");      
                myCharacter = "u";
                currentOrientation = direction;
                
                break;
            }
            
            maze = buildMaze.toString();
        } else Toolkit.getDefaultToolkit().beep();
    }
    
    private boolean movePossible(int direction){
        int currentPosition=maze.indexOf(myCharacter);
        try{
            switch (direction){
            case 37:{
                if (maze.charAt(currentPosition-1) == ' ' || maze.charAt(currentPosition-1) == 'X' || (maze.charAt(currentPosition-1) == '#' && maze.charAt(currentPosition-2) == ' ')){
                    return true;
                } else return false;
            }
            case 38:{
                if (maze.charAt(currentPosition-(this.width+4)) == ' '|| maze.charAt(currentPosition-(this.width+4)) == 'X' || (maze.charAt(currentPosition-(this.width+4)) == '#' && maze.charAt(currentPosition-(this.width+4)*2) == ' ')){
                    return true;
                } else return false;
            }
            case 39:{
                if (maze.charAt(currentPosition+1) == ' ' || maze.charAt(currentPosition+1) == 'X' || (maze.charAt(currentPosition+1) == '#' && maze.charAt(currentPosition+2) == ' ')){
                    return true;
                } else return false;
            }
            case 40:{
                if (maze.charAt(currentPosition+(this.width+4)) == ' ' || maze.charAt(currentPosition+(this.width+4)) == 'X' || (maze.charAt(currentPosition+(this.width+4)) == '#' && maze.charAt(currentPosition+(this.width+4)*2) == ' ')){
                    return true;
                } else return false;
            }
            default:{
                return false;
            }
            }
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }
}