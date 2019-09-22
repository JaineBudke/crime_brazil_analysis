package jmhTestes;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Warmup;

public class BenchDataTestUp {

	@Benchmark
	@Warmup(iterations = 2)
	public void init() {
	
		String[] line = new String[3];
		line[0] = "Branca";
		line[1] = "Tarde";
		line[2] = "M";
		
		String cor = "BRANCA";
		String turno = "Tarde";
		String sexo = "F";
		
		
		 
		// verifica se é o mes passado pelo usuario		
		String corLine = line[0];
		
		// verifica se a cor corrente é uma das possíveis
		if( corLine.equalsIgnoreCase("PRETA")   || 
			corLine.equalsIgnoreCase("BRANCA")  || 
			corLine.equalsIgnoreCase("AMARELA") || 
			corLine.equalsIgnoreCase("PARDA")   ){
			
			if( corLine.equalsIgnoreCase(cor) ){
				//bayes.incrementCor();
			}		
		
		// verifica se for é nula
		} else if( corLine.equalsIgnoreCase("NULL") ){
			//bayes.incrementCoresNulas();
		} else if( cor.equalsIgnoreCase("Outras") ){
			//bayes.incrementCor();
		}
		
	
		// verifica se é a hora passada pelo usuario
		if( !line[1].equalsIgnoreCase("NULL")){					
		
			String turnoLine = line[1];
			
			if( turnoLine.equalsIgnoreCase(turno) ){
				//bayes.incrementTurno();
			}
			
		} else {
			//bayes.incrementTurnoNulo();
		}
			
		
		// verifica se é o sexo passada pelo usuario
		if( !line[2].equalsIgnoreCase("NULL") ) {
			String sexoLine = line[2];
			if(sexoLine.equalsIgnoreCase(sexo)) {
				//bayes.incrementSexo();
			} 	
		} else {
			//bayes.incrementSexoNulo();
		}
		
	
	}
	
}
