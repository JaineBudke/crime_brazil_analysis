package CrimeAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;



public class Process {

	// dataset
	private File file1;
	private File file2;
	private File file3;
	
	// leitor de arquivo
	private Scanner arc1;
	private Scanner arc2;
	private Scanner arc3;
	
	// armazenar dados do dataset aqui em uma estrutura de dados
    private List<String[]> dataset;

    private List<String> datasetCor;
    private List<String> datasetSexo;
    private List<String> datasetTurno;
    
    
	// contador do dataset
	private AtomicInteger count;
	
	/**
	 * Construtor. Referencia arquivo
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
	public Process() throws FileNotFoundException {
		
		/**
		 * DATASETs
		 */
		// caminho do dataset
		String fileName1 = "data/RDO_1.csv";
		String fileName2 = "data/RDO_2.csv";
		String fileName3 = "data/RDO_3.csv";
		
		
		// dataset
		this.file1 = new File(fileName1);
		this.file2 = new File(fileName2);
		this.file3 = new File(fileName3);
		
		
		// leitor de arquivo
		this.arc1 = new Scanner(file1);
		this.arc2 = new Scanner(file2);
		this.arc3 = new Scanner(file3);
		
		
		// ignora primeira linha: cabecalho
		arc1.nextLine();
		arc2.nextLine();
		arc3.nextLine();
		
		dataset = Collections.synchronizedList(new ArrayList<String[]>()); 
		
	    datasetCor   = Collections.synchronizedList(new ArrayList<String>()); 
	    datasetSexo  = Collections.synchronizedList(new ArrayList<String>()); 
	    datasetTurno = Collections.synchronizedList(new ArrayList<String>()); 
	    
	    
		count = new AtomicInteger(0); 
		
	}
	
	
	public List<String> getListS() {
		return datasetSexo;
	}
	
	public List<String> getListC() {
		return datasetCor;
	}
	
	public List<String> getListT() {
		return datasetTurno;
	}
	
	
	
	
	/**
	 * Recupera quantidade de furtos
	 * @return Quantidade de furtos
	 */
	public int getQuantFurtos() {
		return dataset.size();
	}
	
	/**
	 * Limpa contador
	 */
	public void clearCount() {
		count = new AtomicInteger(0);
	}
	
	
	/**
	 * Recupera próximo dado da memória
	 * @return vetor com dados da memória
	 */
	public synchronized String[] getNext(){
			
		String[] line = null;
		
		int c = count.incrementAndGet(); 
		
		if( c < dataset.size() ){
			
			line = new String[3];
			line = dataset.get(c);
			
		}
				
		return line;
		
	}
	
	
	/**
	 * Adiciona novo dado à memória
	 * @param memLine Dado a ser adicionado
	 */
	public synchronized void putLine( String[] memLine ){
		
		dataset.add( memLine );
		
	}
	
	
	

	public synchronized void putLineS( String memLine ){		
		datasetSexo.add( memLine );
	}
	
	public synchronized void putLineC( String memLine ){		
		datasetCor.add( memLine );
	}
	
	public synchronized void putLineT( String memLine ){		
		datasetTurno.add( memLine );
	}
	
	
	
	/**
	 * Recupera proxima linha do arquivo
	 * @return linha corrente
	 */
	public synchronized String getLine() {
		
		String line = "";
		
		// recupera proxima linha do arquivo
		/*if( arc1.hasNextLine() ) {
			line = arc1.nextLine();			
		} else if( arc2.hasNextLine() ){
			line = arc2.nextLine();
		} else*/ if( arc3.hasNextLine() ){
			line = arc3.nextLine();
		} 
		System.out.println(line);
		
		return line;
		
	}

	
	
}
