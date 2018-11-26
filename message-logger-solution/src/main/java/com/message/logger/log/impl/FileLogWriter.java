package com.message.logger.log.impl;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.message.logger.log.AbstractLogWriter;
import com.message.logger.model.persist.LogValues;
import com.message.logger.util.MyFileUtil;

/**
 * Clase que implementa los métodos definidos en {@link AbstractLogWriter}, con
 * el objetivo de realizar la impresión de mensaje de log en un archivo con ruta y nombre
 * indicados.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
@Component
public class FileLogWriter extends AbstractLogWriter {

	/**
	 * Instancia de log propia del objeto
	 */
	private static final Logger LOGGER = Logger.getLogger(FileLogWriter.class.getName());

	/**
	 * Método para imprimir un mensaje de log en un archivo de mensaje de log. Los
	 * datos a imprimir se obtienen del parámetro de ingreso.
	 * 
	 * El archivo de log se imprimirá en una nueva carpeta que se creará a la misma
	 * altura de la carpeta del proyecto.
	 * 
	 * Asimismo, se considera que el contenido será de tipo synchronized debido a
	 * que múltiples peticiones podría llegar al mismo tiempo y dado que el archivo
	 * es único, se podría producir una compentencia por el recurso. De esta forma
	 * sólo se podrá acceder al archivo de log de forma segura.
	 * 
	 * @param logValues objeto con los valores (descripción, tipo de mensaje, etc)
	 *                  que se enviarán al archivo de salida
	 * @throws Exception cualquier excepción que podría ocurrir en el proceso
	 */
	@Override
	public void writeMessage(LogValues logValues) throws Exception {
		
		synchronized (logValues) {
			
			//Se obtiene la ruta del directorio padre, para poder crear la nueva carpeta en ella
			String parentDirectoryPath = MyFileUtil.getParentDirectoryPathFromCurrentDirectory(System.getProperty("user.dir"));
			
			//Se genera la ruta de la nueva carpeta que se creará 
			String newDirectoryPath = MyFileUtil.createNewDirectory(parentDirectoryPath, environment.getProperty("message.logger.log.directory.name"), System.getProperty("file.separator"));
			
			//Con la nueva ruta obtenida, se agrega el nombre del archivo de log para crearlo
			String logFilePath = newDirectoryPath + environment.getProperty("message.logger.log.file.name");

			//Se verifica si el archivo de log ya existe. Sino existe, se crea
			File file = new File(logFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			//Se verifica si hay handlers asignado al logger, si no tiene se asigna uno de tipo Archivo
			if (!hasLoggerAssignHandlers(LOGGER)) {
				assignHandlerToLogger(LOGGER, new FileHandler(logFilePath, Boolean.TRUE));
			}
			
			//Se imprime el mensaje de log en la consola
			LOGGER.log(logValues.getLevel(), logValues.getDescription());
			
		}
		
	}

}
