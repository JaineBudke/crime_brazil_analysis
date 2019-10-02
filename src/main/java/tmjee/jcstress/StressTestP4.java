package tmjee.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult2;

import CrimeAnalysis.Features;


public class StressTestP4 {

	
	@State
	public static class MyState extends Features{}
	
		@JCStressTest
	@Description("Teste da variavel 'turno nulo' da classe Features")
	@Outcome(id="[1, 2]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[2, 1]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest4 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementTurnoNulo(1);
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementTurnoNulo(1);
		}
				
	}
	
		
}