package CrimeAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ClassifierFork extends RecursiveAction {

	private static final int SEQUENTIAL_THRESHOLD = 10000;
	private List<String[]> data;
	
	
	// instancia com os parametros analisados
	private Features bayes;
	
	// parametros dados pelo usuario 
	private String sexo;
	private String cor;
	private String turno;
	
	
	public ClassifierFork(List<String[]> list, Features bayes, String sexo, String cor, String turno) { 
		this.data = list; 
		this.sexo = sexo;
		this.cor  = cor;
		this.turno = turno;
		this.bayes = bayes;
		
	}
	
	
	@Override
	protected void compute() {

		if (data.size() <= SEQUENTIAL_THRESHOLD) { // base case
			
			Integer[] sums = computeSumDirectly();
			
			// computo resultado em Features
			bayes.incrementCor(sums[0]);
			bayes.incrementSexo(sums[1]);
			bayes.incrementTurno(sums[2]);
			
			bayes.incrementCoresNulas(sums[3]);
			bayes.incrementSexoNulo(sums[4]);
			bayes.incrementTurnoNulo(sums[5]);
			
			
			
		} else { // recursive case
			
			// Calculate new range
			int mid = data.size() / 2;
			ClassifierFork firstSubtask = new ClassifierFork(data.subList(0, mid), bayes, sexo, cor, turno);
			ClassifierFork secondSubtask = new ClassifierFork(data.subList(mid, data.size()), bayes, sexo, cor, turno);
			firstSubtask.fork(); // queue the first task
			secondSubtask.compute(); // compute the second task
			firstSubtask.join(); // wait for the first task result
			// invokeAll(firstSubtask, secondSubtask);
		} 
		
	}
	
	private Integer[] computeSumDirectly() {
		

		Integer sums[] = new Integer[6];
		sums[0] = 0; // cor
		sums[1] = 0; // sexo
		sums[2] = 0; // turno 
		
		sums[3] = 0; // cor nula
		sums[4] = 0; // sexo nulo
		sums[5] = 0; // turno nulo
		
		
		for( String[] line : data ){
			
			// verifica se é o mes passado pelo usuario		
			String corLine = line[0];
			
			// verifica se a cor corrente é uma das possíveis
			if( corLine.equalsIgnoreCase("PRETA")   || 
				corLine.equalsIgnoreCase("BRANCA")  || 
				corLine.equalsIgnoreCase("AMARELA") || 
				corLine.equalsIgnoreCase("PARDA")   ){
				
				if( corLine.equalsIgnoreCase(cor) ){
					//bayes.incrementCor();
					sums[0] += 1;
				}		
			
			// verifica se for é nula
			} else if( corLine.equalsIgnoreCase("NULL") ){
				//bayes.incrementCoresNulas();
				sums[3] += 1;
			} else if( cor.equalsIgnoreCase("Outras") ){
				sums[0] += 1;
				//bayes.incrementCor();
			}
			
		
			// verifica se é a hora passada pelo usuario
			if( !line[1].equalsIgnoreCase("NULL")){					
			
				String turnoLine = line[1];
				
				if( turnoLine.equalsIgnoreCase(turno) ){
					//bayes.incrementTurno();
					sums[2] += 1;
				}
				
			} else {
				//bayes.incrementTurnoNulo();
				sums[5] += 1;
			}
				
			
			// verifica se é o sexo passado pelo usuario
			if( !line[2].equalsIgnoreCase("NULL") ) {
				String sexoLine = line[2];
				if(sexoLine.equalsIgnoreCase(sexo)) {
					//bayes.incrementSexo();
					sums[1] += 1;
				} 	
			} else {
				//bayes.incrementSexoNulo();
				sums[4] += 1;
			}
			
			
		}
		
		// para cada elemento da lista, vou rodar o codigo DataThread
		// faço a analise pra essa parte do vetor, vou computando a soma e quando acabar é que subo tudo pra Features
		
		
		return sums; 
		
	}
	

	
	
	
	
	
	
}