package de.halemba.solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.halemba.elements.Field;
import de.halemba.gui.SudokuGUI;
import de.halemba.helpers.Helper;

public class Solver implements ActionListener {
	
	SudokuGUI gui;
	JButton start;
	Field[][] grid;
	
	public Solver() {
		
		gui = new SudokuGUI(this);
		start = gui.getStartButton();
		grid = new Field[9][9];
		initFields();
	}

	private void initFields() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				grid[i][j] = new Field(Helper.getQuadrant(i, j));
				
				if(gui.getFieldValue(i, j) > 0){
					grid[i][j].setNumber(gui.getFieldValue(i, j));
					grid[i][j].setFix(true);
				}
			}
		}
	}
	
	private void updatePossibles() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				if(grid[i][j].getFix()) {
				} else {
					for(int k=1; k<=9; k++) {
						if(checkRow(i,j,k)) {
							if(checkCol(i,j,k)) {
								if(checkQuad(i,j,k)) {
									
								} else {
									grid[i][j].delPossibleNumber(k);
								}
							} else {
								grid[i][j].delPossibleNumber(k);
							}
						} else {
							grid[i][j].delPossibleNumber(k);
						}
					}
				}
			}
		}
	}
	
	private boolean checkRow(int i, int j, int k) {
		for(int l=0; l<9; l++) {
			if(grid[i][l].getNumber() == k && l!=j)
				return false;
		}
		return true;
	}
	
	private boolean checkCol(int i, int j, int k) {
		for(int l=0; l<9; l++) {
			if(grid[l][j].getNumber() == k && l!=i)
				return false;
		}
		return true;
	}
	
	private boolean checkQuad(int i, int j, int k) {
		for(int l=0; l<9; l++) {
			for(int m=0; m<9; m++) {
				if(l!=i && m!=j) {
					if(grid[l][m].getQuadrant() == grid[i][j].getQuadrant() && grid[l][m].getNumber() == k) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() ==  gui.getStartButton()) {
			System.out.println("Start");
		} else if (e.getSource() == gui.getPosButton()) {
			updatePossibles();
			gui.updateFields(grid);
		}
	}

}
