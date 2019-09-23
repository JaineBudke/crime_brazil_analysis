package CrimeAnalysis;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;



public class Classifier {

	private static final Executor exec = Executors.newFixedThreadPool(100); 

	
	/**
	 * Análise com dados na memória e com base nas entradas do usuário
	 * @param proc Instancia de Process
	 * @param cor Variável cor dada como entrada pelo usuário
	 * @param sexo Variável sexo dada como entrada pelo usuário
	 * @param turno Variável turno dada como entrada pelo usuário
	 * @return resultado da classificação 
	 */
	public String makeAnalysis( Process proc, String cor, String sexo, String turno ){
		
		int qntThreads = 4;

		// instancia da classe Features
		Features bayes = new Features();

		//DataThread[] thrs = new DataThread[qntThreads]; 
		
		Runnable data1 = new DataT( bayes, proc, sexo, cor, turno ); 
		Runnable data2 = new DataT( bayes, proc, sexo, cor, turno ); 
		Runnable data3 = new DataT( bayes, proc, sexo, cor, turno ); 
		Runnable data4 = new DataT( bayes, proc, sexo, cor, turno ); 
		
		exec.execute(data1);
		exec.execute(data2);
		exec.execute(data3);
		exec.execute(data4);
		
		
		int queued, active, notCompleted;
		do {
			
			queued = ((ThreadPoolExecutor) exec).getQueue().size();
			active = ((ThreadPoolExecutor) exec).getActiveCount();
			notCompleted = queued + active; // approximate
			
		}
		while( notCompleted != 0 );
		
		
		// classifica dados
		String classif = dataClassifier(bayes, proc);
		
		clear( proc );
		

		/*// cria e inicializa threads
		for( int i=0; i<qntThreads; i++ ){
			thrs[i] = new DataThread( bayes, proc, sexo, cor, turno );
			thrs[i].start();
		}
		
		// espera threads acabarem
		try {
			for( int i=0; i<qntThreads; i++ ) {
				thrs[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		return classif;
		
	}
	
	
	
	
	/**
	 * Limpa contador da classe Process
	 * @param proc Instancia de Process
	 */
	public static void clear( Process proc ){
		
		proc.clearCount();
		
	}
	
	
	/**
	 * Classifica resultado (Seguro, Inseguro, Pouco Seguro) 
	 * @param bayes Instancia de Features
	 * @param proc Instancia de Process
	 * @return resultado da classificação
	 */
	public String dataClassifier( Features bayes, Process proc ){
		
		// recupera valores dos parametros analisados
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
		
		
		// (Parametro sexo) Inseguro( p >= 0.65 ), Pouco Seguro( 0.35 < p < 0.65 ) e Seguro ( p <= 0.35 )	
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
		
		String classif = ""; 
		
		// soma >= 4: inseguro; soma <= 2: seguro; caso contrario: pouco seguro 
		if( (sexoClassify + corClassify + turnoClassify) >= 4 ){
			classif = "INSEGURO";
		} else if( (sexoClassify + corClassify + turnoClassify) <= 2 ) {
			classif = "SEGURO";
		} else {
			classif = "POUCO SEGURO";
		}
			
		return classif;
		
	}


}
