package com.Kerstin.Fun;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.*;

public class BullshitBingo implements ActionListener, MouseListener{
	
	private JFrame frame;
	private JPanel table;
	private boolean cellsEditable = false;
	private String saveFilePath;
	
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
		editButtons.setLayout(new BorderLayout());
		JButton phraseAdder = new JButton("Add phrase");
		editButtons.add(phraseAdder, BorderLayout.PAGE_START);
		phraseAdder.addActionListener(this);
		phraseAdder.setActionCommand("addphrase");
		JButton cellAdder = new JButton("AddCell");
		editButtons.add(cellAdder, BorderLayout.CENTER);
		cellAdder.addActionListener(this);
		cellAdder.setActionCommand("addcell");
		JButton saver = new JButton("Save");
		editButtons.add(saver, BorderLayout.PAGE_END);
		saver.addActionListener(this);
		saver.setActionCommand("save");
		frame.add(editButtons);
		//initialize table
		 table = new JPanel();
		GridLayout tableLayout = new GridLayout(0,4);
		table.setLayout(tableLayout);
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
		if (!loadSaved){
			int initialSize = 8;
			String[] cellText = {"u der", "pls",  "ASAP", "???", "do the needful", "man", "there", "tight deadline"};
			for (int i = 0; i < initialSize; i++){
				tableContent.add(new TableCell(cellText[i]));
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
			} catch (IOException e){
				if (e instanceof FileNotFoundException){
					JOptionPane.showMessageDialog(this.frame, "No table saved, adding empty table!");
				} else{
					e.printStackTrace();
				}
			} 
		}
		return tableContent;
	}
	
	private boolean checkIfDone(){
		int noOfCells = table.getComponentCount();
		for (int i = 0; i < noOfCells; i++){
			TableCell currentCell = (TableCell) table.getComponent(i);
			if (!currentCell.checked){
				return false;
			}
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand == "addphrase"){
			this.cellsEditable = true;
		}
		if (actionCommand == "addcell"){
			JPanel newCell = new TableCell("my new cell");
			newCell.addMouseListener(this);
			this.table.add(newCell);
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

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		TableCell target = (TableCell)e.getSource();
		if (e.getClickCount() == 1) {
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

	public void mouseReleased(MouseEvent arg0) {	
	}
	
	@SuppressWarnings("serial")
	private class TableCell extends JPanel{
		private JLabel text;
		boolean checked;
		
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
		
		TableCell(String newText){
			checked = false;
			text = new JLabel(newText);
			this.add(text);
		}
	}
	
	public static void main(String[] args){
		new BullshitBingo();
	}
}