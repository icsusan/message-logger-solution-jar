package com.message.logger.model.persist;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.message.logger.common.MessageLoggerConstants;

/**
 * Clase que contiene la información y atributos necesarios para imprimir
 * mensajes de log en base de datos.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 * 
 */
@Entity
@Table(name = "LOG_VALUES")
public class LogValues {

	/**
	 * Atributo persistente para la llave primaria (identificador) de la tabla de
	 * mensajes. Sería numérico y de valor auto - incremental.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	/**
	 * Atributo persistente para el mensaje de texto para la descripción del mensaje
	 * de log.
	 */
    @NotNull
    @Size(min = 3, max = 250)
    private String description;
    
    /**
     * Atributo persistente para el tipo de mensaje de log.
     * 		- 1: Mensaje
     *  	- 2: Error 
     * 		- 3: Advertencia)
     */
    @NotNull
    private Integer logType;
    
    /**
     * Constructor por defecto
     */
    public LogValues() {
    	
    }

	/**
	 * Constructor con argumentos de datos persistentes
	 * 
	 * @param description mensaje de log
	 * @param logType tipo de mensaje (1: Mensaje || 2: Error || 3: Advertencia)
	 */
	public LogValues(@NotNull @Size(min = 3, max = 250) String description, @NotNull Integer logType) {
		this.description = description;
		this.logType = logType;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the logType
	 */
	public Integer getLogType() {
		return logType;
	}

	/**
	 * @param logType the logType to set
	 */
	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogValues [" 
				+ (id != null ? "id=" + id + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (logType != null ? "logType=" + logType : "") + "]";
	}

	
	/**
	 * Auxiliaries attributes and methods
	 */
	
	/**
	 * Atributo para indicar el tipo de nivel de log que tiene el objeto 
	 */
	@Transient
	private Level level;
	
	/**
	 * Atributo para identificar el tipo de mensaje de log que se va a usar
	 */
	@Transient
	private String descriptionLogType;
	
	/**
	 * Constructor con argumentos que son los valores recibidos desde el cliente que
	 * invoque al objeto. Con los cuales se pueden generar los valores de los
	 * atributos persistentes. Tales como armar el mensaje y obtener el tipo de log.
	 * También los atributos no persistentes que son de utilidad.
	 * 
	 * @param messageText      mensaje que envía el cliente que crea el objeto, será
	 *                         transformada para obtener el formato final
	 * @param message          indicador que dice si el mensaje es de tipo
	 *                         informativo (INFO / MESSAGE)
	 * @param warning          indicador que dice si el mensaje es de tipo
	 *                         advertencia (WARNING / WARNING)
	 * @param error            indicador que dice si el mensaje es de tipo error
	 *                         (SEVERE / ERROR)
	 */
	public LogValues(String messageText, boolean message, boolean warning, boolean error) {
		
		messageText.trim();
		
		//Asignando los valores a description
		assignLogTypeValues(message, warning, error);
		
		//Asignando los valores a descriptionLogType, level y logType
		assignDescriptionLogMessageText(messageText);
		
	}
	
	/**
	 * Método que sirve para asignar formato al mensaje de texto que se
	 * imprimirá/guardará en la salida de log solicitada. Al mensaje de ingreso
	 * recibido como parámetro, se le agregará la fecha y hora de ocurrencia
	 * completa, el texto, el tipo de mensaje.
	 * 
	 * @param messageText mensaje que se debe aplicar el formato indicado
	 */
	public void assignDescriptionLogMessageText(String messageText) {
		
		StringBuffer logMessageText = new StringBuffer("");
		logMessageText.append(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM).format(new Date()) + "  ")
				.append(descriptionLogType + "  ---- [logMessage] : ")
				.append(messageText);

		description = logMessageText.toString();
	}
	
	/**
	 * Método que ayuda verificar que tipo de mensaje se está solicitando
	 * imprimir/guardar, usando los indicadores finales que ha enviado el cliente.
	 * 
	 * @param message indicador que dice si el mensaje es de tipo informativo (INFO
	 *                / MESSAGE)
	 * @param warning indicador que dice si el mensaje es de tipo advertencia
	 *                (WARNING / WARNING)
	 * @param error   indicador que dice si el mensaje es de tipo error (SEVERE /
	 *                ERROR)
	 */
	public void assignLogTypeValues(boolean message, boolean warning, boolean error) {
		
		if (message) {
			descriptionLogType = MessageLoggerConstants.DESCRIPTION_MESSAGE_LOG_TYPE;
			level = Level.INFO;
			logType = MessageLoggerConstants.INDICATOR_MESSAGE_LOG_TYPE;
		} else if (error) {
			descriptionLogType = MessageLoggerConstants.DESCRIPTION_ERROR_LOG_TYPE;
			level = Level.SEVERE;
			logType = MessageLoggerConstants.INDICATOR_ERROR_LOG_TYPE;
		} else if (warning) {
			descriptionLogType = MessageLoggerConstants.DESCRIPTION_WARNING_LOG_TYPE;
			level = Level.WARNING;
			logType = MessageLoggerConstants.INDICATOR_WARNING_LOG_TYPE;
		}  
		
	}
	
	/**
	 * Método igual a toString() pero considerando los atributos no persistentes,
	 * para obtener la descripción completa de la instancia.
	 * 
	 * @return descripción completa de la instancia
	 */
	public String printFullObjectDescription() {
		return "LogValues [" 
				+ (id != null ? "id=" + id + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (logType != null ? "logType=" + logType + ", " : "") 
				+ (level != null ? "level=" + level + ", " : "")
				+ (descriptionLogType != null ? "descriptionLogType=" + descriptionLogType + ", " : "") + "]";
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the descriptionLogType
	 */
	public String getDescriptionLogType() {
		return descriptionLogType;
	}

	/**
	 * @param descriptionLogType the descriptionLogType to set
	 */
	public void setDescriptionLogType(String descriptionLogType) {
		this.descriptionLogType = descriptionLogType;
	}
	
}