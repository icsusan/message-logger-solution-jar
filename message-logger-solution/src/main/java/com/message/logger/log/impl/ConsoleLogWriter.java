package com.message.logger.log.impl;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.message.logger.log.AbstractLogWriter;
import com.message.logger.model.persist.LogValues;

/**
 * Clase que implementa los métodos definidos en {@link AbstractLogWriter}, con
 * el objetivo de realizar la impresión de mensaje de log en la salida de la Consola.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
@Component
public class ConsoleLogWriter extends AbstractLogWriter {
	
	/**
	 * Instancia de log propia del objeto
	 */
	private static final Logger LOGGER = Logger.getLogger(ConsoleLogWriter.class.getName());

	/**
	 * Método para imprimir un mensaje de log en la salida de la Consola. Los datos
	 * a imprimir se obtienen del parámetro de ingreso.
	 * 
	 * @param logValues objeto con los valores (descripción, tipo de mensaje, etc)
	 *                  que se enviarán a la Consola
	 * @throws Exception cualquier excepción que podría ocurrir en el proceso
	 */
	@Override
	public void writeMessage(LogValues logValues) throws Exception {
		
		//Se verifica si hay handlers asignado al logger, si no tiene se asigna uno de tipo Consola
		if (!hasLoggerAssignHandlers(LOGGER)) {
			assignHandlerToLogger(LOGGER, new ConsoleHandler());
		}
		
		//Se imprime el mensaje de log en la consola
		LOGGER.log(logValues.getLevel(), logValues.getDescription());
		
	}

}
