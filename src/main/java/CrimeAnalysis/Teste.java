package CrimeAnalysis;

import java.io.IOException;


public class Teste {

	public static void main(String [] args) throws IOException {
		
		Process proc = new Process();
		NaiveBayes bayes = new NaiveBayes();
		
		
		// dá entrada numa cidade, hora e mes
		String sexo = "F";
		String cor  = "Amarela";
		String turno = "Tarde";
		
		
		String line;
	
		// enquanto tiver linhas
		while( (line = proc.getLine()) != "" ) {
			
			// separa linha por virgula
			String[] parts = line.split(",");

			// verifica se é furto
			String rubrica = (parts[16].split(" "))[0];

			int tam = parts.length;
			
			// se for, pode analisar o restante dos casos
			if("\"Furto".equals(rubrica) || "Furto".equals(rubrica) ) {
								
				
				// verifica se é o mes passado pelo usuario		
				String corLine = parts[ tam-1 ];
				
				corLine = corLine.trim().toUpperCase();
				
				System.out.println(corLine);
				if( corLine.equals("PRETA")   || 
					corLine.equals("BRANCA")  || 
					corLine.equals("AMARELA") || 
					corLine.equals("PARDA")   ){
					
					if( corLine.equals(cor.trim().toUpperCase()) ){
						bayes.incrementCor();
					}		
						
				} else if( corLine.trim().toUpperCase().equals("NULL") ){
					bayes.incrementCoresNulas();
				}
				else {
					
					if( cor.equals("Outras") ){
						bayes.incrementCor();
					}	
				}
					

				// verifica se é a hora passada pelo usuario
				if( !parts[13].equals("NULL")){					
					
					int hourLine = 0;
					if( parts[13].split(":").length > 1 ){
						hourLine = Integer.parseInt((parts[13].split(":")[0]));
					} else if( parts[13].split("H").length > 1 ){
						hourLine = Integer.parseInt((parts[13].split("H")[0]));
					}
						
					
					String turnoLine = "";
					
					// classifica turno em manha, tarde, noite, madrugada
					if( hourLine >= 0 && hourLine < 6 ){
						turnoLine = "Madrugada";
					} else if( hourLine >= 6 && hourLine < 12 ){
						turnoLine = "Manha";
					} else if( hourLine >= 12 && hourLine < 18 ){
						turnoLine = "Tarde";
					} else {
						turnoLine = "Noite";
					}
					
					if( turnoLine.equals(turno) ){
						bayes.incrementTurno();
					}

				}
				
				
				// verifica se é a cidade passada pelo usuario
				String sexoLine = parts[ tam-3 ];
				if(sexoLine.equals(sexo)) {
					bayes.incrementSexo();
				}		
								
			}
			
		}
		
		classifier(bayes, proc );
		
	}
	
	public static void classifier( NaiveBayes bayes, Process proc ){
		
		float qntCor   = bayes.getCor();
		float qntSexo  = bayes.getSexo();
		float qntTurno = bayes.getTurno();
		float qntFurtos = proc.getQuantFurtos();
		float coresNulas = bayes.getCoresNulas();
		float turnoNulo = bayes.getTurnoNulo();
		float sexoNulo = bayes.getSexoNulo();
		
		
		// Classificações: Seguro (0), Pouco seguro (1), Inseguro (2)
		int sexoClassify;
		int corClassify;
		int turnoClassify;
		
		
		// (Parametro sexo) Inseguro( p >= 0.65 ), Pouco Seguro( 0.35 < p < 0.65 ) e Seguro ( p <= 0.35)	
		if( (qntSexo/(qntFurtos-sexoNulo)) >= 0.65  ){
			sexoClassify = 2;
		} else if( (qntSexo/(qntFurtos-sexoNulo)) <= 0.35 ){
			sexoClassify = 0;
		} else {
			sexoClassify = 1;
		}
		
		
		// (Parametro cor) Inseguro( p >= 0.4 ), Pouco Seguro ( 0.1 < p < 0.4 ) e Seguro( p <= 0.1 )
		if( (qntCor/(qntFurtos-coresNulas)) >= 0.65  ){
			corClassify = 2;
		} else if( (qntCor/(qntFurtos-coresNulas)) <= 0.35 ){
			corClassify = 0;
		} else {
			corClassify = 1;
		}	
		
		// (Parametro turno) Inseguro( p >= 0.6 ), Pouco Seguro( 0.15 < p < 0.6 ) e Seguro( p <= 0.15 )
		if( (qntTurno/(qntFurtos-turnoNulo)) >= 0.6  ){
			turnoClassify = 2;
		} else if( (qntTurno/(qntFurtos-turnoNulo)) <= 0.15 ){
			turnoClassify = 0;
		} else {
			turnoClassify = 1;
		}	
		
		
		// soma >= 4: inseguro; soma <= 2: seguro; caso contrario: pouco seguro 
		if( (sexoClassify + corClassify + turnoClassify) >= 4 ){
			System.out.println("INSEGURO");
		} else if( (sexoClassify + corClassify + turnoClassify) <= 2 ) {
			System.out.println("SEGURO");
		} else {
			System.out.println("POUCO SEGURO");
		}
			
	}
	
}
