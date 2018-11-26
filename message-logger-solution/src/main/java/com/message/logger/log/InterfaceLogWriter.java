package com.message.logger.log;

import com.message.logger.model.persist.LogValues;

/**
 * Interface base que define los métodos a implementarse para escribir y
 * registrar mensaje de logs en una salida indicada por el cliente que lo
 * solicite.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
public interface InterfaceLogWriter {

	/**
	 * Método que debe permitir imprimir y/o guardar un mensaje en la salida de log
	 * que se indique.
	 * 
	 * @param logValues objeto con los valores (descripción, tipo de mensaje, etc)
	 *                  que se enviarán a la salida solicitada
	 * @throws Exception cualquier excepción que podría ocurrir en el proceso
	 */
	public void writeMessage(LogValues logValues) throws Exception;

}
