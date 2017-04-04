package de.halemba.solver;

import de.halemba.elements.Field;

public class BruteForcer {

	Field[][] grid;
	boolean solved;
	
	public BruteForcer() {
		this.grid = new Field[9][9];
		this.solved = false;
	}
	
	public int[] getNextField(int x, int y) {
		
		int[] pos = new int[2];
		pos[0] = -1;
		pos[1] = -1;
		
		for(int i=x; i<9; i++) {
			for(int j=y; j<9; j++) {
				if(!grid[i][j].getFix()) {
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}	
		return pos;
	}
	
	public int[] getLastField(int x, int y) {
		
		int[] pos = new int[2];
		pos[0] = -1;
		pos[1] = -1;
		
		for(int i=x; i>0; i--) {
			for(int j=y; j>0; j--) {
				if(!grid[i][j].getFix()) {
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}	
		return pos;
	}
	
	public void force(int x, int y) {
		
		 
	}
	
	
}
  