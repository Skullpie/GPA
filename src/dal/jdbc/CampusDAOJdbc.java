package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Campus;
import dal.CampusDAO;
import dal.ConnectionProvider;
import exceptions.DAOException;

public class CampusDAOJdbc implements CampusDAO {



		private static final String LISTE_CAMPUS = "SELECT id_lieu, intitule FROM LIEUX WHERE code is not null ORDER BY intitule;";
		private static final String GET_CAMPUS = "SELECT id_lieu, intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps, code FROM LIEUX WHERE id_lieu = ? ;";
		private static final String INSERTCAMPUS = "INSERT INTO LIEUX  (intitule, rue, code_postal, ville, information_complementaire, latitude_gps, longitude_gps, code) VALUES (?,?,?,?,?,?,?,?) ;";
		private static final String UPDATE = "UPDATE LIEUX SET  intitule = ?, rue = ?, code_postal = ?, ville = ?, information_complementaire = ?, latitude_gps = ?, longitude_gps = ?, code = ? WHERE id_lieu = ?;";
		private static final String DELETE_CAMPUS = "DELETE FROM LIEUX WHERE id_lieu = ? ;";
		
		@Override
		public List<Campus> selectAll() throws DAOException {
			List<Campus> lst = new ArrayList<Campus>();
			
			try(Connection cnx = ConnectionProvider.getConnection();
				Statement stmt = cnx.createStatement();	){
				
				ResultSet rs = stmt.executeQuery(LISTE_CAMPUS);
				Campus c = null;
				
				while(rs.next()) {
					c = new Campus();
					c.setId(rs.getInt("id_lieu"));
					c.setIntitule(rs.getString("intitule"));
					
					lst.add(c);
				}
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Echec de la recupération list<Lieu>", e);
			}
			
			return lst;
		}

		@Override
		public Campus selectById(int id) throws DAOException {

			Campus c = null;
			
			try(Connection cnx = ConnectionProvider.getConnection();
					PreparedStatement pstmt = cnx.prepareStatement(GET_CAMPUS);	){
					pstmt.setInt(1, id);
					
					ResultSet rs = pstmt.executeQuery();
					
					if(rs.next()) {
						c = new Campus();
						c.setId(rs.getInt("id_lieu"));
						c.setIntitule(rs.getString("intitule"));
						c.setRue(rs.getString("rue"));
						c.setCodePostal(rs.getString("code_postal"));
						c.setVille(rs.getString("ville"));
						c.setInformationComplementaire(rs.getString("information_complementaire"));
						c.setLatitude(rs.getString("latitude_gps"));
						c.setLongitude(rs.getString("longitude_gps"));
						c.setCode(rs.getString("code"));
						
					}
					
					rs.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException("Echec de la récupération d'un lieu", e);
				}
			return c;
		}

		@Override
		public void delete(Campus u) throws DAOException {
			try (Connection cnx = ConnectionProvider.getConnection();
					PreparedStatement pstmt = cnx.prepareStatement(DELETE_CAMPUS)) {

				pstmt.setInt(1, u.getId());
				pstmt.executeUpdate();


			} catch (SQLException e) {
				throw new DAOException("Echec de la suppression", e);
			}
			
		}

		@Override
		public void update(Campus u) throws DAOException {
			try (Connection cnx = ConnectionProvider.getConnection();
					PreparedStatement pstmt = cnx.prepareStatement(UPDATE);) {

				pstmt.setString(1, u.getIntitule());
				pstmt.setString(2, u.getRue());
				pstmt.setString(3, u.getCodePostal());
				pstmt.setString(4, u.getVille());
				pstmt.setString(5, u.getInformationComplementaire());
				pstmt.setString(6, u.getLatitude());
				pstmt.setString(7, u.getLongitude());
				pstmt.setString(8, u.getCode());
				pstmt.setInt(9, u.getId());

				pstmt.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DAOException("Echec de l'update du campus", e);
			}
			
		}
		@Override
		public void insert(Campus u) throws DAOException {
			
			
			try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERTCAMPUS);){

				pstmt.setString(1, u.getIntitule());
				pstmt.setString(2, u.getRue());
				pstmt.setString(3, u.getCodePostal());
				pstmt.setString(4, u.getVille());
				pstmt.setString(5, u.getInformationComplementaire());
				pstmt.setString(6, u.getLatitude());
				pstmt.setString(7, u.getLongitude());
				pstmt.setString(8, u.getCode());
			
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DAOException("Echec de l'insertion du Campus", e);	
			}
		}
}
