package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicketServer {
	static int PORT;
	
	public int getPort() {
		return PORT;
	}
	public void setPort(int port) {
		PORT = port;
	}
	
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		PORT = portNumber;
		int port2 = portNumber + 30;
		int port3 = portNumber + 50;
		Runnable serverThread = new ThreadedTicketServer(PORT);
		Runnable serverThread2 = new ThreadedTicketServer(port2);
		Runnable serverThread3 = new ThreadedTicketServer(port3);
		

		Thread t = new Thread(serverThread);
		Thread t2 = new Thread(serverThread2);
		Thread t3 = new Thread(serverThread3);
		
		t.setName("Office 1");
		t2.setName("Office 2");
		t3.setName("Office 3");

		t2.start();
		t.start();
		t3.start();
		
	}
}

class ThreadedTicketServer implements Runnable {

	public static Theater concertHall = new Theater();
	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;
	int port;
	
	
	public ThreadedTicketServer(int port) {
		super();
		this.port = port;
	}

	//returns 1 if a seat sold, -1 if sold out
	public int[] serverRun(){		int[] bestSeat;
			bestSeat = concertHall.bestAvailiableSeat();
			if(bestSeat[0]==-1) return bestSeat;
			concertHall.setSeat(bestSeat[0], bestSeat[1], true);
		//concertHall.printSeat(bestSeat[0], bestSeat[1]);
		return bestSeat;
	}

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			
			while (true){
				Socket clientSocket = serverSocket.accept(); 
				
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				
					int[] reservedSeat = serverRun();
					
					if (reservedSeat[0]<0){
						System.out.println("No Seats");
						return;
					}
					else{
						String colString =  Integer.toString(reservedSeat[1]);
						String rowString =  Character.toString((char)(reservedSeat[0]+65));
						out.println( Thread.currentThread().getName() + " sold seat "+rowString+colString);
					}
				
				clientSocket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		while(true){
			try{
				Socket socket = serverSocket.accept();
			}
		}
		*/
	}
}