package jmhTestes;

import org.openjdk.jmh.annotations.Benchmark;

public class BenchDataTest {

	@Benchmark
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
		if( corLine.equals("PRETA")   || 
			corLine.equals("BRANCA")  || 
			corLine.equals("AMARELA") || 
			corLine.equals("PARDA")   ){
			
			if( corLine.equals(cor.trim().toUpperCase()) ){
				//bayes.incrementCor();
			}		
		
		// verifica se for é nula
		} else if( corLine.trim().toUpperCase().equals("NULL") ){
			//bayes.incrementCoresNulas();
		} else {
			if( cor.equals("Outras") ){
				//bayes.incrementCor();
			}	
		}
		
	
		// verifica se é a hora passada pelo usuario
		if( !line[1].trim().toUpperCase().equals("NULL")){					
		
			String turnoLine = line[1];
			
			if( turnoLine.equals(turno) ){
				//bayes.incrementTurno();
			}
	
		} else {
			//bayes.incrementTurnoNulo();
		}
			
		
		// verifica se é o sexo passada pelo usuario
		if( !line[2].trim().toUpperCase().equals("NULL") ) {
			String sexoLine = line[2];
			if(sexoLine.equals(sexo)) {
				//bayes.incrementSexo();
			} 	
		} else {
			//bayes.incrementSexoNulo();
		}
		
	
	}
	
}
