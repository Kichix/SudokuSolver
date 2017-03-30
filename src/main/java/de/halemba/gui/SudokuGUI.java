package de.halemba.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import de.halemba.elements.Field;
import de.halemba.helpers.Helper;
import de.halemba.solver.Solver;

public class SudokuGUI {
	
	JPanel mainpanel;
	JPanel[] subPanels;
	JTextField[][] fields;
	JFrame frame;
	JButton startbtn;
	JButton posStepBtn;
	Solver solver;
	
	public SudokuGUI(Solver solver) {
		
		this.solver = solver;
		
		//Blackline Border 
		Border blackline;
        blackline = BorderFactory.createLineBorder(Color.black);
        
		//Create Frame
		frame = new JFrame();
		frame.setSize(600, 600);
		frame.setTitle("SudokuSolver");
		frame.setLayout(new java.awt.BorderLayout());
		
		//Create Main Panel
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(3,3));
		frame.add(mainpanel, java.awt.BorderLayout.CENTER);
		
		//Create Subpanels
		subPanels = new JPanel[9];
		fields = new JTextField[9][9];
	
		for (int i = 0; i<9; i++) {
			subPanels[i] = new JPanel();
			subPanels[i].setLayout(new GridLayout(3,3));
			subPanels[i].setBorder(blackline);
			mainpanel.add(subPanels[i]);
			System.out.println("Created Panel: "+i);
		}
		
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9; j++) {
				fields[i][j] = new JTextField("0", 5);
				subPanels[Helper.getQuadrant(i, j)].add(fields[i][j]);
			}
		}
		
		//Create Buttons
		startbtn = new JButton("Start");
		frame.add(startbtn, java.awt.BorderLayout.PAGE_END);
		startbtn.addActionListener(this.solver);
		
		posStepBtn = new JButton("Possible Check");
		frame.add(posStepBtn, java.awt.BorderLayout.EAST);
		posStepBtn.addActionListener(this.solver);
		
		frame.setVisible(true);
	}
	
	public JButton getStartButton() {
		return startbtn;
	}	
	
	public JButton getPosButton() {
		return posStepBtn;
	}
	
	public int getFieldValue(int i, int j) {
		return Integer.parseInt(fields[i][j].getText());
	}
	
	public void updateFields(Field[][] f) {
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9; j++) {
				fields[i][j].setText(Integer.toString(f[i][j].getNumber()));
			}
		}	
	}

}
