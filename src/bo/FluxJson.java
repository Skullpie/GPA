package bo;

import java.io.Serializable;
import java.util.List;

public class FluxJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int nombreDePlace;
	private List<Reservataire> reservataires;
	
	public FluxJson() {
		super();
	}

	public FluxJson(int nombreDePlace, List<Reservataire> reservataires) {
		super();
		this.nombreDePlace = nombreDePlace;
		this.reservataires = reservataires;
	}

	public int getNombreDePlace() {
		return nombreDePlace;
	}

	public void setNombreDePlace(int nombreDePlace) {
		this.nombreDePlace = nombreDePlace;
	}

	public List<Reservataire> getReservataires() {
		return reservataires;
	}

	public void setReservataires(List<Reservataire> reservataires) {
		this.reservataires = reservataires;
	}
	
	
}
