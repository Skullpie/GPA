package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.MotifIndisponibilite;
import dal.ConnectionProvider;
import dal.MotifDAO;
import exceptions.DAOException;

public class MotifDAOJdbc implements MotifDAO {
	
	private static final String LISTE_MOTIF = "SELECT id_motif_indisponibilite, intitule FROM MOTIFS_INDISPONIBILITE ORDER BY intitule;";
	private static final String GET_MOTIF = "SELECT id_motif_indisponibilite, intitule FROM MOTIFS_INDISPONIBILITE WHERE id_motif_indisponibilite = ? ;";
	private static final String INSERT_MOTIF = "INSERT INTO MOTIFS_INDISPONIBILITE (intitule) VALUES (?) ;";

	@Override
	public MotifIndisponibilite selectById(int id) throws DAOException {

		MotifIndisponibilite m = null;
		
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_MOTIF);	){
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					m = new MotifIndisponibilite();
					m.setId(rs.getInt("id_motif_indisponibilite"));
					m.setIntitule(rs.getString("intitule"));
					
				}
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Echec de la récupération d'un motif", e);
			}
		return m;
	}

	@Override
	public List<MotifIndisponibilite> selectAll() throws DAOException {
		List<MotifIndisponibilite> lst = new ArrayList<MotifIndisponibilite>();
		
		try(Connection cnx = ConnectionProvider.getConnection();
			Statement stmt = cnx.createStatement();	){
			
			ResultSet rs = stmt.executeQuery(LISTE_MOTIF);
			MotifIndisponibilite m = null;
			
			while(rs.next()) {
				m = new MotifIndisponibilite();
				m.setId(rs.getInt("id_motif_indisponibilite"));
				m.setIntitule(rs.getString("intitule"));
				
				lst.add(m);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Motif>", e);
		}
		
		return lst;
	}

	@Override
	public void delete(MotifIndisponibilite u) throws DAOException {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void update(MotifIndisponibilite u) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(MotifIndisponibilite u) throws DAOException {
		
		
		try(Connection cnx = ConnectionProvider.getConnection();
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_MOTIF);){

			pstmt.setString(1, u.getIntitule());
		
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'insertion du motif", e);	
		}
	}

}
