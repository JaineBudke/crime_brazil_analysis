package CrimeAnalysis;
import java.io.File;
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
	private File file;
	
	// leitor de arquivo
	private Scanner arc;
	
	// armazenar dados do dataset aqui em uma estrutura de dados (usar hashmap)
    private List<String[]> dataset;
    
    
	// contador do dataset
	private volatile AtomicInteger count;
	
	/**
	 * Construtor. Referencia arquivo
	 * @throws IOException
	 */
	public Process() throws IOException {
		// caminho do dataset
		String fileName = "data/RDO_3.csv";
		// dataset
		this.file = new File(fileName);
		// leitor de arquivo
		this.arc = new Scanner(file);
		
		// ignora primeira linha: cabecalho
		arc.nextLine();
	
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
		if( arc.hasNextLine() ) {
			line = arc.nextLine();			
		}
		
		System.out.println(line);
		
		return line;
		
	}
	
	
}
