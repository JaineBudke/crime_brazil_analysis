package CrimeAnalysis;

public class DataThread extends Thread {

	// instancia do processador do dataset
	private Process archive;
	
	// instancia com os parametros analisados
	private Features bayes;
	
	// parametros dados pelo usuario 
	private String sexo;
	private String cor;
	private String turno;
	
	
	// construtor
	public DataThread(Features bayes, Process archive, String sexo, String cor, String turno){
		this.bayes = bayes;
		this.archive = archive;
		this.sexo = sexo;
		this.cor = cor;
		this.turno = turno;
	}
	

	public void run(){
		
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
			
	
}
