package CrimeAnalysis;

import java.io.IOException;
import java.util.Scanner;

public class Main {


	private static Scanner scan;


	public static void main(String [] args) throws IOException {
				
		Process proc = new Process();		
		
		int qntThreads = 4;
		
		// processamento dos dados diretamentos dos datasets
		ArchiveThread arc = new ArchiveThread(proc);
		arc.initialize();
		
		int p1 = 0;
		
		
		scan = new Scanner(System.in);
		System.out.println("0: encerrar programa; 1: fazer predição");
		p1 = scan.nextInt();
		
		while( p1 != 0 ) {
						
			// entradas do usuario
			String sexo = "";
			String cor  = "";
			String turno = "";
			
			
			System.out.println("Sexo: (0) Feminino; (1) Masculino ");
			int p2 = scan.nextInt();
			
			if( p2 == 0 ){
				sexo = "F";
			} else {
				sexo = "M";
			}
			
			System.out.println("Turno: (0) Manhã; (1) Tarde; (2) Noite; (3) Madrugada");
			int p3 = scan.nextInt();
			
			if(p3 == 0 ){
				turno = "Manha";
			} else if( p3 == 1 ) {
				turno = "Tarde";
			} else if( p3 == 2 ) {
				turno = "Noite";
			} else {
				turno = "Madrugada";
			}
			
			
			System.out.println("Cor: (0) Preta; (1) Branca; (2) Parda; (3) Amarela; (4) Outras ");
			int p4 = scan.nextInt();
		
			if( p4 == 0 ) {
				cor = "PRETA";
			} else if( p4 == 1 ) {
				cor = "BRANCA";
			} else if( p4 == 2 ) {
				cor = "PARDA";
			} else if( p4 == 3 ) {
				cor = "AMARELA";
			} else {
				cor = "OUTRAS";
			}

		
			String result = makeAnalysis( qntThreads, proc, cor, sexo, turno );

			System.out.println(result);

			
			System.out.println("0: encerrar programa; 1: fazer predição");
			p1 = scan.nextInt();
			
		}
			
	} 
		

	/**
	 * Análise com dados na memória e com base nas entradas do usuário
	 * @param proc Instancia de Process
	 * @param cor Variável cor dada como entrada pelo usuário
	 * @param sexo Variável sexo dada como entrada pelo usuário
	 * @param turno Variável turno dada como entrada pelo usuário
	 * @return resultado da classificação 
	 */
	public static String makeAnalysis( int qntThreads, Process proc, String cor, String sexo, String turno ){
		

		// instancia da classe Features
		Features bayes = new Features();

		DataThread[] thrs = new DataThread[qntThreads]; 
		
		// cria e inicializa threads
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
		
		
		// classifica dados
		String classif = classifier(bayes, proc);
		
		clear( proc );
		
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
	public static String classifier( Features bayes, Process proc ){
		
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
