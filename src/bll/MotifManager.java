package bll;

import java.util.ArrayList;
import java.util.List;

import bo.MotifIndisponibilite;
import dal.DAOFactory;
import dal.MotifDAO;
import exceptions.BLLException;
import exceptions.DAOException;

public abstract class MotifManager {

	public static MotifIndisponibilite get(int id) throws BLLException {
		MotifIndisponibilite m = new MotifIndisponibilite();

		MotifDAO mManager = DAOFactory.getMotifDAO();

		try {
			m = mManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return m;
	}

	public static List<MotifIndisponibilite> getAll() throws BLLException {
		List<MotifIndisponibilite> lst = new ArrayList<MotifIndisponibilite>();

		MotifDAO mManager = DAOFactory.getMotifDAO();

		try {
			lst = mManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return lst;
	}

	public static void inserer(MotifIndisponibilite m) throws BLLException {

		MotifDAO mManager = DAOFactory.getMotifDAO();

		try {
			mManager.insert(m);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

}
