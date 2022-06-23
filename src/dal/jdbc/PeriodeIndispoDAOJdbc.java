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

import bo.MotifIndisponibilite;
import bo.PeriodeIndisponibiliteVehicule;
import bo.Vehicule;
import dal.ConnectionProvider;
import dal.PeriodeIndispoDAO;
import exceptions.DAOException;

public class PeriodeIndispoDAOJdbc implements PeriodeIndispoDAO {
	
	private static final String LISTE_PERIODE_INDISPO = "SELECT id_indisponibilite, datetime_debut, datetime_fin, p.id_motif_indisponibilite, intitule, p.immatriculation, description, vendu FROM periodes_indisponibilite_vehicule as p LEFT OUTER JOIN motifs_indisponibilite as m ON p.id_motif_indisponibilite = m.id_motif_indisponibilite LEFT OUTER JOIN vehicules as v ON p.immatriculation = v.immatriculation;";
	private static final String LISTE_PERIODE_INDISPO_IMMAT = "SELECT id_indisponibilite, datetime_debut, datetime_fin, p.id_motif_indisponibilite, intitule, p.immatriculation, description, vendu FROM periodes_indisponibilite_vehicule as p LEFT OUTER JOIN motifs_indisponibilite as m ON p.id_motif_indisponibilite = m.id_motif_indisponibilite LEFT OUTER JOIN vehicules as v ON p.immatriculation = v.immatriculation WHERE p.immatriculation = ?";
	private static final String GET_INDISPO = "SELECT id_indisponibilite, datetime_debut, datetime_fin, p.id_motif_indisponibilite, intitule, p.immatriculation, description, vendu FROM periodes_indisponibilite_vehicule as p INNER JOIN vehicules as v ON p.immatriculation = v.immatriculation INNER JOIN motifs_indisponibilite as m ON p.id_motif_indisponibilite = m.id_motif_indisponibilite WHERE id_indisponibilite = ? ;";
	private static final String INSERT_INDISPO = "INSERT INTO periodes_indisponibilite_vehicule  (datetime_debut, datetime_fin, id_motif_indisponibilite, immatriculation) VALUES (?,?,?,?) ;";
	private static final String UPDATE = "UPDATE periodes_indisponibilite_vehicule SET  datetime_debut = ?, datetime_fin = ?, id_motif_indisponibilite = ?, immatriculation = ? WHERE id_indisponibilite = ?;";
	private static final String DELETE = "DELETE FROM periodes_indisponibilite_vehicule WHERE id_indisponibilite = ? ;";
	

	@Override
	public PeriodeIndisponibiliteVehicule selectById(int id) throws DAOException {

		PeriodeIndisponibiliteVehicule p = null;
		
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_INDISPO);	){
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					p = new PeriodeIndisponibiliteVehicule();
					p.setIdIndisponibilite(rs.getInt("id_indisponibilite"));
					p.setDateDebut(LocalDateTime.of(rs.getDate("datetime_debut").toLocalDate(), rs.getTime("datetime_debut").toLocalTime()));
					p.setDateFin(LocalDateTime.of(rs.getDate("datetime_fin").toLocalDate(), rs.getTime("datetime_fin").toLocalTime()));
					
					MotifIndisponibilite m = new MotifIndisponibilite();
					m.setId(rs.getInt("id_motif_indisponibilite"));
					m.setIntitule(rs.getString("intitule"));
					p.setMotifIndisponibilite(m);
					
					Vehicule v = new Vehicule();
					v.setImmatriculation(rs.getString("immatriculation"));
					v.setDescription(rs.getString("description"));
					v.setVendu(rs.getBoolean("vendu"));
					p.setVehicule(v);
				}
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Echec de la récupération d'un lieu", e);
			}
		return p;
	}

	@Override
	public List<PeriodeIndisponibiliteVehicule> selectAll() throws DAOException {
		List<PeriodeIndisponibiliteVehicule> lst = new ArrayList<PeriodeIndisponibiliteVehicule>();
		
		try(Connection cnx = ConnectionProvider.getConnection();
			Statement stmt = cnx.createStatement();	){
			
			ResultSet rs = stmt.executeQuery(LISTE_PERIODE_INDISPO);
			PeriodeIndisponibiliteVehicule p = null;
			
			while(rs.next()) {
				p = new PeriodeIndisponibiliteVehicule();
				p.setIdIndisponibilite(rs.getInt("id_indisponibilite"));
				p.setDateDebut(LocalDateTime.of(rs.getDate("datetime_debut").toLocalDate(), rs.getTime("datetime_debut").toLocalTime()));
				p.setDateFin(LocalDateTime.of(rs.getDate("datetime_fin").toLocalDate(), rs.getTime("datetime_fin").toLocalTime()));
				
				MotifIndisponibilite m = new MotifIndisponibilite();
				m.setId(rs.getInt("id_motif_indisponibilite"));
				m.setIntitule(rs.getString("intitule"));
				p.setMotifIndisponibilite(m);
				
				Vehicule v = new Vehicule();
				v.setImmatriculation(rs.getString("immatriculation"));
				v.setDescription(rs.getString("description"));
				v.setVendu(rs.getBoolean("vendu"));
				p.setVehicule(v);
				
				lst.add(p);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Periode>", e);
		}
		
		return lst;
	}

	@Override
	public void delete(PeriodeIndisponibiliteVehicule u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE)) {

			pstmt.setInt(1, u.getIdIndisponibilite());
			pstmt.executeUpdate();


		} catch (SQLException e) {
			throw new DAOException("Echec de la suppression", e);
		}	
		
	}

	@Override
	public void update(PeriodeIndisponibiliteVehicule u) throws DAOException {
		
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE);) {

			pstmt.setTimestamp(1, Timestamp.valueOf(u.getDateDebut()));
			pstmt.setTimestamp(2, Timestamp.valueOf(u.getDateFin()));
			pstmt.setInt(3, u.getMotifIndisponibilite().getId());
			pstmt.setString(4, u.getVehicule().getImmatriculation());
			pstmt.setInt(5, u.getIdIndisponibilite());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'update du véhicule", e);
		}
	}

	@Override
	public void insert(PeriodeIndisponibiliteVehicule u) throws DAOException {
		
		
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_INDISPO);){

			pstmt.setTimestamp(1, Timestamp.valueOf(u.getDateDebut()));
			pstmt.setTimestamp(2, Timestamp.valueOf(u.getDateFin()));
			pstmt.setInt(3, u.getMotifIndisponibilite().getId());
			pstmt.setString(4, u.getVehicule().getImmatriculation());

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion du Lieu", e);	
		}
	}

	@Override
	public List<PeriodeIndisponibiliteVehicule> getAllByImmatriculation(String immatriculation) throws DAOException {
		List<PeriodeIndisponibiliteVehicule> lst = new ArrayList<PeriodeIndisponibiliteVehicule>();
		
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(LISTE_PERIODE_INDISPO_IMMAT);	){
				pstmt.setString(1, immatriculation);
				
				ResultSet rs = pstmt.executeQuery();

			PeriodeIndisponibiliteVehicule p = null;
			
			while(rs.next()) {
				p = new PeriodeIndisponibiliteVehicule();
				p.setIdIndisponibilite(rs.getInt("id_indisponibilite"));
				p.setDateDebut(LocalDateTime.of(rs.getDate("datetime_debut").toLocalDate(), rs.getTime("datetime_debut").toLocalTime()));
				p.setDateFin(LocalDateTime.of(rs.getDate("datetime_fin").toLocalDate(), rs.getTime("datetime_fin").toLocalTime()));
				
				MotifIndisponibilite m = new MotifIndisponibilite();
				m.setId(rs.getInt("id_motif_indisponibilite"));
				m.setIntitule(rs.getString("intitule"));
				p.setMotifIndisponibilite(m);
				
				Vehicule v = new Vehicule();
				v.setImmatriculation(rs.getString("immatriculation"));
				v.setDescription(rs.getString("description"));
				v.setVendu(rs.getBoolean("vendu"));
				p.setVehicule(v);
				
				lst.add(p);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Periode>", e);
		}
		return lst;
	}
	

}
