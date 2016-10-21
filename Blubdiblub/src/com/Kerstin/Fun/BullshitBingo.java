package com.Kerstin.Fun;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.awt.font.TextAttribute;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

public class BullshitBingo implements ActionListener, MouseListener{
	
	private JFrame mainWindow;
	private JPanel table;
	private boolean cellsEditable = false;
	
	private BullshitBingo(){
		mainWindow = new JFrame("Bullshit Bingo");
		this.initialize(mainWindow);
	}
	
	private void initialize(JFrame frame){
		//initialize frame
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//initialize buttons
		JButton phraseAdder = new JButton("Add phrase");
		frame.add(phraseAdder);
		phraseAdder.addActionListener(this);
		phraseAdder.setActionCommand("addphrase");
		JButton cellAdder = new JButton("AddCell");
		frame.add(cellAdder);
		cellAdder.addActionListener(this);
		cellAdder.setActionCommand("addcell");
		//initialize table
		 table = new JPanel();
		GridLayout tableLayout = new GridLayout(0,4);
		table.setLayout(tableLayout);
		JPanel[] tableContent = this.initializeTableContent();
		for (JPanel p : tableContent){
			p.addMouseListener(this);
			table.add(p);
		}
		frame.add(table);
		frame.pack();
	}
	
	private JPanel[] initializeTableContent(){
		int initialSize = 8;
		JPanel[] tableContent = new JPanel[initialSize];
		String[] cellText = {"u der", "pls",  "ASAP", "???", "do the needful", "man", "there", "tight deadline"};
		for (int i = 0; i < initialSize; i++){
			tableContent[i] = new TableCell(cellText[i]);
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
		Object target = e.getSource();
		String actionCommand = e.getActionCommand();
		if (actionCommand == "addphrase"){
			this.cellsEditable = true;
		}
		if (actionCommand == "addcell"){
			JPanel newCell = new TableCell("my new cell");
			newCell.addMouseListener(this);
			this.table.add(newCell);
			this.mainWindow.pack();
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
				String newPhrase = JOptionPane.showInputDialog(this.mainWindow, "Enter new phrase:", "Pls do the needful", 1);
				target.setText(newPhrase);
				this.cellsEditable = false;
			}
		}
	    if (this.checkIfDone()){
	    	JOptionPane.showMessageDialog(this.mainWindow, "Yay, you won! Greetings to Rohit!");
	    } 
	}

	public void mouseReleased(MouseEvent arg0) {	
	}
	
	private class TableCell extends JPanel{
		private JLabel text;
		boolean checked;
		
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