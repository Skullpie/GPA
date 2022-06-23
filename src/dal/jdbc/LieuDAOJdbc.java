package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Lieu;
import dal.ConnectionProvider;
import dal.LieuDAO;
import exceptions.DAOException;


public class LieuDAOJdbc implements LieuDAO {

	private static final String LISTE_LIEU = "SELECT id_lieu, intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps FROM LIEUX WHERE code is null;";
	private static final String GET_LIEU = "SELECT id_lieu, intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps FROM LIEUX WHERE id_lieu = ? ;";
	private static final String INSERTLIEU = "INSERT INTO LIEUX  (intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps) VALUES (?,?,?,?,?,?,?) ;";
	private static final String UPDATE = "UPDATE LIEUX SET  intitule = ?, rue = ?, code_postal = ?, ville = ?, information_complementaire = ?, latitude_gps = ?, longitude_gps = ? WHERE id_lieu = ?;";
	private static final String DELETE_LIEU = "DELETE FROM LIEUX WHERE id_lieu = ? ;";
	
	@Override
	public List<Lieu> selectAll() throws DAOException {
		List<Lieu> lst = new ArrayList<Lieu>();
		
		try(Connection cnx = ConnectionProvider.getConnection();
			Statement stmt = cnx.createStatement();	){
			
			ResultSet rs = stmt.executeQuery(LISTE_LIEU);
			Lieu l = null;
			
			while(rs.next()) {
				l = new Lieu();
				l.setId(rs.getInt("id_lieu"));
				l.setIntitule(rs.getString("intitule"));
				l.setRue(rs.getString("rue"));
				l.setCodePostal(rs.getString("code_postal"));
				l.setVille(rs.getString("ville"));
				l.setInformationComplementaire(rs.getString("information_complementaire"));
				l.setLatitude(rs.getString("latitude_gps"));
				l.setLongitude(rs.getString("longitude_gps"));
				
				
				
				lst.add(l);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Lieu>", e);
		}
		
		return lst;
	}

	@Override
	public Lieu selectById(int id) throws DAOException {

		Lieu lieu = null;
		
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_LIEU);	){
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					lieu = new Lieu();
					lieu.setId(rs.getInt("id_lieu"));
					lieu.setIntitule(rs.getString("intitule"));
					lieu.setRue(rs.getString("rue"));
					lieu.setCodePostal(rs.getString("code_postal"));
					lieu.setVille(rs.getString("ville"));
					lieu.setInformationComplementaire(rs.getString("information_complementaire"));
					lieu.setLatitude(rs.getString("latitude_gps"));
					lieu.setLongitude(rs.getString("longitude_gps"));
				}
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Echec de la récupération d'un lieu", e);
			}
		return lieu;
	}

	@Override
	public void delete(Lieu u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_LIEU)) {

			pstmt.setInt(1, u.getId());
			pstmt.executeUpdate();


		} catch (SQLException e) {
			throw new DAOException("Echec de la suppression", e);
		}		
	}

	@Override
	public void update(Lieu u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE);) {

			pstmt.setString(1, u.getIntitule());
			pstmt.setString(2, u.getRue());
			pstmt.setString(3, u.getCodePostal());
			pstmt.setString(4, u.getVille());
			pstmt.setString(5, u.getInformationComplementaire());
			pstmt.setString(6, u.getLatitude());
			pstmt.setString(7, u.getLongitude());
			pstmt.setInt(8, u.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'update du lieu", e);
		}
		
	}

	@Override
	public void insert(Lieu u) throws DAOException {
		
		
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(INSERTLIEU);){

			pstmt.setString(1, u.getIntitule());
			pstmt.setString(2, u.getRue());
			pstmt.setString(3, u.getCodePostal());
			pstmt.setString(4, u.getVille());
			pstmt.setString(5, u.getInformationComplementaire());
			pstmt.setString(6, u.getLatitude());
			pstmt.setString(7, u.getLongitude());
		
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion du Lieu", e);	
		}
	}

}
