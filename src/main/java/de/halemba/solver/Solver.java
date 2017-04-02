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
		
		if(Helper.debug) {
			debugValues();
			gui.updateFields(grid);
		}
	}
	
	public void debugValues() {
		
		int k = 1;
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				if(grid[i][j].getQuadrant() == 0) {
					grid[i][j].setNumber(k);
					k += 1;
				}
			}
		}
	}

	private void initFields() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				grid[i][j] = new Field(Helper.getQuadrant(i, j));
			}
		}
	}
	
	private void updateFields() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
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
				if(!grid[i][j].getFix()) {
					for(int k=1; k<=9; k++) {
						if(Helper.checkRow(i,j,k,grid)) {
							if(Helper.checkCol(i,j,k,grid)) {
								if(Helper.checkQuad(i,j,k,grid)) {
									
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
	
	public void checkNeededInRow() {
		
		int n;
		
		//Rows
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(Helper.checkMissingInRow(i, j, grid)) {
					n = Helper.checkSinglePossiblityRow(i, j, grid);
					if(n>=0) {
						grid[n][j].setNumber(i);
					}
				}
			}
		}
	}
	
	public void resetFields() {
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
			grid[i][j].reset();
			}
		}
		
		gui.updateFields(grid);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() ==  gui.getStartButton()) {
			checkNeededInRow();
			gui.updateFields(grid);
		} else if (e.getSource() == gui.getPosButton()) {
			updateFields();
			updatePossibles();
			for(int i = 0; i<9; i++) {
			gui.updateFields(grid);
			}
		} else if (e.getSource() == gui.getResetButton()) {
			resetFields();
		}
	}
}
