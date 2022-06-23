package dal;

import bo.Vehicule;
import exceptions.DAOException;

public interface VehiculeDAO extends GeneriqueDAO<Vehicule> {
	public Vehicule selectByImmatricualtion(String immatriculation) throws DAOException;
	public void reinstate(Vehicule u) throws DAOException;
}
