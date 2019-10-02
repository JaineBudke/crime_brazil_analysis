package CrimeAnalysis;

import java.util.List;
import java.util.stream.Collectors;

public class ClassifierStreams {


	
	public List<String> streamsCompute( List<String> dataset, String userParameter ){
		
		List<String> newList = dataset.parallelStream()
				.filter( data -> data.equalsIgnoreCase(userParameter) )
				.collect(Collectors.toList());
		
		return newList;
		
	}
	
	
	
	/**
	 * Classifica resultado (Seguro, Inseguro, Pouco Seguro) 
	 * @param bayes Instancia de Features
	 * @param proc Instancia de Process
	 * @return resultado da classificação
	 */
	public String dataClassifier( int qntFurtos, int qntSexo, int qntTurno, int qntCor, int coresNulas, int turnoNulo, int sexoNulo ){
		
		
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