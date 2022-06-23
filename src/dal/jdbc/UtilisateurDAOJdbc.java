package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Utilisateur;
import dal.ConnectionProvider;
import dal.UtilisateurDAO;
import exceptions.DAOException;




public class UtilisateurDAOJdbc implements UtilisateurDAO {

	private final static String AUTH_QUERY = "SELECT id_utilisateur,email,role FROM UTILISATEURS WHERE pseudo = ? and mot_passe = ? ;";
	private static final String LISTE_UTILISATEUR = "SELECT id_utilisateur, pseudo, email, mot_passe, role FROM UTILISATEURS;";
	private static final String GET_UTILISATEUR = "SELECT id_utilisateur, pseudo, email, mot_passe, role FROM UTILISATEURS WHERE id_utilisateur = ?;";
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, email, mot_passe, role) VALUES (?,?,?,?) ;";
	private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET  pseudo = ?, email = ?, mot_passe = ?, role = ? WHERE id_utilisateur = ?;";
	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE id_utilisateur = ? ;";
	
	@Override
	public Utilisateur authenticate(String pseudo, String motDePasse) throws DAOException {
		Utilisateur u = null;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pstmt = cnx.prepareStatement(AUTH_QUERY);
			pstmt.setString(1, pseudo);
			pstmt.setString(2, motDePasse);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				u = new Utilisateur(rs.getInt("id_utilisateur"),
									pseudo,
									rs.getString("email"), 
									motDePasse,
									rs.getString("role"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'autenthification", e);
			
		}
		
		
		return u;
	}

	@Override
	public Utilisateur selectById(int id) throws DAOException {
Utilisateur u = null;
		
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(GET_UTILISATEUR);	){
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					u = new Utilisateur();
					u.setId(rs.getInt("id_utilisateur"));
					u.setPseudo(rs.getString("pseudo"));
					u.setEmail(rs.getString("email"));
					u.setMotDePasse(rs.getString("mot_passe"));
					u.setRole(rs.getString("role"));
				}
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Echec de la récupération de l'utilisateur", e);
			}
		return u;
	}

	@Override
	public List<Utilisateur> selectAll() throws DAOException {
		List<Utilisateur> lst = new ArrayList<Utilisateur>();
		
		try(Connection cnx = ConnectionProvider.getConnection();
			Statement stmt = cnx.createStatement();	){
			
			ResultSet rs = stmt.executeQuery(LISTE_UTILISATEUR);
			Utilisateur u = null;
			
			while(rs.next()) {
				u = new Utilisateur();
				u.setId(rs.getInt("id_utilisateur"));
				u.setPseudo(rs.getString("pseudo"));
				u.setEmail(rs.getString("email"));
				u.setMotDePasse(rs.getString("mot_passe"));
				u.setRole(rs.getString("role"));
				
				lst.add(u);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Echec de la recupération list<Utilisateur>", e);
		}
		
		return lst;
	}

	@Override
	public void delete(Utilisateur u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR)) {

			pstmt.setInt(1, u.getId());
			pstmt.executeUpdate();


		} catch (SQLException e) {
			throw new DAOException("Echec de la suppression", e);
		}	
		
	}

	@Override
	public void update(Utilisateur u) throws DAOException {
		try (Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR);) {

			pstmt.setString(1, u.getPseudo());
			pstmt.setString(2, u.getEmail());
			pstmt.setString(3, u.getMotDePasse());
			pstmt.setString(4, u.getRole());
			pstmt.setInt(5, u.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Echec de l'update de l'utilisateur", e);
		}
		
	}

	@Override
	public void insert(Utilisateur u) throws DAOException {
		try(Connection cnx = ConnectionProvider.getConnection();
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_UTILISATEUR);){

				pstmt.setString(1, u.getPseudo());
				pstmt.setString(2, u.getEmail());
				pstmt.setString(3, u.getMotDePasse());
				pstmt.setString(4, u.getRole());
			
				pstmt.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DAOException("Echec de l'insertion de l'utilisateur", e);	
			}
		}

	
}
