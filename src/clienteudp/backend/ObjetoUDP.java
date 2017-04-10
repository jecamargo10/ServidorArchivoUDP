package clienteudp.backend;

import java.io.Serializable;
import java.util.Date;

public class ObjetoUDP implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private int numeroSecuencia;
	private Date marcaTiempo;

	public ObjetoUDP(int numSec,Date marcaTmp){
		numeroSecuencia=numSec;
		marcaTiempo=marcaTmp;
	}

	public int getNumeroSecuencia() {
		return numeroSecuencia;
	}

	public void setNumeroSecuencia(int numeroSecuencia) {
		this.numeroSecuencia = numeroSecuencia;
	}

	public Date getMarcaTiempo() {
		return marcaTiempo;
	}

	public void setMarcaTiempo(Date marcaTiempo) {
		this.marcaTiempo = marcaTiempo;
	}

}
