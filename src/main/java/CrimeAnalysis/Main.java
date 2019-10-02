package CrimeAnalysis;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;



public class Main {


	private static Scanner scan;


	public static void main(String [] args) throws IOException {
				
		Process proc = new Process();		
		
		Features bayes = new Features();
		// processamento dos dados diretamentos dos datasets
		ArchiveThread arc = new ArchiveThread(bayes,proc);
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

			/*Classifier classif = new Classifier();
			String result = classif.makeAnalysis( proc, cor, sexo, turno );
			
			System.out.println(result);
			*/
			
			
			//List<String[]> data = proc.getList();
			
			/*System.out.println(data.size());
			
			ForkJoinPool pool = new ForkJoinPool();
			
			Features bayes = new Features();
			
			ClassifierFork task = new ClassifierFork(data, bayes, sexo, cor, turno);
			pool.invoke(task);
			
			// classifica dados
			Classifier classif = new Classifier();
			String dC = classif.dataClassifier(bayes, proc);
			
			System.out.println(dC);
			*/
			
			List<String> dataC = proc.getListC();
			List<String> dataS = proc.getListS();
			List<String> dataT = proc.getListT();
			
			ClassifierStreams classif = new ClassifierStreams(  );
			
			List<String> newDataC = classif.streamsCompute(dataC, cor);
			List<String> newDataS = classif.streamsCompute(dataS, sexo);
			List<String> newDataT = classif.streamsCompute(dataT, turno);
			
			int qntFurtos = bayes.getFurtos();
			int qntSexo   = newDataS.size();
			int qntTurno  = newDataT.size();
			int qntCor    = newDataC.size();
			
			int coresNulas = bayes.getCoresNulas();
			int turnoNulo  = bayes.getTurnoNulo();
			int sexoNulo  = bayes.getSexoNulo();
			
			System.out.println(qntFurtos);
			System.out.println(qntSexo);
			System.out.println(qntTurno);
			System.out.println(qntCor);
			System.out.println(coresNulas);
			System.out.println(turnoNulo);
			System.out.println(sexoNulo);
			
			
			
			String result = classif.dataClassifier( qntFurtos, qntSexo, qntTurno, qntCor, coresNulas, turnoNulo, sexoNulo );
			
			System.out.println(result);
			
			System.out.println("0: encerrar programa; 1: fazer predição");
			p1 = scan.nextInt();
			
		}
			
	} 
		
	
}
