package jmhTestes;

import java.io.IOException;

import org.openjdk.jmh.annotations.Benchmark;

public class BenchTestUp {

	public void init()  {
	
		
		String line = "10004,DIRD - DEPTO IDENT.REG.DIV              ,DIV.POL.PORTO/AERO/PROT.TURIS-DECADE    ,06º D.P. METROPOLITANO        ,S.PAULO                                 ,2011,2128,DECAP                                   ,DEL.SEC.1º CENTRO                       ,03º D.P. CAMPOS ELISEOS       ,S.PAULO                       ,Boletim de Ocorrência                   ,30/09/2011,14:30,NULL,Consumado,\"Furto qualificado (art. 155, §4o.)\",INTERIOR TRANSPORTE COLETIVO,§4o. Se o crime é cometido:,Terminal/Estação                        ,Metrov. e ferroviário metrop.-vagão                                             ,ENTRE EST. S. JUDAS E LUZ - METRÔ       ,0,-23.54379412,-46.63578529,Vítima              ,NULL,M,50,Parda               , ";
			
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
				
				int hourLine = Integer.parseInt(parts[13].substring(0, 1));
				
				
				/*
				if( parts[13].split(":").length > 1 ){
					hourLine = Integer.parseInt((parts[13].split(":")[0]));
				} else if( parts[13].split("H").length > 1 ){
					hourLine = Integer.parseInt((parts[13].split("H")[0]));
				}*/
					
				
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
				
		}
	
	}

	
}