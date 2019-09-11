package CrimeAnalysis;

public class ArchiveThread extends Thread {

	// instancia do processador do dataset
	private Process archive;
	
	// construtor
	public ArchiveThread( Process archive ){
		this.archive = archive;
	}
	
	
	public void run(){
		
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
				String corLine = parts[ tam-1 ].trim();
				
				
				// verifica se a cor corrente é uma das possíveis
				if( corLine.equalsIgnoreCase("PRETA")   || 
					corLine.equalsIgnoreCase("BRANCA")  || 
					corLine.equalsIgnoreCase("AMARELA") || 
					corLine.equalsIgnoreCase("PARDA")   ){
					
					memLine[0] = corLine;							
					
				// verifica se cor é nula
				} else if( corLine.equalsIgnoreCase("NULL") ){
					memLine[0] = "NULL";
				} else {
					memLine[0] = "OUTRAS";	
				}
					

				// verifica se é a hora passada pelo usuario
				if( !parts[13].equals("NULL")){					
					
					//int hourLine = Integer.parseInt(parts[13].substring(0, 1));
					
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
			
	
}
