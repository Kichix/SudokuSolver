package de.halemba.solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.halemba.elements.Field;
import de.halemba.gui.SudokuGUI;
import de.halemba.helpers.SolveHelper;
import de.halemba.helpers.Validator;

public class Solver implements ActionListener {
	
	SudokuGUI gui;
	JButton start;
	Field[][] grid;
	Field[][] oldGrid;
	
	public Solver() {
		
		gui = new SudokuGUI(this);
		start = gui.getStartButton();
		grid = new Field[9][9];
		oldGrid = new Field[9][9];
		initFields();
		
		if(SolveHelper.debug) {
			debugValues();
			gui.updateFields(grid);
		}
	}
	
	//Sets some values for debugging purposes
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

	//Initializes the grid
	private void initFields() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				grid[i][j] = new Field(SolveHelper.getQuadrant(i, j));
			}
		}
	}
	
	//Updates the grid from the GUI
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
	
	//Updates the possible numbers for each field
	private void updatePossibles() {
		
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
				if(!grid[i][j].getFix()) {
					for(int k=1; k<=9; k++) {
						if(SolveHelper.checkRow(i,j,k,grid)) {
							if(SolveHelper.checkCol(i,j,k,grid)) {
								if(SolveHelper.checkQuad(i,j,k,grid)) {
									
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
	
	//Sets a number if it is needed in a row and there is only one possible field
	public void checkNeededInRow() {
		
		int n;
		
		//Rows
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(SolveHelper.checkMissingInRow(i, j, grid)) {
					n = SolveHelper.checkSinglePossiblityRow(i, j, grid);
					if(n>=0) {
						grid[j][n].setNumber(i);
						break;
					}
				}
			}
		}
	}
	
	//Sets a number if it is needed in a column and there is only one possible field
	public void checkNeededInColumn() {

		int n;
		
		//Columns
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(SolveHelper.checkMissingInColumn(i, j, grid)) {
					n = SolveHelper.checkSinglePossiblityColumn(i, j, grid);
					if(n>=0) {
						grid[n][j].setNumber(i);
						break;
					}
				}
			}
		}
	}
	
	//Sets a number if it is needed in a quadrant and there is only one possible field
	public void checkNeededInQuadrant() {
		
		int[] n;
		
		//Quadrants
		for(int j=0; j<9; j++) {
			//Numbers
			for(int i=1; i<10; i++) {
				if(SolveHelper.checkMissingInQuadrant(i, j, grid)) {
					n = SolveHelper.checkSinglePossiblityQuadrant(i, j, grid);
					if(n[0]>=0) {
						grid[n[0]][n[1]].setNumber(i);
						break;
					}
				}
			}
		}
	}
	
	//Executes all the "needed"-methods from above in a single call
	public void checkNeeded() {
		
		checkNeededInRow();
		checkNeededInColumn();
		checkNeededInQuadrant();
	}
	
	//Resets the Sudoku
	public void resetFields() {
		for(int i = 0; i<9; i++) {
			for(int j = 0; j<9; j++) {
			grid[i][j].reset();
			}
		}
		
		gui.updateFields(grid);
	}
	
	//Checks if something happened in the last iteration
	public boolean checkChange() {
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(grid[i][j] != oldGrid[i][j]) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	public void refreshOldgrid() {
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				oldGrid[i][j] = grid[i][j];
			}
		}
		
	}
	
	//Solves the Sudoku
	public void solve() {
		
		String error = "";
		boolean progress = true;
		int countPos, countNeed;
		
		updateFields();
		
		if(Validator.validate(grid)==0) {
			while(!SolveHelper.solved(grid) && progress) {
				
				refreshOldgrid();
				updatePossibles();
				checkNeeded();
				
				if(!checkChange()){
					progress=false;
					gui.setState("Es konnte keine Lösung gefunden werden");
				}
			}
			
			gui.updateFields(grid);
		} else {
			switch(Validator.validate(grid)) {
			case 1: error = "Zeile";
			case 2: error = "Spalte";
			case 3: error = "Kästchen";
			}
			gui.setState(String.format("Eingaben sind nicht valide, ein(e) %s enthält eine doppelte Zahl", error));
		}
	}
	
	//Actionlistener
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() ==  gui.getStartButton()) {
			solve();
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
