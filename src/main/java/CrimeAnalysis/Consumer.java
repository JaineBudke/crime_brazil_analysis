package CrimeAnalysis;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;


public class Consumer implements Subscriber<Parameters> {

	private String name;
	private Subscription subscription;
	
	
	public Consumer (String name) { 
		this.name = name; 
	}
	
	
	public void onSubscribe(Subscription subscription) {
		this.subscription=subscription;
		this.subscription.request(1);
		System.out.println("Subscription OK");
	}

	public void onNext(Parameters item) {
		// TODO Auto-generated method stub
	}

	public void onError(Throwable throwable) {
		throwable.printStackTrace();
	}
	

	public void onComplete() {
		System.out.println("No more events");
	}
	

	
}