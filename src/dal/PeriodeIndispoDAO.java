package dal;

import java.util.List;

import bo.PeriodeIndisponibiliteVehicule;
import exceptions.DAOException;

public interface PeriodeIndispoDAO extends GeneriqueDAO<PeriodeIndisponibiliteVehicule> {
	public List<PeriodeIndisponibiliteVehicule> getAllByImmatriculation(String immatriculation) throws DAOException;
}
