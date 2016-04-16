package assignment6;

public class Theater {
	boolean configuration[][];

	public boolean[][] getConfiguration() {
		return configuration;
	}

	public void setConfiguration(boolean[][] configuration) {
		this.configuration = configuration;
	}

	public Theater() {
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
	
}
