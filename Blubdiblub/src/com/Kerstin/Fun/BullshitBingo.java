package com.Kerstin.Fun;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.awt.font.TextAttribute;

import javax.swing.*;

public class BullshitBingo implements ActionListener, MouseListener{
	
	private JFrame mainWindow;
	private JTable table;
	private final Object[][] choices;
	private final Object[] columns = {"1", "2", "3"};
	private boolean cellsEditable = false;
	
	private BullshitBingo(){
		choices = new Object[2][3];
		Object[] col1 = {"test", "bla", "blo"};
		JPanel testpanel = new JPanel();
		testpanel.setBackground(Color.RED);
		testpanel.setVisible(true);
		JLabel testlabel = new JLabel("label");
		testlabel.setVisible(true);
		testpanel.add(testlabel);
		testpanel.setLayout(new FlowLayout());
		testpanel.repaint();
		Object[] col2 = {"blu", "ble", testpanel};
		choices[0] = col1;
		choices[1] = col2;
		mainWindow = new JFrame("Bullshit Bingo");
		mainWindow.setLayout(new FlowLayout());
		mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
		table = new JTable(choices, columns);
		table.addMouseListener(this);
		table.setVisible(true);
		JButton phraseAdder = new JButton("Add phrase");
		mainWindow.add(phraseAdder);
		phraseAdder.addActionListener(this);
		mainWindow.add(table);
		mainWindow.pack();
	}
	
	private boolean checkIfDone(){
		int rows = this.table.getRowCount();
		int cols = this.table.getColumnCount();
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				if (this.table.getValueAt(i, j) != "done"){
					return false;
				}
			}
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
		this.cellsEditable = true;
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
	      JTable target = (JTable)e.getSource();
	      if (e.getClickCount() == 1) {
    		  int row = target.rowAtPoint(e.getPoint());
		      int column = target.columnAtPoint(e.getPoint());
	    	  if (!cellsEditable){
			      target.setValueAt("done", row, column);
	    	  } else{
	    		  String newPhrase = JOptionPane.showInputDialog(this.mainWindow, "Enter new phrase:", "Pls do the needful", 1);
	    		  target.setValueAt(newPhrase, row, column);
	    		  this.cellsEditable = false;
	    	  }
		      
	      }
	      if (this.checkIfDone()){
	    	  JOptionPane.showMessageDialog(this.mainWindow, "Yay, you won! Greetings to Rohit!");
	      } 

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
	
	public static void main(String[] args){
		new BullshitBingo();
	}
}