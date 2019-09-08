package tmjee.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.LongResult2;

import CrimeAnalysis.NaiveBayes;


public class StressTest {

	@State
	public static class MyState extends NaiveBayes{}
	
	@JCStressTest
	@Description("Teste da variavel 'cor' da classe NaiveBayes")
	@Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[1, 0]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest1 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementCor();
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementCor();
		}
				
	} 
	

	@JCStressTest
	@Description("Teste da variavel 'sexo' da classe NaiveBayes")
	@Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[1, 0]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest2 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementSexo();
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementSexo();
		}
				
	} 
	
	@JCStressTest
	@Description("Teste da variavel 'turno' da classe NaiveBayes")
	@Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[1, 0]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest3 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementTurno();
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementTurno();
		}
				
	} 
	
	@JCStressTest
	@Description("Teste da variavel 'turno nulo' da classe NaiveBayes")
	@Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[1, 0]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest4 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementTurnoNulo();
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementTurnoNulo();
		}
				
	} 
	
	@JCStressTest
	@Description("Teste da variavel 'sexo nulo' da classe NaiveBayes")
	@Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[1, 0]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest5 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementSexoNulo();
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementSexoNulo();
		}
				
	} 
	
	@JCStressTest
	@Description("Teste da variavel 'cor nula' da classe NaiveBayes")
	@Outcome(id="[0, 1]", expect = Expect.ACCEPTABLE, desc = "get back 0-1")
	@Outcome(id="[1, 0]", expect = Expect.ACCEPTABLE, desc = "get back 1-0")
	public static class StressTest6 { 
		
		@Actor
		public void actor1(MyState myState, LongResult2 r) {
			r.r1 = myState.incrementCoresNulas();
		}
		
		@Actor
		public void actor2(MyState myState, LongResult2 r) {
			r.r2 = myState.incrementCoresNulas();
		}
				
	} 
	
}