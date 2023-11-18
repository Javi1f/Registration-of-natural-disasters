package co.edu.unbosque.services;

import java.util.List;

public interface CRUDOperations<T> {

	public int create(T data);

	public List<T> getAll();

	public int deleteById(Long id);

	public int updateById(Long id, T newData);

	public long count();

	public boolean exists(Long id);
}
