package com.Kerstin.TestStuff;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.IOException;

import javax.swing.JDialog;

public class PopUp extends JDialog{

	public PopUp(Dialog owner, String title) {
		super(owner, title);
		// TODO Auto-generated constructor stub
		this.setSize(1000, 1000);
		this.setVisible(true);
		Button button = new Button();
		this.add(button);
		button.setSize(200, 50);
		button.setLabel("Start Google");
		button.addActionListener(this.new test());
		button.setVisible(true);
		TextField textfield = new TextField();
		this.add(textfield);
		//textfield.addActionListener(arg0);
		textfield.addTextListener(this.new test2());
		textfield.setText("blablo");
		textfield.setEditable(true);
		textfield.setSize(200, 50);
		textfield.setVisible(true);
	}

	public class test implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("click click");
			try {
			    Process p = Runtime.getRuntime().exec("\"/Program Files (x86)/Google/Chrome/Application/chrome.exe\" \"www.google.at\"");
			    p.waitFor();
			    System.out.println("Google Chrome launched!");
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
		
	}
	
	public class test2 implements TextListener{

		@Override
		public void textValueChanged(TextEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("somethinghappened");
		}

		
	}
	
}