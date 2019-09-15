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
			if( corLine.equalsIgnoreCase("PRETA")   || 
				corLine.equalsIgnoreCase("BRANCA")  || 
				corLine.equalsIgnoreCase("AMARELA") || 
				corLine.equalsIgnoreCase("PARDA")   ){
				
				if( corLine.equalsIgnoreCase(cor) ){
					bayes.incrementCor();
				}		
			
			// verifica se for é nula
			} else if( corLine.equalsIgnoreCase("NULL") ){
				bayes.incrementCoresNulas();
			} else if( cor.equalsIgnoreCase("Outras") ){
				bayes.incrementCor();
			}
			
		
			// verifica se é a hora passada pelo usuario
			if( !line[1].equalsIgnoreCase("NULL")){					
			
				String turnoLine = line[1];
				
				if( turnoLine.equalsIgnoreCase(turno) ){
					bayes.incrementTurno();
				}
				
			} else {
				bayes.incrementTurnoNulo();
			}
				
			
			// verifica se é o sexo passado pelo usuario
			if( !line[2].equalsIgnoreCase("NULL") ) {
				String sexoLine = line[2];
				if(sexoLine.equalsIgnoreCase(sexo)) {
					bayes.incrementSexo();
				} 	
			} else {
				bayes.incrementSexoNulo();
			}
			
			
			
			
								
		}

	}
			
	
}
