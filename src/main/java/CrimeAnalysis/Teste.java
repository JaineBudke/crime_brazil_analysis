package CrimeAnalysis;

import java.io.IOException;


public class Teste {

	public static void main(String [] args) throws IOException {
		

		
		Process proc = new Process();
		

		
		
		
		
		long tempoInicial = System.currentTimeMillis();
		initialize(proc);
		
		
		System.out.println("o metodo executou em " + (System.currentTimeMillis() - tempoInicial));

		for( int x=0; x<20;x++ ){
			Features bayes = new Features();

			// dá entrada numa cidade, hora e mes
			String sexo = "F";
			String cor  = "BRANCA";
			String turno = "Tarde";
		dataProcess(bayes, proc, sexo, cor, turno);
		
		classifier(bayes, proc);
		
		proc.cleanCount();
		
		}
		
	}
	
	public static void initialize( Process archive ) {
		String line;
		
		// enquanto tiver linhas
		while( (line = archive.getLine()) != "" ) {
			
			// separa linha por virgula
			String[] parts = line.split(",");
					
			int tam = parts.length;
			
			// verifica se é furto
			String rubrica = (parts[16].split(" "))[0];

			// se for, pode analisar o restante dos casos
			if("\"Furto".equals(rubrica) || "Furto".equals(rubrica) ) {
								
				String[] memLine = new String[3]; 
	
				// verifica se é o mes passado pelo usuario		
				String corLine = parts[ tam-1 ].trim().toUpperCase();
				
				
				// verifica se a cor corrente é uma das possíveis
				if( corLine.equals("PRETA")   || 
					corLine.equals("BRANCA")  || 
					corLine.equals("AMARELA") || 
					corLine.equals("PARDA")   ){
					
					memLine[0] = corLine;							
					
				// verifica se cor é nula
				} else if( corLine.trim().toUpperCase().equals("NULL") ){
					memLine[0] = "NULL";
				} else {
					memLine[0] = "OUTRAS";	
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
					
					memLine[1] = turnoLine;

				} else {
					memLine[1] = "NULL";
				}
				
				
				// armazena sexo
				String sexoLine = parts[ tam-3 ];
				memLine[2] = sexoLine;		
					
				archive.putLine(memLine);

			}

			
		}

	}
	
	
	public static void dataProcess( Features bayes, Process archive, String sexo, String cor, String turno ) {
		
		String[] line;
		
		// enquanto tiver linhas
		while( (line = archive.getNext()) != null ) {  
			 
			// verifica se é o mes passado pelo usuario		
			String corLine = line[0];
			
			// verifica se a cor corrente é uma das possíveis
			if( corLine.equals("PRETA")   || 
				corLine.equals("BRANCA")  || 
				corLine.equals("AMARELA") || 
				corLine.equals("PARDA")   ){
				
				if( corLine.equals(cor.trim().toUpperCase()) ){
					bayes.incrementCor();
				}		
			
			// verifica se for é nula
			} else if( corLine.trim().toUpperCase().equals("NULL") ){
				bayes.incrementCoresNulas();
			} else {
				if( cor.equals("Outras") ){
					bayes.incrementCor();
				}	
			}
			

			// verifica se é a hora passada pelo usuario
			if( !line[1].trim().toUpperCase().equals("NULL")){					
			
				String turnoLine = line[1];
				
				if( turnoLine.equals(turno) ){
					bayes.incrementTurno();
				}

			} else {
				bayes.incrementTurnoNulo();
			}
				
			
			// verifica se é o sexo passada pelo usuario
			if( !line[2].trim().toUpperCase().equals("NULL") ) {
				String sexoLine = line[2];
				if(sexoLine.equals(sexo)) {
					bayes.incrementSexo();
				} 	
			} else {
				bayes.incrementSexoNulo();
			}
			
			
			
								
		}
	}
	
	public static void classifier( Features bayes, Process proc ){
		
		float qntCor   = bayes.getCor();
		float qntSexo  = bayes.getSexo();
		float qntTurno = bayes.getTurno();
		float qntFurtos = proc.getQuantFurtos();
		float coresNulas = bayes.getCoresNulas();
		float turnoNulo = bayes.getTurnoNulo();
		float sexoNulo = bayes.getSexoNulo();
		
		System.out.println(qntCor);
		System.out.println(qntSexo);
		System.out.println(qntTurno);
		System.out.println(qntFurtos);
		System.out.println(coresNulas);
		System.out.println(turnoNulo);
		System.out.println(sexoNulo);
		
		
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
