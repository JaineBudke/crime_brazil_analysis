package CrimeAnalysis;


public class Features {

	// contadores dos parametros
	private int qntTurno;
	private int qntSexo;
	private int qntCor;
	
	
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
	
	
	/**
	 * Incrementa variavel de quantidade de cores nulas no dataset
	 */
	public synchronized int incrementCoresNulas(){
		return coresNulas++;
	}
	
	/**
	 * Incrementa variavel de quantidade de turnos nulos no dataset
	 */
	public synchronized int incrementTurnoNulo(){
		return turnoNulo++;
	}
	
	/**
	 * Incrementa variavel de quantidade de sexo nulo no dataset
	 */
	public synchronized int incrementSexoNulo(){
		return sexoNulo++;
	}

	/**
	 * Incrementa variavel de quantidade de correspondências do parametro turno
	 */
	public synchronized int incrementTurno(){
		return qntTurno++;
	}

	/**
	 * Incrementa variavel de quantidade de correspondências do parametro sexo
	 */
	public synchronized int incrementSexo(){
		return qntSexo++;
	}
	
	/**
	 * Incrementa variavel de quantidade de correspondências do parametro cor
	 */
	public synchronized int incrementCor(){
		return qntCor++;
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
