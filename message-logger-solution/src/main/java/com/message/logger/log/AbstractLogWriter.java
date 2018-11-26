package com.message.logger.log;

import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.message.logger.model.persist.LogValues;

/**
 * Clase abstracta base que define los métodos a implementarse para escribir y
 * registrar mensaje de logs en una salida indicada por el cliente que lo
 * solicite. Adicionalmente, considera la implementación concreta de algunos
 * métodos utilitarios para validar y trabajar los {@link StreamHandler} que se
 * usen en los log. 
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
public abstract class AbstractLogWriter implements InterfaceLogWriter {
	
    @Autowired
    protected Environment environment;
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void writeMessage(LogValues logValues) throws Exception;
	
	/**
	 * Método para validar si la instancia de log ya tiene al menos un handler
	 * asignado.
	 * 
	 * @param logger instancia de logger que se está usando para imprimir mensajes
	 *               en las salidas solicitadas (consola o archivo)
	 * @return indicador que dice si se encontraron o no handlers en el logger
	 */
	protected boolean hasLoggerAssignHandlers(Logger logger) {
		
		boolean process = Boolean.FALSE;
		if (logger != null && logger.getHandlers() != null && logger.getHandlers().length > 0) {
			process = Boolean.TRUE;
		}

		return process;
		
	}
	
	/**
	 * Método que permite asignar un handler a un logger. Los tipos de handler que
	 * se pueden asignar son las descendientes de {@link StreamHandler}.
	 * 
	 * @param logger        instancia de logger que se está usando para imprimir
	 *                      mensajes en las salidas solicitadas (consola o archivo)
	 * @param streamHandler tipo de handler que se agregará al logger
	 */
	protected void assignHandlerToLogger(Logger logger, StreamHandler streamHandler) {
		logger.addHandler(streamHandler);
	}
	
}
