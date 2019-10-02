package CrimeAnalysis;


public class Features {

	// contadores dos parametros
	private int qntTurno;
	private int qntSexo;
	private int qntCor;

	
	private int qntFurtos;
	
	// contadores dos valores nulos
	private int coresNulas;
	private int turnoNulo;
	private int sexoNulo;
	
	
	// construtor
	public Features() {
		qntTurno = 0;
		qntSexo = 0;
		qntCor = 0;
		
		coresNulas = 0;
		turnoNulo = 0;
		sexoNulo = 0;
	}
	
	
	
	
	public void incrementFurtos(){
		qntFurtos++;
	}
	
	public int getFurtos() {
		return qntFurtos;
	}
	
	
	/**
	 * Incrementa variavel de quantidade de cores nulas no dataset
	 */
	public synchronized int incrementCoresNulas( int quantity ){
		return coresNulas+=quantity;
	}
	
	/**
	 * Incrementa variavel de quantidade de turnos nulos no dataset
	 */
	public synchronized int incrementTurnoNulo( int quantity ){
		return turnoNulo+=quantity;
	}
	
	/**
	 * Incrementa variavel de quantidade de sexo nulo no dataset
	 */
	public synchronized int incrementSexoNulo( int quantity ){
		return sexoNulo+=quantity;
	}

	/**
	 * Incrementa variavel de quantidade de correspondências do parametro turno
	 */
	public synchronized int incrementTurno( int quantity ){
		return qntTurno+=quantity;
	}

	/**
	 * Incrementa variavel de quantidade de correspondências do parametro sexo
	 */
	public synchronized int incrementSexo( int quantity ){
		return qntSexo+=quantity;
	}
	
	/**
	 * Incrementa variavel de quantidade de correspondências do parametro cor
	 */
	public synchronized int incrementCor( int quantity ){
		return qntCor+=quantity;
	}
	

	
	/**
	 * Recupera quantidade de correspondencias no turno passado pelo usuario
	 */
	public int getTurno(){
		return qntTurno;
	}
	
	/**
	 * Recupera quantidade de correspondencias no sexo passado pelo usuario
	 */
	public int getSexo(){
		return qntSexo;
	}
	
	/**
	 * Recupera quantidade de correspondencias na cor passado pelo usuario
	 */
	public int getCor(){
		return qntCor;
	}
	

	
	/**
	 * Recupera quantidade de cores nulas no dataset
	 */
	public int getCoresNulas() {
		return coresNulas;
	}
	
	/**
	 * Recupera quantidade de turnos nulos no dataset
	 */
	public int getTurnoNulo() {
		return turnoNulo;
	}
	
	/**
	 * Recupera quantidade de sexo nulo no dataset
	 */
	public int getSexoNulo() {
		return sexoNulo;
	}
	
}
