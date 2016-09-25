package com.Kerstin.JavaTutorial;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class CustomBla{
	public static void main (String[] args){
		new CustomBla().new CustomFrame("bla");
	}
	
	private class CustomFrame extends JFrame implements ActionListener{
		private String title;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		CustomFrame(String title){
			this.setTitle(title);
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			JPanel panel = new JPanel();
			
			CustomButton button = new CustomButton("button");
			panel.add(button);
			panel.setVisible(true);
			this.add(panel);
			this.pack();
			this.setVisible(true);
		}
	}
	
	private class CustomButton extends JComponent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		CustomButton(String text){
			JLabel buttonLabel = new JLabel(text);
			buttonLabel.setVisible(true);
			this.add(buttonLabel);
			this.setOpaque(true);
			this.setVisible(true);
		}
		
	}
}