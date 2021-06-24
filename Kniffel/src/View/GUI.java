package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame{
	
	//TESTMAIN - Nur zu Testzwecken
	public static void main(String[] args) {
		new GUI("Kniffel");
	}
	
	public GUI(String titel) {
		super(titel);
		Container cp = getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		cp.setBackground(Color.LIGHT_GRAY);
		cp.setLayout(new GridBagLayout());
		
		JPanel sheet = new JPanel(new FlowLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.6;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		
		sheet.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cp.add(sheet, c);
			
		JPanel dice = new JPanel(new FlowLayout());
		dice.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		c.weightx = 0.4;
		c.gridx = 1;
		c.gridy = 0;
		
		cp.add(dice, c);
		
		setSize(800, 800);
		setLocation(300, 0);
		setVisible(true);
		setResizable(false);
	}
}
