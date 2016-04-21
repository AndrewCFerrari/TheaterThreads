package assignment6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Theater {
	boolean configuration[][]; //variable showing available seats
	boolean configurationLocked;
	
	private Lock changeLock;
	 
	
	
	public boolean[][] getConfiguration() {
		return configuration;
	}

	public void setConfiguration(boolean[][] configuration) {
		this.configuration = configuration;
	}
	
	public boolean getConfigurationLocked() {
		return configurationLocked;
	}

	public void setConfigurationLocked(boolean configurationLocked) {
		this.configurationLocked = configurationLocked;
	}

	public Theater() {
		changeLock = new ReentrantLock();
		this.setConfigurationLocked(false);
		boolean[][] emptyTheater = new boolean[26][28];
		for (int i=0;i<26;i++){
			for (int j=0;j<28;j++){
				emptyTheater[i][j] = false;
			}	
		}
		this.setConfiguration(emptyTheater);
	}

	public int[] bestAvailiableSeat(){
		boolean config[][] = this.getConfiguration();
		int[] seat = new int[2]; 
		for (seat[0]=0;seat[0]<26;seat[0]++){
			//center (seats 7-20)
			for ( seat[1]=7;seat[1]<21;seat[1]++){
				if (config[seat[0]][seat[1]]==false) return seat;
			}
			//left (seats 0-6)
			for ( seat[1]=0;seat[1]<7;seat[1]++){
				if (config[seat[0]][seat[1]]==false) return seat;
			}
			//right (seats 21-27)
			for ( seat[1]=21;seat[1]<28;seat[1]++){
				if (config[seat[0]][seat[1]]==false) return seat;
			}			
		}
		//no open seats
		seat[0]= -1;
		seat[1]= -1;
		return seat;
	}
	
	//Sets the seat Row, Column, to the boolean taken. Respects lock
	//Returns 1 if the value changed, -1 if the value didn't change
	public int setSeat (int row,int column, boolean taken){
		changeLock.lock();
		 try
		 {
		this.setConfigurationLocked(true);
		boolean[][] currentSetup = this.getConfiguration();
		boolean oldVal = currentSetup[row][column];
		currentSetup[row][column]= taken;
		this.setConfiguration(currentSetup);
		this.setConfigurationLocked(false);
		if (oldVal == taken) return -1;
		return 1;
		 }
		 finally
		 { changeLock.unlock();}
	}
	
	public void printSeat (int row, int column){
		String colString =  Integer.toString(column);
		String rowString =  Character.toString((char)(row+65));
		System.out.println("Seat "+rowString+colString+" sold");
	}
	
}
