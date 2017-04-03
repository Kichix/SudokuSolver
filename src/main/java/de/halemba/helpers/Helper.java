package de.halemba.helpers;

import de.halemba.elements.Field;

public final class Helper {
	
	public static final boolean debug = false;

	//Delivers the quadrant in which the field is in 
	public static int getQuadrant(int i, int j) {
		//Decide to which quadrant the field belongs to
		if(i < 3 && j < 3) {
			return 0;
		} else if(i < 3 && j>= 3 && j<6) {
			return 1;
		} else if(i < 3 && j>=6){
			return 2;
		} else if(i >= 3 && i <6 && j <3) {
			return 3;
		} else if(i >= 3 && i <6 && j >= 3 && j<6) {
			return 4;
		} else if(i >= 3 && i <6 && j>=6) {
			return 5;
		} else if(i >= 6 && j < 3) {
			return 6;
		} else if(i >= 6 && j >= 3 & j < 6) {
			return 7;
		} else if(i >= 6 && j >= 6) {
			return 8;
		} else return -1;
	}
	
	//Checks if a nunmber is still free in the row
	public static boolean checkRow(int i, int j, int k, Field[][] grid) {
		for(int l=0; l<9; l++) {
			if(grid[i][l].getNumber() == k && l!=j)
				return false;
		}
		return true;
	}
	
	//Checks if a number is still free in the column
	public static boolean checkCol(int i, int j, int k, Field[][] grid) {
		for(int l=0; l<9; l++) {
			if(grid[l][j].getNumber() == k && l!=i)
				return false;
		}
		return true;
	}
	
	//Checks if a number is still free in the quadrant
	public static boolean checkQuad(int i, int j, int k, Field[][] grid) {
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
	
	//Checks if there is only one possible Field for a number(i) in a row(j)
	public static int checkSinglePossiblityRow(int i, int j, Field[][] grid) {
		
		int count = 0;
		int field = -1;
		int[] possibles;
		
		//Column
		for(int k=0; k<9; k++) {
			if(!grid[j][k].getFix()) {
				possibles = grid[j][k].getPossbileNumbers();
				
				if(possibles[i-1] == i) {
					count += 1;
					field = k;
				}
			}
		}
		
		if(count > 1) {
			field = -1;
		}
		
		if(i==7 && j==0) {
			System.out.println(field);
		}
		return field;
	}
	
	//Checks if a number is still missing in a row
	public static boolean checkMissingInRow(int i, int j, Field[][] grid) {
			
		for(int k=0; k<9; k++) {
			if(grid[j][k].getNumber() == i) {
					return false;
				} 
		}
		return true;
	}
}
