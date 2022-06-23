package dal;

import java.util.List;

import exceptions.DAOException;


public interface GeneriqueDAO<T> {
	public T selectById(int id) throws DAOException; 	
	public List<T> selectAll() throws DAOException;
	public void delete(T u) throws DAOException;
	public void update(T u) throws DAOException;
	public void insert(T u) throws DAOException;
}
