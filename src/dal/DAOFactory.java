package dal;

import dal.jdbc.CampusDAOJdbc;
import dal.jdbc.LieuDAOJdbc;
import dal.jdbc.MotifDAOJdbc;
import dal.jdbc.PeriodeIndispoDAOJdbc;
import dal.jdbc.ReservataireDAOJdbc;
import dal.jdbc.ReservationDAOJdbc;
import dal.jdbc.UtilisateurDAOJdbc;
import dal.jdbc.VehiculeDAOJdbc;

public abstract class DAOFactory {
	
	public static UtilisateurDAO getUtilisateurDAO()
	{
		return new UtilisateurDAOJdbc();
	}
	public static ReservataireDAO getReservataireDAO()
	{
		return new ReservataireDAOJdbc();
	}
	public static LieuDAO getLieuDAO()
	{
		return new LieuDAOJdbc();
	}
	public static VehiculeDAO getVehiculeDAO()
	{
		return new VehiculeDAOJdbc();
	}
	public static CampusDAO getCampusDAO()
	{
		return new CampusDAOJdbc();
	}
	public static MotifDAO getMotifDAO()
	{
		return new MotifDAOJdbc();
	}
	public static PeriodeIndispoDAO getPeriodeIndispoDAO()
	{
		return new PeriodeIndispoDAOJdbc();
	}
	public static ReservationDAO getReservationDAO()
	{
		return new ReservationDAOJdbc();
	}
}
