package CrimeAnalysis;

import java.io.IOException;
import java.util.Scanner;



public class Main {


	private static Scanner scan;


	public static void main(String [] args) throws IOException {
				
		Process proc = new Process();		
		
		
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

			Classifier classif = new Classifier();
			String result = classif.makeAnalysis( proc, cor, sexo, turno );

			System.out.println(result);

			
			System.out.println("0: encerrar programa; 1: fazer predição");
			p1 = scan.nextInt();
			
		}
			
	} 
		
	
}
