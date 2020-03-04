//Please note that we have used the following link as a reference to complete part 1 of the project. 
//Link: https://www.geeksforgeeks.org/producer-consumer-solution-using-threads-java/

import java.util.LinkedList;

public class part1 {

	public static void main(String[] args) throws InterruptedException {
		
		//Create an object of programCounter that has the required methods for both consume and produce. 
		final ProgramCounter programCounter = new ProgramCounter();
		
		//Producer thread is thread1. 
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				try {programCounter.producer();} catch (InterruptedException ie) {ie.printStackTrace();}
			}});
		
		//Consumer thread is thread2. 
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				try {programCounter.consumer();} catch (InterruptedException ie) {ie.printStackTrace();}
			}});
		
		//Start both of the thread.s
		thread1.start();
		thread2.start();
		
		//Thread1 will complete execution before Thread2. 
		thread1.join();
		thread2.join();
		
	}
	
}

class ProgramCounter {

	//Initiate list to be shared by both producers and consumers. (of sizeCapacity 2).
	LinkedList<Integer> listPC = new LinkedList<>();
	int totalCapacity = 2; 
	
	//Producer thread utilizes this method.
	public void producer() throws InterruptedException {
		
		int value = 0; 
		
		while (true) {
			
			synchronized (this) {
				
				//Producer thread must wait for list to be empty. 
				while (listPC.size() == totalCapacity){wait();}
				
				//Output producing information.
				System.out.println("Producer produced-" + value);
				
				//Insert the jobs into the shared list. 
				listPC.add(value++);
				
				//Notify the consumer thread  to allow it to proceed. 
				notify();
				
				//Sleep timer for the thread. 
				Thread.sleep(1000);	
			}	
		}
	}
	
	//Consumer thread utilizes this method. 
	public void consumer() throws InterruptedException {
		while (true) {
			synchronized (this) {
				//Consumer thread must as long as the list remains empty. 
				while (listPC.size() == 0) {wait();}
				
				//Fetch the first job within the job list. 
				int value = listPC.remove(0);
				
				//Output consuming information.
				System.out.println("Consumer consumed-" + value);
				
				//Allow the producer thread to be woken up. 
				notify();
				
				//Sleep timer for the thread.
				Thread.sleep(1000);
			}
		}
	}	
}
