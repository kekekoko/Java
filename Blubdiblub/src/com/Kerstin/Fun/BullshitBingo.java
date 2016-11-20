package com.Kerstin.Fun;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BullshitBingo implements ActionListener, MouseListener, ChangeListener{
	
	private JFrame frame;
	private JPanel table;
	private GridLayout tableLayout;
	private String databasePath;
	private ArrayList<String> defaultValues = new ArrayList<String>(Arrays.asList("doesn't understand docu", "'screenshot'", "ok after auto-message", "ETA", "ok but doesn't understand", "at the earliest", "misspelled name", "hi team", "the same", "not getting it", "step by step", "incidence", "not working", "open and close chat", "Document FR", "please help", "urgent", "user falls asleep", "hi", "funny bug", "u der", "pls", "ASAP", "???", "do the needful", "there?", "wrong answer from 2nd", "no contribution from 2nd", "tight deadline", "critical issue", "trail version", "XBox", "Tosco", "any update", "yes to polar question", "ridiculous abbreviation", "please check", "ther", "man", ":)"));
	private boolean isMuted = false;
	private int difficulty = 0;
	
	private BullshitBingo(){
		createDatabase();
		frame = new JFrame("Bullshit Bingo");
		this.initialize();
	}
		
	private void playAudio(boolean won){
		if (!this.isMuted){
			 try {          
		    	  URL url = BullshitBingo.class.getResource("/nobingo.wav");
		    	  AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
		          AudioFormat format = inputStream.getFormat();
		          DataLine.Info info = new DataLine.Info(Clip.class, format);
		          Clip clip = (Clip)AudioSystem.getLine(info);
		          clip.open(inputStream);
		          clip.start(); 	          
		      } catch (Exception e){
		    	  e.printStackTrace();
		      }
		}
	}
	
	private void saveTable(){
		int noOfCells = table.getComponentCount();
		try (Connection conn = DriverManager.getConnection(this.databasePath)) { 
			Statement stmt = conn.createStatement();
			stmt.executeUpdate( "CREATE TABLE IF NOT EXISTS savedValues (Name VARCHAR UNIQUE, IsSelected INTEGER);");
			stmt.executeUpdate("DELETE FROM savedValues");
			for (int i = 0; i < noOfCells; i++){
				TableCell currentCell = (TableCell) table.getComponent(i);
				int currentDbBool = currentCell.checked ? 1 : 0;
				stmt.executeUpdate("INSERT OR IGNORE INTO savedValues (Name, IsSelected) VALUES (\"" + currentCell.getText() + "\", " + currentDbBool + ");");
			}
        } catch (SQLException e) {
        	System.out.println("something went completely wrong, looks like you broke the db. wtf did you do?");
            System.out.println(e.getMessage());
        }
	}
		
	private void createDatabase() {
		Path path = Paths.get(System.getProperty("user.home"), "BullshitBingo");
		//create BullshitBingo folder is it doesn't exist
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//dtabase stuff
		path = Paths.get(path.toString(), "BullshitDB.db");
		this.databasePath = "jdbc:sqlite:" + path.toString();
		try (Connection conn = DriverManager.getConnection(this.databasePath)) { 
			Statement stmt = conn.createStatement();
			stmt.executeUpdate( "CREATE TABLE IF NOT EXISTS defaultValues (Name VARCHAR UNIQUE);");
			for (String newValue : this.defaultValues){
				stmt.executeUpdate("INSERT OR IGNORE INTO defaultValues (Name) VALUES (\"" + newValue + "\");");
			}
        } catch (SQLException e) {
        	System.out.println("something went completely wrong, looks like you broke the db. wtf did you do?");
            System.out.println(e.getMessage());
        }
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
		int maxDifficulty = 6;
		Integer[] difficulties = new Integer[maxDifficulty - 1];
		for (int i = 0; i < maxDifficulty - 1; i++){
			difficulties[i] = i+2;
		}
		JSpinner difficulty = new JSpinner(new SpinnerListModel(difficulties));
		difficulty.addChangeListener(this);
		p2.add(yes);
		p2.add(no);
		p2.add(difficulty);
		masterPanel.add(p1);
		masterPanel.add(p2);
		frame.add(masterPanel);
		frame.pack();
	}
	
	private void initializeMainComponents(boolean loadSaved){
		//initialize buttons and stuff in JPanel
		JPanel editButtons = new JPanel();
		editButtons.setLayout(new GridLayout(0,1));
		JButton randomizer = new JButton("Randomize Cells");
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
		JCheckBox mute = new JCheckBox("Stop this sound!!!!!!");
		editButtons.add(mute);
		mute.setFocusPainted(false);
		mute.addMouseListener(this);
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
		//new sqlite part
		try (Connection newConnection = DriverManager.getConnection(this.databasePath)) { 
			Statement newStatement = newConnection.createStatement();
			if (!loadSaved){
				ResultSet defaultValues = newStatement.executeQuery("SELECT * FROM defaultValues");
				ArrayList<String> allValues = new ArrayList<String>();
				while (defaultValues.next()){
					String newValue = defaultValues.getString("Name");
					allValues.add(newValue);
				}
				tableSize = this.difficulty;
				Collections.shuffle(allValues);
				for (int i = 0; i < tableSize * tableSize; i++){
					tableContent.add(new TableCell(allValues.get(i)));
				}
			} else {
				ResultSet savedValues = newStatement.executeQuery("SELECT * FROM savedValues");
				LinkedHashMap<String, Integer> savedTable = new LinkedHashMap<String, Integer>();
				while (savedValues.next()){
					String newValue = savedValues.getString("Name");
					Integer newState  = savedValues.getInt("IsSelected");
					savedTable.put(newValue, newState);
				}
				//maybe add some logic to make sure table is square
				Set<String> savedStrings = savedTable.keySet();
				for (String s : savedStrings){
					TableCell newCell = new TableCell(s);
					if (savedTable.get(s) != 0){
						newCell.changeStatus();
					}
					tableContent.add(newCell);
				}
				tableSize = Math.pow(savedTable.size(), 0.5);
			}
		} catch (SQLException e1) {
			System.out.println("something went completely wrong with the db, wtf did you do?");
		}
		tableLayout = new GridLayout(0,(int) tableSize);
		table.setLayout(tableLayout);
		return tableContent;
	}
	
	private void addTableCells(){
		int oldTableSize = this.tableLayout.getColumns() ;
		int oldCellNumber = oldTableSize * oldTableSize;
		int newCellNumber = (oldTableSize + 1) * (oldTableSize + 1);
		this.tableLayout.setColumns(oldTableSize + 1);
		for(int i = oldCellNumber; i < newCellNumber ; i++){
			TableCell newCell = new TableCell();
			newCell.addMouseListener(this);
			this.table.add(newCell);
		}
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
		if (!done){
			this.playAudio(false);
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
		if (e.getSource().getClass() == TableCell.class){
			TableCell target = (TableCell)e.getSource();
			if (e.getButton() == MouseEvent.BUTTON3){
				target.edit();
			} else if (e.getButton() == MouseEvent.BUTTON1 && target.getText() != "") {
					target.changeStatus();
					if (this.checkIfDone()){
				    	JOptionPane.showMessageDialog(this.frame, "Yay, you won!");
				    } 
			}
		} else if (e.getSource().getClass() == JCheckBox.class){
			JCheckBox source = (JCheckBox) e.getSource();
			this.isMuted = source.isSelected();
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
			this.setLayout(new GridBagLayout());
			checked = false;
			text = new JLabel(newText);
			this.add(text);
			this.editField = new JTextField();
			this.editField.addKeyListener(this);
		}
		
		TableCell(){
			this.setLayout(new GridBagLayout());
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
			this.remove(this.text);
			this.add(this.editField);
			this.editField.setSize(this.getSize());
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getSource()  instanceof  JTextField && e.getKeyCode() == KeyEvent.VK_ENTER){
				String newText = this.editField.getText();
				if (newText != ""){
					this.setText(newText);
				}
				this.remove(this.editField);
				this.add(this.text);
				BullshitBingo.this.frame.revalidate();
				BullshitBingo.this.frame.repaint();
			} 
			else if (e.getSource()  instanceof  JTextField && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.remove(this.editField);
				this.add(this.text);
				BullshitBingo.this.frame.repaint();
				BullshitBingo.this.frame.revalidate();
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
        	System.out.println("What weird kind of system are you using? Get rid of it!");
        	System.out.println(e.getStackTrace());
        }
		new BullshitBingo();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JSpinner){
			JSpinner spinner = (JSpinner) e.getSource();
			this.difficulty = (int) spinner.getValue();
		}
	}

}