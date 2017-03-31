package de.halemba.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
	JPanel btnPanel;
	JTextField[][] fields;
	JFrame frame;
	
	//Buttons
	JButton startbtn;
	JButton posStepBtn;
	JButton resetbtn;
	
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
		
		//Create Button Panel
		btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		frame.add(btnPanel, java.awt.BorderLayout.SOUTH);
		
		//Create Subpanels
		subPanels = new JPanel[9];
		fields = new JTextField[9][9];
	
		for (int i = 0; i<9; i++) {
			subPanels[i] = new JPanel();
			subPanels[i].setLayout(new GridLayout(3,3));
			subPanels[i].setBorder(blackline);
			mainpanel.add(subPanels[i]);
		}
		
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9; j++) {
				fields[i][j] = new JTextField("0", 5);
				subPanels[Helper.getQuadrant(i, j)].add(fields[i][j]);
			}
		}
		
		
		createButtons();
		frame.setVisible(true);
	}
	
	public void createButtons() {
		//Create Buttons
				startbtn = new JButton("Start");
				btnPanel.add(startbtn);
				startbtn.addActionListener(this.solver);
				
				posStepBtn = new JButton("Possible Check");
				btnPanel.add(posStepBtn);
				posStepBtn.addActionListener(this.solver);
				
				resetbtn = new JButton("Reset");
				btnPanel.add(resetbtn);
				resetbtn.addActionListener(this.solver);
	}
	
	public JButton getStartButton() {
		return startbtn;
	}	
	
	public JButton getPosButton() {
		return posStepBtn;
	}
	
	public JButton getResetButton() {
		return resetbtn;
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
