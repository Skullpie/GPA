package dal;

import java.util.List;

import bo.Reservation;
import exceptions.DAOException;

public interface ReservationDAO extends GeneriqueDAO<Reservation> {
	public List<Reservation> selectByImmat(String immatriculation) throws DAOException;
}
