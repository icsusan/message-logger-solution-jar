package com.message.logger.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.message.logger.model.persist.LogValues;

/**
 * Interface que especifica el contrato de métodos para el acceso y persistencia
 * de los datos de instancias de la clase {@link LogValues}. Implementa la
 * interface {@link JpaRepository} para heredar toda la funcionalidad necesaria
 * para trabajar con JPA.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 * 
 */
@Repository
@Transactional
public interface LogValuesDao extends JpaRepository<LogValues, Long>{

	/**
	 * Método que permite realizar la búsqueda de todos los objetos de tipo
	 * {@link LogValues} registrados en la base de datos.
	 * 
	 */
	public List<LogValues> findAll();
	
}
