package com.Kerstin.Fun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class BullshitBingo implements ActionListener, MouseListener{
	
	private JFrame frame;
	private JPanel table;
	private boolean cellsEditable = false;
	private String saveFilePath;
	private GridLayout tableLayout;
	
	private BullshitBingo(){
		setFilePath();
		frame = new JFrame("Bullshit Bingo");
		this.initialize();
	}
	
	private void saveTable(){
		try{
			PrintWriter writer = new PrintWriter(saveFilePath, "UTF-8");
			int noOfCells = table.getComponentCount();
			for (int i = 0; i < noOfCells; i++){
				TableCell currentCell = (TableCell) table.getComponent(i);
				writer.println(currentCell.getText());
			}
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void setFilePath(){
		Path path = Paths.get(System.getProperty("user.home"), "BullshitBingo");
		//create BullshitBingo folder is it doesn't exist
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		path = Paths.get(path.toString(), "savedTable.txt");
		this.saveFilePath = path.toString();
	}
	
	private void initialize(){
		//initialize frame
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//first screen
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JLabel initialText = new JLabel("Do you want to load your saved table?");
		p1.add(initialText);
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");
		yes.addActionListener(this);
		yes.setActionCommand("yesloadsaved");
		no.addActionListener(this);
		no.setActionCommand("nodontload");
		p2.add(yes);
		p2.add(no);
		masterPanel.add(p1);
		masterPanel.add(p2);
		frame.add(masterPanel);
		frame.pack();
	}
	
	private void initializeMainComponents(boolean loadSaved){
		//initialize buttons in JPanel
		JPanel editButtons = new JPanel();
		editButtons.setLayout(new GridLayout(0,1));
		JButton randomizer = new JButton("Randomize");
		editButtons.add(randomizer);
		randomizer.addActionListener(this);
		randomizer.setActionCommand("randomize");
		JButton cellAdder = new JButton("Add Cells");
		editButtons.add(cellAdder);
		cellAdder.addActionListener(this);
		cellAdder.setActionCommand("addcells");
		JButton cellClearer = new JButton("Clear Selections");
		editButtons.add(cellClearer);
		cellClearer.addActionListener(this);
		cellClearer.setActionCommand("clear");
		JButton saver = new JButton("Save");
		editButtons.add(saver);
		saver.addActionListener(this);
		saver.setActionCommand("save");
		frame.add(editButtons);
		//initialize table
		 table = new JPanel();
		ArrayList<JPanel> tableContent = this.initializeTableContent(loadSaved);
		for (JPanel p : tableContent){
			p.addMouseListener(this);
			table.add(p);
		}
		frame.add(table);
		frame.setVisible(true);
		frame.pack();
	}
	
	private ArrayList<JPanel> initializeTableContent(boolean loadSaved){
		ArrayList<JPanel> tableContent = new ArrayList<JPanel>();
		double tableSize = 0;
		if (!loadSaved){
			tableSize = 2;
			String[] cellText = {"u der", "pls",  "ASAP", "???"};
			for (String s: cellText){
				tableContent.add(new TableCell(s));
			}
		} else{
			try{
				BufferedReader in = null;
				in = new BufferedReader(new FileReader(this.saveFilePath));
				String line;
				while ((line = in.readLine())!= null){
					tableContent.add(new TableCell(line));
				}
				in.close();
				//verify table is square
				tableSize = Math.pow(tableContent.size(), 0.5);
				if (tableSize != (int) tableSize){
					tableContent.clear();
					this.initializeTableContent(false);
				}
			} catch (IOException e){
				if (e instanceof FileNotFoundException){
					JOptionPane.showMessageDialog(this.frame, "No table saved, adding empty table!");
					this.initializeTableContent(false);
				} else{
					e.printStackTrace();
				}
			} 
		}
		tableLayout = new GridLayout(0,(int) tableSize);
		table.setLayout(tableLayout);
		return tableContent;
	}
	
	private void addTableCells(){
		int oldTableSize = this.tableLayout.getColumns() ;
		int oldCellNumber = oldTableSize * oldTableSize;
		int newCellNumber = (oldTableSize + 1) * (oldTableSize + 1);
		for(int i = oldCellNumber; i < newCellNumber ; i++){
			TableCell newCell = new TableCell();
			newCell.addMouseListener(this);
			this.table.add(newCell);
			newCell.edit();
		}
		this.tableLayout.setColumns(oldTableSize + 1);
		this.frame.pack();
	}
	
	private void randomize(){
		ArrayList<TableCell> tempList = new ArrayList<TableCell>();
		int noOfCells = table.getComponentCount();
		for (int i = 0; i < noOfCells; i++){
			tempList.add((TableCell) table.getComponent(i));
		}
		Collections.shuffle(tempList);
		this.table.removeAll();
		for (TableCell c : tempList){
			this.table.add(c);
		}
		this.frame.pack();
	}
	
	private boolean checkIfDone(){
		boolean done = true;
		int tableSize = tableLayout.getColumns();
//		TableCell currentCell;
//		boolean firstD = true, secondD = true, rows = true, cols = true;
//		
//		for (int i = 0; i < tableSize; i++){
//			//check 1st diagonal
//			if (firstD){
//				currentCell = (TableCell) table.getComponent(i + (tableSize*i));
//				firstD = currentCell.checked;
//			}
//			//check 2nd diagonal
//			if (secondD){
//				currentCell = (TableCell) table.getComponent(tableSize*(i+1) - (i + 1));
//				secondD = currentCell.checked;
//			}
//			//check rows
//			if (!rows){
//				rows = true;
//			}
//				for (int j = 0; j < tableSize; j++){
//					if (rows){
//						currentCell = (TableCell) table.getComponent(i * tableSize + j);
//						rows = currentCell.checked;
//					}
//				}
//			
//			//check columns
////			for (int j = 0; j < tableSize; j++){
////				cols = true;
////				if (cols){
////					currentCell = (TableCell) table.getComponent(i + j * tableSize);
////					cols = currentCell.checked;
////				}
////			}
//		}
//		if (firstD | secondD | rows){
//			return true;
//		} else return false;
		
		//check 1st diagonal
		for (int i = 0; i < tableSize; i++){
			TableCell currentCell = (TableCell) table.getComponent(i + (tableSize*i));
			if (done){
				done = currentCell.checked;
			}
		}
		if (done){
			return done;
		}
		//check 2nd diagonal
		done = true;
		for (int i = 0; i < tableSize; i++){
			TableCell currentCell = (TableCell) table.getComponent(tableSize*(i+1) - (i + 1));
			if (done){
				done = currentCell.checked;
			}
		}
		if (done){
			return done;
		}
		//check rows
		for (int i = 0; i < tableSize; i++){
			done = true;
			for (int j = 0; j < tableSize; j++){
				TableCell currentCell = (TableCell) table.getComponent(i * tableSize + j);
				if (done){
					done = currentCell.checked;
				}
			}
			if (done){
				return done;
			}
		}
		//check columns
		for (int i = 0; i < tableSize; i++){
			done = true;
			for (int j = 0; j < tableSize; j++){
				TableCell currentCell = (TableCell) table.getComponent(i + j * tableSize);
				if (done){
					done = currentCell.checked;
				}
			}
			if (done){
				return done;
			}
		}
		return done;
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand == "randomize"){
			this.randomize();
		}
		if (actionCommand == "addcells"){
			this.addTableCells();
		}
		if (actionCommand == "clear"){
			int noOfCells = table.getComponentCount();
			for (int i = 0; i < noOfCells; i++){
				TableCell currentCell = (TableCell) table.getComponent(i);
				if (currentCell.checked){
					currentCell.changeStatus();
				}
			}
			this.frame.pack();
		}
		if (actionCommand == "save"){
			this.saveTable();
		}
		if (actionCommand == "yesloadsaved"){
			this.frame.getContentPane().removeAll();
			this.initializeMainComponents(true);
		}
		if (actionCommand == "nodontload"){
			this.frame.getContentPane().removeAll();
			this.initializeMainComponents(false);
		}
	}

	public void mouseClicked(MouseEvent e) {
		TableCell target = (TableCell)e.getSource();
		if (e.getClickCount() == 2){
			target.edit();
		} else if (e.getClickCount() == 1) {
			if (!cellsEditable){
				target.changeStatus();
			} else{
				String newPhrase = JOptionPane.showInputDialog(this.frame, "Enter new phrase:", "Pls do the needful", 1);
				target.setText(newPhrase);
				this.cellsEditable = false;
			}
		}
	    if (this.checkIfDone()){
	    	JOptionPane.showMessageDialog(this.frame, "Yay, you won! Greetings to Rohit!");
	    } 
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent arg0) {	
	}
	
	@SuppressWarnings("serial")
	private class TableCell extends JPanel implements KeyListener{
		private JLabel text;
		private JTextField editField;
		private boolean checked;
		private boolean isEditable;
		
		TableCell(String newText){
			checked = false;
			text = new JLabel(newText);
			this.add(text);
			this.editField = new JTextField();
			this.editField.addKeyListener(this);
		}
		
		TableCell(){
			checked = false;
			text = new JLabel();
			this.add(text);
			this.editField = new JTextField();
			this.editField.addKeyListener(this);
		}
		
		public String getText(){
			return this.text.getText();
		}
		
		protected void setText(String newText){
			this.text.setText(newText);
			checked = false;
			this.setBackground(null);
		}
		
		protected void changeStatus(){
			checked = !checked;
			if (checked){
				this.setBackground(Color.GREEN);
			} else this.setBackground(null);
		}
		
		private void edit(){
			this.text.setVisible(false);
			this.editField.setSize(this.getSize());
			this.add(this.editField);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource()  instanceof  JTextField && e.getKeyCode() == KeyEvent.VK_ENTER){
				String newText = this.editField.getText();
				if (newText != ""){
					this.setText(newText);
				}
				this.remove(this.editField);
				this.text.setVisible(true);
				BullshitBingo.this.frame.pack();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	public static void main(String[] args){
		new BullshitBingo();
	}

}