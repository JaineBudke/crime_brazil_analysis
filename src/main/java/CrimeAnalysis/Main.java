package CrimeAnalysis;

import java.io.IOException;
import java.util.Scanner;

public class Main {


	public static void main(String [] args) throws IOException {
		
		Process proc = new Process();

		initialize( proc );
		
		if( proc.getQuantFurtos() > 0 ) {
			System.out.println("DEU CERTO!!");
		} else {
			System.exit(0);
		}
		
		
		int p1 = 0;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("0: encerrar programa; 1: fazer predição");
		p1 = scan.nextInt();
		
		
		while( p1 != 0 ) {
			
			NaiveBayes bayes = new NaiveBayes();
			
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
			
			
			// inicializa threads
			DataThread[] thrs = new DataThread[3]; 
			
			// cria e inicializa threads
			for( int i=0; i<3; i++ ){
				thrs[i] = new DataThread( bayes, proc, sexo, cor, turno );
				thrs[i].start();
			}
			
			// espera threads acabarem
			try {
				for( int i=0; i<3; i++ ) {
					thrs[i].join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			// classifica dados
			classifier(bayes, proc);
			
			clear( proc, bayes );
			
			System.out.println("0: encerrar programa; 1: fazer predição");
			p1 = scan.nextInt();
			
			
		} 
		

	}
	
	
	public static void initialize( Process proc ) {

		// inicializa threads
		ArchiveThread[] thrs = new ArchiveThread[3]; 
		
		// cria e inicializa threads
		for( int i=0; i<3; i++ ){
			thrs[i] = new ArchiveThread( proc );
			thrs[i].start();
		}
		
		// espera threads acabarem
		try {
			for( int i=0; i<3; i++ ) {
				thrs[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void clear( Process proc, NaiveBayes bayes ){
		
		proc.cleanCount();
		bayes.cleanVariables();
		
	}
	
	
	/**
	 * Classifica e imprime resultado (Seguro, Inseguro, Pouco Seguro) 
	 * @param bayes Classe com parametros para classificação
	 */
	public static void classifier( NaiveBayes bayes, Process proc ){
		
		// recupera valores dos parametros analisados
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
