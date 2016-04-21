package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	int port;
	TicketClient sc;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname, int port) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
		this.port = port;
	}
	
	public void run() {
		System.out.flush();
		try {
			Random randomint = new Random();
			int random = randomint.nextInt(3);
			Socket echoSocket;
			if(random == 0){
				echoSocket = new Socket("127.0.0.1", TicketServer.PORT);
			}
			else if(random == 1){
				echoSocket = new Socket("127.0.0.1", (TicketServer.PORT + 30));
			}
			else{
				echoSocket = new Socket("127.0.0.1", (TicketServer.PORT + 50));
			}
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
						
			
			String result = in.readLine();	//Result waits until it has a line from server
			
			if(result.equals("No Seats")){
				System.out.println("No Seats Available");
			}
			else if(result != null){
				System.out.println(result + " to "+ this.hostname);
			}
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClient(String hostname, String threadname, int port) {
		tc = new ThreadedTicketClient(this, hostname, threadname, port);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name, 100);
	}

	TicketClient() {
		this("localhost", "unnamed client", 100);
	}

	void requestTicket() {
		// TODO thread.run()
		tc.run();
		//System.out.println(hostName + "," + threadName + " got one ticket");
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
