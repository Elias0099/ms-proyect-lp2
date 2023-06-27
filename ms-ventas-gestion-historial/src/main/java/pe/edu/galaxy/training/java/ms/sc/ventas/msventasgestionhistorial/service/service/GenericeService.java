package pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.service;

import java.util.List;
import java.util.Optional;

import pe.edu.galaxy.training.java.ms.sc.ventas.msventasgestionhistorial.service.exception.ServiceException;

public interface GenericeService<T> {
	
	List<T> findByLike(T t) throws ServiceException;
	
	Optional<T> findById(T id)throws ServiceException;
	
	T save(T t) throws ServiceException;
	
	T update(Long id, T t) throws ServiceException;
	
	void delete(Long id) throws ServiceException;


}
