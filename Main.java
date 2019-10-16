package crime_analysis;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;


public class Main {

	public static void main(String[] args) {
		
		SubmissionPublisher<Parameters> publisher = new SubmissionPublisher<Parameters>();
		
		for (int i = 0; i < 5; i++) {
			
			Consumer consumer = new Consumer("Consumer "+i);
			publisher.subscribe(consumer); }
			DataT dataT = new DataT(publisher);
			new Thread(dataT).start();
			
			do {
				
				System.out.println("Publisher: MaximunLag:"+ publisher.estimateMaximumLag());
				System.out.println("Publisher: Max Buffer Capacity: "+
				publisher.getMaxBufferCapacity());
				try { TimeUnit.SECONDS.sleep(10); }
				catch (InterruptedException e) { e.printStackTrace(); } 
				
			} while ((publisher.estimateMaximumLag() > 0));
		
	}

}
