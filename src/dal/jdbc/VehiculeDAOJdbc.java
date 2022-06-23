package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Campus;
import bo.Reservataire;
import bo.Vehicule;
import dal.ConnectionProvider;
import dal.VehiculeDAO;
import exceptions.DAOException;

public class VehiculeDAOJdbc implements VehiculeDAO {

	private static final String LISTE_VEHICULE = "SELECT id_vehicule, immatriculation, description, date_achat, nombre_place, vendu, copie_carte_grise, id_reservataire, l.id_lieu, intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps, code FROM VEHICULES as v INNER JOIN lieux as l ON v.id_lieu = l.id_lieu ORDER BY intitule;";
	private static final String GET_VEHICULE_BY_IMMAT = "SELECT id_vehicule, immatriculation, description, date_achat, nombre_place, vendu, copie_carte_grise, id_reservataire, l.id_lieu, intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps, code FROM VEHICULES as v INNER JOIN lieux as l ON v.id_lieu = l.id_lieu WHERE immatriculation = ? ;";
	private static final String GET_VEHICULE_BY_ID = "SELECT id_vehicule, immatriculation, description, date_achat, nombre_place, vendu, copie_carte_grise, id_reservataire, l.id_lieu, intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps, code FROM VEHICULES as v INNER JOIN lieux as l ON v.id_lieu = l.id_lieu WHERE id_vehicule = ? ;";
	private static final String INSERT = "INSERT INTO VEHICULES  (immatriculation, description, date_achat, nombre_place, vendu, copie_carte_grise, id_reservataire, id_lieu) VALUES (?,?,?,?,?,?,?,?) ;";
	private static final String UPDATE = "UPDATE VEHICULES SET  immatriculation = ?, description = ?, date_achat = ?, nombre_place = ?, vendu = ?, copie_carte_grise = ?, id_reservataire = ?, id_lieu = ? WHERE id_vehicule = ?;";
	private static final String DELETE = "UPDATE VEHICULES SET  vendu = true WHERE immatriculation = ?";
	private static final String REINSTATE = "UPDATE VEHICULES SET  vendu = false WHERE immatriculation = ?";

	@Override
	public Vehicule selectByImmatricualtion(String immatriculation) throws DAOException {

		Vehicule v = null;
		Reservataire r = null;
		Campus c = null;

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_VEHICULE_BY_IMMAT);) {
			pstmt.setString(1, immatriculation);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				v = new Vehicule();
				v.setId(rs.getInt("id_vehicule"));
				v.setImmatriculation(rs.getString("immatriculation"));
				v.setDescription(rs.getString("description"));
				v.setDateAchat(rs.getDate("date_achat"));
				v.setNombrePlace(rs.getInt("nombre_place"));
				v.setVendu(rs.getBoolean("vendu"));
				v.setCopieCarteGrise(rs.getString("copie_carte_grise"));

				if (rs.getInt("id_reservataire") != 0) {
					ReservataireDAOJdbc rdao = new ReservataireDAOJdbc();
					r = rdao.selectById(rs.getInt("id_reservataire"));
					v.setReservataire(r);
				}
				if (rs.getInt("id_lieu") != 0) {
					c = new Campus();
					c.setId(rs.getInt("id_lieu"));
					c.setIntitule(rs.getString("intitule"));
					c.setCodePostal(rs.getString("code_postal"));
					c.setVille(rs.getString("ville"));
					c.setInformationComplementaire(rs.getString("information_complementaire"));
					c.setLatitude(rs.getString("latitude_gps"));
					c.setLongitude(rs.getString("longitude_gps"));
					c.setCode(rs.getString("code"));

					v.setLieu(c);
				}

			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la récupération d'un véhicule", e);
		}
		return v;
	}

	@Override
	public Vehicule selectById(int id) throws DAOException {
		Vehicule v = null;
		Reservataire r = null;
		Campus c = null;

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_VEHICULE_BY_ID);) {
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				v = new Vehicule();
				v.setId(rs.getInt("id_vehicule"));
				v.setImmatriculation(rs.getString("immatriculation"));
				v.setDescription(rs.getString("description"));
				v.setDateAchat(rs.getDate("date_achat"));
				v.setNombrePlace(rs.getInt("nombre_place"));
				v.setVendu(rs.getBoolean("vendu"));
				v.setCopieCarteGrise(rs.getString("copie_carte_grise"));

				if (rs.getInt("id_reservataire") != 0) {
					ReservataireDAOJdbc rdao = new ReservataireDAOJdbc();
					r = rdao.selectById(rs.getInt("id_reservataire"));
					v.setReservataire(r);
				}
				if (rs.getInt("id_lieu") != 0) {
					c = new Campus();
					c.setId(rs.getInt("id_lieu"));
					c.setIntitule(rs.getString("intitule"));
					c.setCodePostal(rs.getString("code_postal"));
					c.setVille(rs.getString("ville"));
					c.setInformationComplementaire(rs.getString("information_complementaire"));
					c.setLatitude(rs.getString("latitude_gps"));
					c.setLongitude(rs.getString("longitude_gps"));
					c.setCode(rs.getString("code"));

					v.setLieu(c);
				}

			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la récupération d'un véhicule", e);
		}
		return v;
	}

	@Override
	public List<Vehicule> selectAll() throws DAOException {
		List<Vehicule> lst = new ArrayList<Vehicule>();

		try (Connection cnx = ConnectionProvider.getConnection(); Statement stmt = cnx.createStatement();) {

			ResultSet rs = stmt.executeQuery(LISTE_VEHICULE);
			Vehicule v = null;
			Reservataire r = null;
			Campus c = null;

			while (rs.next()) {
				v = new Vehicule();
				v.setId(rs.getInt("id_vehicule"));
				v.setImmatriculation(rs.getString("immatriculation"));
				v.setDescription(rs.getString("description"));
				v.setDateAchat(rs.getDate("date_achat"));
				v.setNombrePlace(rs.getInt("nombre_place"));
				v.setVendu(rs.getBoolean("vendu"));
				v.setCopieCarteGrise(rs.getString("copie_carte_grise"));

				if (rs.getInt("id_reservataire") != 0) {
					ReservataireDAOJdbc rdao = new ReservataireDAOJdbc();
					r = rdao.selectById(rs.getInt("id_reservataire"));
					v.setReservataire(r);
				}
				if (rs.getInt("id_lieu") != 0) {
					c = new Campus();
					c.setId(rs.getInt("id_lieu"));
					c.setIntitule(rs.getString("intitule"));
					c.setCodePostal(rs.getString("code_postal"));
					c.setVille(rs.getString("ville"));
					c.setInformationComplementaire(rs.getString("information_complementaire"));
					c.setLatitude(rs.getString("latitude_gps"));
					c.setLongitude(rs.getString("longitude_gps"));
					c.setCode(rs.getString("code"));

					v.setLieu(c);

					lst.add(v);
				}
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Véhicules>", e);
		}

		return lst;
	}

	@Override
	public void delete(Vehicule u) throws DAOException {

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE);) {

			pstmt.setString(1, u.getImmatriculation());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de la suppression du véhicule", e);
		}
	}

	@Override
	public void reinstate(Vehicule u) throws DAOException {

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(REINSTATE);) {

			pstmt.setString(1, u.getImmatriculation());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de la réintégration du véhicule", e);
		}
	}

	@Override
	public void update(Vehicule u) throws DAOException {
		
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE);) {

			pstmt.setString(1, u.getImmatriculation());
			pstmt.setString(2, u.getDescription());
			pstmt.setDate(3, u.getDateAchat());
			pstmt.setInt(4, u.getNombrePlace());
			pstmt.setBoolean(5, u.isVendu());
			pstmt.setString(6, u.getCopieCarteGrise());
			if (u.getReservataire() != null) {
				pstmt.setInt(7, u.getReservataire().getId());
			} else {
				pstmt.setString(7, null);
			}
			pstmt.setInt(8, u.getLieu().getId());
			pstmt.setInt(9, u.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'update du véhicule", e);
		}
	}

	@Override
	public void insert(Vehicule u) throws DAOException {
		
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERT);) {

			pstmt.setString(1, u.getImmatriculation());
			pstmt.setString(2, u.getDescription());
			pstmt.setDate(3, u.getDateAchat());
			pstmt.setInt(4, u.getNombrePlace());
			pstmt.setBoolean(5, false);
			pstmt.setString(6, u.getCopieCarteGrise());
			if (u.getReservataire() != null) {
				pstmt.setInt(7, u.getReservataire().getId());
			} else {
				pstmt.setString(7, null);
			}
			pstmt.setInt(8, u.getLieu().getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion du véhicule", e);
		}
	}

}
