package com.message.logger.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.message.logger.dao.LogValuesDao;
import com.message.logger.log.AbstractLogWriter;
import com.message.logger.model.persist.LogValues;

/**
 * Clase que implementa los métodos definidos en {@link AbstractLogWriter}, con
 * el objetivo de guardar el mensaje de log en la base de datos.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
@Component
public class DatabaseLogWriter extends AbstractLogWriter {

	@Autowired
	private LogValuesDao logValuesDao;

	/**
	 * Método para guardar un mensaje de log en la base de datos. Los datos
	 * a guardar se obtienen del parámetro de ingreso.
	 * 
	 * @param logValues objeto con los valores (descripción, tipo de mensaje, etc)
	 *                  que se enviarán a la base de datos
	 * @throws Exception cualquier excepción que podría ocurrir en el proceso
	 */
	@Override
	public void writeMessage(LogValues logValue) throws Exception {
		
		//Se usa el método save del objeto de acceso a base de datos para guardar el mensaje
		logValuesDao.save(logValue);
		
	}

}
