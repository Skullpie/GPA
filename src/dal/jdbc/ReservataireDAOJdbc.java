package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Lieu;
import bo.Reservataire;
import dal.ConnectionProvider;
import dal.ReservataireDAO;
import exceptions.DAOException;

public class ReservataireDAOJdbc implements ReservataireDAO {

	private static final String LISTE_RESERVATAIRES = "SELECT id_reservataire, nom, prenom, email, telephone, r.rue, r.code_postal, r.ville, r.id_lieu, intitule FROM RESERVATAIRES as r INNER JOIN LIEUX as l ON r.id_lieu = l.id_lieu ORDER BY nom;";
	private static final String GET_RESERVATAIRE = "SELECT id_reservataire, nom, prenom, email, telephone, r.rue, r.code_postal, r.ville, r.id_lieu, intitule FROM RESERVATAIRES as r INNER JOIN LIEUX as l ON r.id_lieu = l.id_lieu WHERE id_reservataire = ? ;";
	private static final String INSERT_RESERVATAIRE = "INSERT INTO RESERVATAIRES  (nom, prenom, email, telephone, rue, code_postal, ville, id_lieu) VALUES (?,?,?,?,?,?,?,?) ;";
	private static final String UPDATE = "UPDATE RESERVATAIRES SET  nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, id_lieu = ? WHERE id_reservataire = ?;";
	private static final String DELETE_RESERVATAIRE = "DELETE FROM RESERVATAIRES WHERE id_reservataire = ? ;";

	
	@Override
	public Reservataire selectById(int id) throws DAOException {
		Reservataire r = null;
		
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_RESERVATAIRE);	){
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					r = new Reservataire();
					r.setId(rs.getInt("id_reservataire"));
					r.setNom(rs.getString("nom"));
					r.setPrenom(rs.getString("prenom"));
					r.setEmail(rs.getString("email"));
					r.setTelephone(rs.getString("telephone"));
					r.setRue(rs.getString("rue"));
					r.setCodePostal(rs.getString("code_postal"));
					r.setVille(rs.getString("ville"));
					
					Lieu l = new Lieu();
					l.setId(rs.getInt("id_lieu"));
					l.setIntitule(rs.getString("intitule"));
					
					r.setLieu(l);
				}
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Echec de la récupération d'un réservataire", e);
			}
		return r;
	}

	@Override
	public List<Reservataire> selectAll() throws DAOException {
		List<Reservataire> lst = new ArrayList<Reservataire>();

		try (Connection cnx = ConnectionProvider.getConnection(); Statement stmt = cnx.createStatement();) {

			ResultSet rs = stmt.executeQuery(LISTE_RESERVATAIRES);
			Reservataire r = null;

			while (rs.next()) {
				r = new Reservataire();
				r = new Reservataire();
				r.setId(rs.getInt("id_reservataire"));
				r.setNom(rs.getString("nom"));
				r.setPrenom(rs.getString("prenom"));
				r.setEmail(rs.getString("email"));
				r.setTelephone(rs.getString("telephone"));
				r.setRue(rs.getString("rue"));
				r.setCodePostal(rs.getString("code_postal"));
				r.setVille(rs.getString("ville"));
				
				Lieu l = new Lieu();
				l.setId(rs.getInt("id_lieu"));
				l.setIntitule(rs.getString("intitule"));
				
				r.setLieu(l);

				lst.add(r);
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Réservataire>", e);
		}

		return lst;
	}

	@Override
	public void delete(Reservataire u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_RESERVATAIRE)) {

			pstmt.setInt(1, u.getId());
			pstmt.executeUpdate();


		} catch (SQLException e) {
			throw new DAOException("Echec de la suppression", e);
		}
	}

	@Override
	public void update(Reservataire u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE);) {

			pstmt.setString(1, u.getNom());
			pstmt.setString(2, u.getPrenom());
			pstmt.setString(3, u.getEmail());
			pstmt.setString(4, u.getTelephone());
			pstmt.setString(5, u.getRue());
			pstmt.setString(6, u.getCodePostal());
			pstmt.setString(7, u.getVille());
			pstmt.setInt(8, u.getLieu().getId());
			pstmt.setInt(9, u.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'update du réservataire", e);
		}
	}

	@Override
	public void insert(Reservataire u) throws DAOException {
		
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_RESERVATAIRE);) {

			pstmt.setString(1, u.getNom());
			pstmt.setString(2, u.getPrenom());
			pstmt.setString(3, u.getEmail());
			pstmt.setString(4, u.getTelephone());
			pstmt.setString(5, u.getRue());
			pstmt.setString(6, u.getCodePostal());
			pstmt.setString(7, u.getVille());
			pstmt.setInt(8, u.getLieu().getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion du véhicule", e);
		}
	}

}
