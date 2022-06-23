package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bo.Lieu;
import bo.Reservataire;
import bo.Reservation;
import bo.Vehicule;
import dal.ConnectionProvider;
import dal.ReservationDAO;
import exceptions.DAOException;

public class ReservationDAOJdbc implements ReservationDAO {

	private static final String LISTE_RESERVATION = "SELECT r.id_reservation, r.motif, r.datetime_debut, r.datetime_fin, `commentaire`, r.immatriculation, r.id_lieu, v.description, l.intitule, rr.id_reservataire, re.nom, re.prenom FROM `reservations` as r INNER JOIN vehicules as v on r.immatriculation = v.immatriculation INNER JOIN lieux as l ON r.id_lieu = l.id_lieu INNER JOIN reservations_reservataires as rr ON r.id_reservation = rr.id_reservation LEFT OUTER JOIN reservataires as re ON rr.id_reservataire = re.id_reservataire ORDER BY r.datetime_debut DESC;";
	private static final String GET_RESERVATION = "SELECT r.id_reservation, r.motif, r.datetime_debut, r.datetime_fin, `commentaire`, r.immatriculation, r.id_lieu, v.description, v.nombre_place, l.intitule, rr.id_reservataire, re.nom, re.prenom FROM `reservations` as r INNER JOIN vehicules as v on r.immatriculation = v.immatriculation INNER JOIN lieux as l ON r.id_lieu = l.id_lieu INNER JOIN reservations_reservataires as rr ON r.id_reservation = rr.id_reservation LEFT OUTER JOIN reservataires as re ON rr.id_reservataire = re.id_reservataire WHERE r.id_reservation = ?;";
	private static final String GET_RESERVATION_BY_IMMAT = "SELECT r.id_reservation, r.motif, r.datetime_debut, r.datetime_fin, `commentaire`, r.immatriculation, r.id_lieu, v.description, l.intitule, rr.id_reservataire, re.nom, re.prenom FROM `reservations` as r INNER JOIN vehicules as v on r.immatriculation = v.immatriculation INNER JOIN lieux as l ON r.id_lieu = l.id_lieu INNER JOIN reservations_reservataires as rr ON r.id_reservation = rr.id_reservation LEFT OUTER JOIN reservataires as re ON rr.id_reservataire = re.id_reservataire WHERE r.immatriculation = ?;";
	private static final String INSERT_RESERVATION = "INSERT INTO `reservations` (`motif`, `datetime_debut`, `datetime_fin`, `commentaire`, `immatriculation`, `id_lieu`) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String INSERT_RESERVATION_RESERVATAIRE = "INSERT INTO `reservations_reservataires` (`id_reservation`, `id_reservataire`) VALUES (?, ?);";
	private static final String UPDATE_RESERVATION = "UPDATE reservations SET  motif = ?, datetime_debut = ?, datetime_fin = ?, commentaire = ?, immatriculation = ?, id_lieu = ? WHERE id_reservation = ?;";
	private static final String DELETE_RESERVATION_RESERVATAIRE = "DELETE FROM reservations_reservataires WHERE id_reservation = ? ;";
	private static final String DELETE_RESERVATION = "DELETE FROM reservations WHERE id_reservation = ? ;";

	@Override
	public Reservation selectById(int id) throws DAOException {
		Reservation r = null;

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_RESERVATION);) {
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				if (r == null) {
					r = new Reservation();
					r.setId(rs.getInt("id_reservation"));
					r.setMotif(rs.getString("motif"));
					r.setDateDebut(LocalDateTime.of(rs.getDate("datetime_debut").toLocalDate(),
							rs.getTime("datetime_debut").toLocalTime()));
					r.setDateFin(LocalDateTime.of(rs.getDate("datetime_fin").toLocalDate(),
							rs.getTime("datetime_fin").toLocalTime()));
					r.setCommentaire(rs.getString("commentaire"));
					Vehicule v = new Vehicule();
					v.setImmatriculation(rs.getString("immatriculation"));
					v.setDescription(rs.getString("description"));
					v.setNombrePlace(rs.getInt("nombre_place")-1); //le -1 sert à l'affichage lors du chargement en get d'ajouter et modifier une réservation
					r.setVehicule(v);
					Lieu l = new Lieu();
					l.setId(rs.getInt("id_lieu"));
					l.setIntitule(rs.getString("intitule"));
					r.setLieu(l);
					Reservataire re = new Reservataire();
					re.setId(rs.getInt("id_reservataire"));
					re.setNom(rs.getString("nom"));
					re.setPrenom(rs.getString("prenom"));
					r.addReservataire(re);
				} else {
					Reservataire re = new Reservataire();
					re.setId(rs.getInt("id_reservataire"));
					re.setNom(rs.getString("nom"));
					re.setPrenom(rs.getString("prenom"));
					r.addReservataire(re);
				}

			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la récupération d'une réservation", e);
		}
		return r;
	}

	@Override
	public List<Reservation> selectByImmat(String immatriculation) throws DAOException {
		List<Reservation> lst = new ArrayList<Reservation>();

		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_RESERVATION_BY_IMMAT);) {
			pstmt.setString(1, immatriculation);

			ResultSet rs = pstmt.executeQuery();

			Reservation r = null;
			int index = -1;

			while (rs.next()) {

				if (r == null || r.getId() != rs.getInt("id_reservation")) {
					r = new Reservation();
					r.setId(rs.getInt("id_reservation"));
					r.setMotif(rs.getString("motif"));
					r.setDateDebut(LocalDateTime.of(rs.getDate("datetime_debut").toLocalDate(),
							rs.getTime("datetime_debut").toLocalTime()));
					r.setDateFin(LocalDateTime.of(rs.getDate("datetime_fin").toLocalDate(),
							rs.getTime("datetime_fin").toLocalTime()));
					r.setCommentaire(rs.getString("commentaire"));
					Vehicule v = new Vehicule();
					v.setImmatriculation(rs.getString("immatriculation"));
					v.setDescription(rs.getString("description"));
					r.setVehicule(v);
					Lieu l = new Lieu();
					l.setId(rs.getInt("id_lieu"));
					l.setIntitule(rs.getString("intitule"));
					r.setLieu(l);

					Reservataire re = new Reservataire();
					re.setId(rs.getInt("id_reservataire"));
					re.setNom(rs.getString("nom"));
					re.setPrenom(rs.getString("prenom"));
					r.addReservataire(re);
					index++;
					lst.add(r);
				} else {
					Reservataire re = new Reservataire();
					re.setId(rs.getInt("id_reservataire"));
					re.setNom(rs.getString("nom"));
					re.setPrenom(rs.getString("prenom"));
					lst.get(index).addReservataire(re);
				}
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Reservation>", e);
		}

		return lst;
	}

	@Override
	public List<Reservation> selectAll() throws DAOException {
		List<Reservation> lst = new ArrayList<Reservation>();

		try (Connection cnx = ConnectionProvider.getConnection(); Statement stmt = cnx.createStatement();) {

			ResultSet rs = stmt.executeQuery(LISTE_RESERVATION);
			Reservation r = null;
			int index = -1;

			while (rs.next()) {

				if (r == null || r.getId() != rs.getInt("id_reservation")) {
					r = new Reservation();
					r.setId(rs.getInt("id_reservation"));
					r.setMotif(rs.getString("motif"));
					r.setDateDebut(LocalDateTime.of(rs.getDate("datetime_debut").toLocalDate(),
							rs.getTime("datetime_debut").toLocalTime()));
					r.setDateFin(LocalDateTime.of(rs.getDate("datetime_fin").toLocalDate(),
							rs.getTime("datetime_fin").toLocalTime()));
					r.setCommentaire(rs.getString("commentaire"));
					Vehicule v = new Vehicule();
					v.setImmatriculation(rs.getString("immatriculation"));
					v.setDescription(rs.getString("description"));
					r.setVehicule(v);
					Lieu l = new Lieu();
					l.setId(rs.getInt("id_lieu"));
					l.setIntitule(rs.getString("intitule"));
					r.setLieu(l);

					Reservataire re = new Reservataire();
					re.setId(rs.getInt("id_reservataire"));
					re.setNom(rs.getString("nom"));
					re.setPrenom(rs.getString("prenom"));
					r.addReservataire(re);
					index++;
					lst.add(r);
				} else {
					Reservataire re = new Reservataire();
					re.setId(rs.getInt("id_reservataire"));
					re.setNom(rs.getString("nom"));
					re.setPrenom(rs.getString("prenom"));
					lst.get(index).addReservataire(re);
				}
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Reservation>", e);
		}

		return lst;
	}

	@Override
	public void delete(Reservation u) throws DAOException {
		
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_RESERVATION)) {

			pstmt.setInt(1, u.getId());
			pstmt.executeUpdate();


		} catch (SQLException e) {
			throw new DAOException("Echec de la suppression", e);
		}
	}

	@Override
	public void update(Reservation u) throws DAOException {
		
		Connection cnx = null;
		try { 
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_RESERVATION);
			PreparedStatement pstmt2 = cnx.prepareStatement(DELETE_RESERVATION_RESERVATAIRE);
			PreparedStatement pstmt3 = cnx.prepareStatement(INSERT_RESERVATION_RESERVATAIRE);
			cnx.setAutoCommit(false);
			

			pstmt.setString(1, u.getMotif());
			pstmt.setTimestamp(2, Timestamp.valueOf(u.getDateDebut()));
			pstmt.setTimestamp(3, Timestamp.valueOf(u.getDateFin()));
			pstmt.setString(4, u.getCommentaire());
			pstmt.setString(5, u.getVehicule().getImmatriculation());
			pstmt.setInt(6, u.getLieu().getId());
			pstmt.setInt(7, u.getId());

			pstmt.executeUpdate();

			pstmt2.setInt(1, u.getId());
			pstmt2.executeUpdate();
			
			

			for (Reservataire reservataire : u.getReservataires()) {
				pstmt3.setInt(1, u.getId());
				pstmt3.setInt(2, reservataire.getId());
				
				pstmt3.executeUpdate();
			}

			


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion des réservataires", e);
		}finally {
			if (cnx!=null) {
				try {
					cnx.setAutoCommit(true);
					cnx.close();
				} catch (SQLException e) {
					throw new DAOException("Echec de fermeture de connexion", e);
				}
			}
		}
	}

	@Override
	public void insert(Reservation u) throws DAOException {
		
		Connection cnx = null;
		try {
			cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_RESERVATION, PreparedStatement.RETURN_GENERATED_KEYS);
			PreparedStatement pstmt2 = cnx.prepareStatement(INSERT_RESERVATION_RESERVATAIRE);
			cnx.setAutoCommit(false);
			
			pstmt.setString(1, u.getMotif());
			pstmt.setTimestamp(2, Timestamp.valueOf(u.getDateDebut()));
			pstmt.setTimestamp(3, Timestamp.valueOf(u.getDateFin()));
			pstmt.setString(4, u.getCommentaire());
			pstmt.setString(5, u.getVehicule().getImmatriculation());
			pstmt.setInt(6, u.getLieu().getId());
			

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				u.setId(rs.getInt(1));
				
				for (Reservataire reservataire : u.getReservataires()) {

					pstmt2.setInt(1, u.getId());
					pstmt2.setInt(2, reservataire.getId());

					pstmt2.executeUpdate();

				}
			}
			rs.close();
			cnx.commit();
		} catch (SQLException e) {
			try {
				cnx.rollback();
			} catch (SQLException e1) {
				throw new DAOException("Echec de l'insertion des réservataires", e);
			}
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion des réservataires", e);
		}finally {
			if (cnx!=null) {
				try {
					cnx.setAutoCommit(true);
					cnx.close();
				} catch (SQLException e) {
					throw new DAOException("Echec de fermeture de connexion", e);
				}
			}
		}
	}
}
