package dal;

import bo.Utilisateur;
import exceptions.DAOException;

public interface UtilisateurDAO extends GeneriqueDAO<Utilisateur> {
	public Utilisateur authenticate(String pseudo, String motDePasse) throws DAOException;
}
