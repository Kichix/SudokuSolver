package de.halemba.elements;

public class Field {
	
	private int[] possibleNumbers;
	private int currentNumber;
	private boolean fixed;
	private int quadrant;
	private int possibleCount;
	
	public Field(int q) {
		
		//Init starting values
		currentNumber = 0;
		fixed = false;
		quadrant = q;
		possibleCount = 9;
		
		//Initialize possible numbers array
		possibleNumbers = new int[9];
		
		//Fill it up
		for(int i=1; i < 10; i++) {
			possibleNumbers[i-1] = i;
		}
		
	}
	
	public void setNumber(int i) {
		this.currentNumber = i;
	}
	
	public int getNumber() {
		return this.currentNumber;
	}
	
	public void setFix(boolean b) {
		this.fixed = b;
	}
	
	public boolean getFix() {
		return this.fixed;
	}
	
	public void delPossibleNumber(int i) {
		
		for(int j = 0; j <= possibleNumbers.length; j++) {
			if(possibleNumbers[j] == i) {
				possibleNumbers[j] = 0;
			}
		}
		
		updateCount();
	}
	
	public void addPossibleNumber(int i) {
		
		this.possibleNumbers[i-1] = i;
		updateCount();
	}
	
	public int getQuadrant() {
		return this.quadrant;
	}
	
	public void updateCount() {
		
		int x = 0;
		int y = 0;
		
		for(int i = 0; i<possibleNumbers.length; i++) {
			if (possibleNumbers[i] != 0) {
				x+=1;
			} else {
				y = possibleNumbers[i];
			}
		}
		
		if(x>1) {
		this.possibleCount = x;
		} else if(x==1) {
			currentNumber = y;
			possibleCount = 1;
		}
	}

}