package CrimeAnalysis;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;



public class Process {

	// dataset
	private File file1;
	private File file2;
	private File file3;
	
	private File fileTest;
	private Scanner arcTest;
	
	// leitor de arquivo
	private Scanner arc1;
	private Scanner arc2;
	private Scanner arc3;
	
	// armazenar dados do dataset aqui em uma estrutura de dados (usar hashmap)
    private List<String[]> dataset;
    
    
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
		
		String fileName = "data/Test.csv";
		this.fileTest = new File(fileName);
		
		//this.arcTest = new Scanner(fileTest);
		

		
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
		
		// inicialização do iterador
		dataset = Collections.synchronizedList(new ArrayList<String[]>()); 
		
		count = new AtomicInteger(0); 
		
	}
	
	public int getQuantFurtos() {
		return dataset.size();
	}
	
	
	public void cleanCount() {
		count = new AtomicInteger(0);
	}
	
	
	public synchronized String[] getNext(){
			
		String[] line = null;
		
		int c = count.incrementAndGet(); 
		
		if( c < dataset.size() ){
			
			line = new String[3];
			line = dataset.get(c);
			
		}
				
		return line;
		
	}
	
	public synchronized void putLine( String[] memLine ){
		
		dataset.add( memLine );
		
	}
	
	
	/**
	 * Recupera proxima linha do arquivo
	 * @return linha corrente
	 */
	public synchronized String getLine() {
		
		String line = "";
		
		// recupera proxima linha do arquivo
		if( arc1.hasNextLine() ) {
			line = arc1.nextLine();			
		} else if( arc2.hasNextLine() ){
			line = arc2.nextLine();
		} else if( arc3.hasNextLine() ){
			line = arc3.nextLine();
		}
		
		/*if( arcTest.hasNextLine() ) {
			line = arcTest.nextLine();			
		} */
		
		//System.out.println(line);
		
		return line;
		
	}

	
	
}
