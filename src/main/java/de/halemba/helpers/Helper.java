package de.halemba.helpers;

public final class Helper {
	
	public static final boolean debug = false;

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
}
