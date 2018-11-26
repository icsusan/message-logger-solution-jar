package com.message.logger.log;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.message.logger.log.impl.ConsoleLogWriter;
import com.message.logger.log.impl.DatabaseLogWriter;
import com.message.logger.log.impl.FileLogWriter;
import com.message.logger.model.persist.LogValues;

/**
 * Clase que brinda toda la funcionalidad para realizar la impresión/registro de
 * mensaje de log en las 3 salidas que brinda: consola, archivo y base de datos.
 * El cliente que invoque la instancia debe indicar el tipo de log (mensaje,
 * advertencia y error), el texto del mensaje y la salida a la cual quiere
 * direccionar la impresión/registro los mensajes de log. Será ésta clase quien
 * leerá dicha información y determinará cuales son los componentes que
 * intervendrán para realizar la tarea solicitada. Es decir, será quien de
 * acuerdo a los parámetros recibidos, determine si se le está solicitando
 * imprimir en: 
 * 		- Consola, entonces usará el componente {@link ConsoleLogWriter}
 * 					para imprimir el mensaje de log en la consola 
 * 		- Archivo, entonces usará el componente {@link FileLogWriter} 
 * 					para imprimir el mensaje de log en un archivo 
 * 		- Base de datos, entonces usará el componente {@link DatabaseLogWriter} 
 * 					para guardar el mensaje de log en la base de datos
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
public class JobLogger {

	//Componente para imprimir el mensaje de log en la consola 
	@Autowired
	private ConsoleLogWriter consoleLogger;
	
	//Componente para imprimir el mensaje de log en un archivo 
	@Autowired
	private FileLogWriter fileLogger;
	
	//Componente para guardar el mensaje de log en la base de datos
	@Autowired
	private DatabaseLogWriter databaseLogger;
	
	//Indicador para saber si la instancia está configurada para imprimir el mensaje de log en la consola 
	private boolean logToConsole;
	
	//Indicador para saber si la instancia está configurada para imprimir el mensaje de log en un archivo
	private boolean logToFile;
	
	//Indicador para saber si la instancia está configurada para guardar el mensaje de log en la base de datos
	private boolean logToDatabase;

	//Indicador para saber si el mensaje de log es un mensaje informativo
	private boolean logMessage;
	
	//Indicador para saber si el mensaje de log es un mensaje de advertencia
	private boolean logWarning;
	
	//Indicador para saber si el mensaje de log es un mensaje de error
	private boolean logError;
	
	/**
	 * Constructor que sirve para asignar las configuraciones iniciales a la instancia.
	 * 
	 * @param logToFileParam indicador para saber si la instancia estará configurada para imprimir el mensaje de log en un archivo
	 * @param logToConsoleParam indicador para saber si la instancia estará configurada para imprimir el mensaje de log en la consola 
	 * @param logToDatabaseParam indicador para saber si la instancia estará configurada para guardar el mensaje de log en la base de datos
	 * @param logMessageParam indicador para saber si el mensaje de log será un mensaje informativo
	 * @param logWarningParam indicador para saber si el mensaje de log será un mensaje de advertencia
	 * @param logErrorParam indicador para saber si el mensaje de log será un mensaje de error
	 */
	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam) {
		
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		
	}
	
	/**
	 * Método que se brinda a los clientes que necesiten realizar la
	 * impresión/registro del mensaje de log para ejecutar dicha tarea. El cliente
	 * debe indicar que tipo de mensaje es (mensaje informativo, advertencia o
	 * error, de forma exclusiva, un mensaje no puede ser de los 3 tipos a la vez) y
	 * cual es el texto del mensaje.
	 * 
	 * @param messageText mensaje de log que envía el cliente
	 * @param message     indicador que envía el cliente que invoca la instancia,
	 *                    para determinar que el mensaje de log será un mensaje
	 *                    informativo
	 * @param warning     indicador que envía el cliente que invoca la instancia,
	 *                    para determinar que el mensaje será un mensaje de
	 *                    advertencia
	 * @param error       indicador que envía el cliente que invoca la instancia,
	 *                    para determinar que el mensaje de log será un mensaje de
	 *                    error
	 * @throws Exception cualquier excepción que podría ocurrir en el proceso
	 */
	public void writeLogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
		
		//Se valida que el mensaje es válido y tenga contenido
		if (StringUtils.isBlank(messageText)) {
			throw new Exception("Invalid content");
		}

		//Se debe haber especificado al menos un tipo de salida válido
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		
		/*
		 * Se debe haber indicado que el mensaje es de al menos uno de los 3 tipos
		 * determinados. Adicionalmente se está considerando que son los parámetros
		 * message, warning y error los que determinarán firnalmente el tipo de mensaje
		 * que se desea imprimir pues son los finalmente necesita el cliente invocador y
		 * logError, logMessage, logWarning se consideran como configuración inicial.
		 */
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}
		
		//Se solicita construir la instancia del objeto con los datos listos para imprimir/registrar 
		LogValues logValues = new LogValues(messageText, message, warning, error);
		
		/*
		 * Se procede a verificar que tipo de salidas se ha habilitado, podrían ser las
		 * 3 disponibles al mismo tiempo
		 * 
		 */
		
		//Si se ha configurado la salida de consola, se procede a llamar al componente
		if (logToConsole) {
			consoleLogger.writeMessage(logValues);
		}

		//Si se ha configurado la salida de archivo, se procede a llamar al componente
		if (logToFile) {
			fileLogger.writeMessage(logValues);
		}
		
		//Si se ha configurado la salida de base de datos, se procede a llamar al componente
		if (logToDatabase) {
			databaseLogger.writeMessage(logValues);
		}
		
	}

	/**
	 * @return the logToConsole
	 */
	public boolean isLogToConsole() {
		return logToConsole;
	}

	/**
	 * @param logToConsole the logToConsole to set
	 */
	public void setLogToConsole(boolean logToConsole) {
		this.logToConsole = logToConsole;
	}

	/**
	 * @return the logToFile
	 */
	public boolean isLogToFile() {
		return logToFile;
	}

	/**
	 * @param logToFile the logToFile to set
	 */
	public void setLogToFile(boolean logToFile) {
		this.logToFile = logToFile;
	}

	/**
	 * @return the logToDatabase
	 */
	public boolean isLogToDatabase() {
		return logToDatabase;
	}

	/**
	 * @param logToDatabase the logToDatabase to set
	 */
	public void setLogToDatabase(boolean logToDatabase) {
		this.logToDatabase = logToDatabase;
	}

	/**
	 * @return the logMessage
	 */
	public boolean isLogMessage() {
		return logMessage;
	}

	/**
	 * @param logMessage the logMessage to set
	 */
	public void setLogMessage(boolean logMessage) {
		this.logMessage = logMessage;
	}

	/**
	 * @return the logWarning
	 */
	public boolean isLogWarning() {
		return logWarning;
	}

	/**
	 * @param logWarning the logWarning to set
	 */
	public void setLogWarning(boolean logWarning) {
		this.logWarning = logWarning;
	}

	/**
	 * @return the logError
	 */
	public boolean isLogError() {
		return logError;
	}

	/**
	 * @param logError the logError to set
	 */
	public void setLogError(boolean logError) {
		this.logError = logError;
	}

}
