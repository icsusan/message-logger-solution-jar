package com.message.logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.message.logger.log.JobLogger;

/**
 * Clase principal del sistema. Desde esta clase de levantará el contexto de
 * Spring. Y también se realizará la captura de información que el usuario
 * ingrese para ejecutar la funcionalidad de impresión de mensajes de logs.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 */
@SpringBootApplication
public class MessageLoggerSolutionApplication {

	private static final Logger LOGGER = Logger.getLogger(MessageLoggerSolutionApplication.class.getName());

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(MessageLoggerSolutionApplication.class, args);

		String messageTextIn = null;
		String messageTypeIn = null;
		String outputSelectedIn = null;
		BufferedReader br = null;
		
		try {
			
			//Imprimiendo el mensaje de aviso de inicio de ingreso de valores
			printStartInputValue();
			
			//Solicitando el ingreso del mensaje a imprimir
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("**** Ingrese el mensaje que desea imprimir (por ejemplo 'Este es un mensaje para la consola' | Máximo 250 caracteres): ");
	        messageTextIn = br.readLine();
	        
	        //Validando el texto ingresado, si no es válido, el programa lo notificará y se cerrará
	        messageTextIn = validateMessageTextInput(messageTextIn);
	        
			//Solicitando el ingreso del tipo de mensaje a imprimir
	        System.out.println("**** Indique el tipo de mensaje (E: Error, A: Advertencia, M: Mensaje Informativo): ");
	        messageTypeIn = br.readLine();
	        
	        //Validando el tipo de mensaje ingresado, si no es válido, el programa lo notificará y se cerrará
	        messageTypeIn = validateMessageTypeInput(messageTypeIn);
			
	        //Solicitando el ingreso de las salidas
	        System.out.println("**** Indique las opciones de impresión (C: Consola, A: Archivo, B: Base de datos, puede elegir varias a la vez, por ejemplo CAB, BA, etc.): ");
	        outputSelectedIn = br.readLine();
	        
	        //Validando las opciones ingresadas, si no son válidas, el programa lo notificará y se cerrará
	        outputSelectedIn = validateOutputSelectedInput(outputSelectedIn);
	        
			/*
			 * Imprimiendo el mensaje de aviso de fin de ingreso de valores, si llegó aquí
			 * todos los valores ingresados son correctos
			 */
	        printEndInputValue();
	        
	        //Asignando el valor del mensaje de texto capturado al atributo que irá al bean de impresión
	        String messageText =  messageTextIn;
	        
	        //Asignando los valores de tipo de mensaje, de acuerdo a lo capturado
	        boolean message = Boolean.FALSE;
	        boolean warning = Boolean.FALSE;
	        boolean error = Boolean.FALSE;
	        
	        //Si se capturó una letra "E", el usuario solicitó un mensaje de tipo error
	        if (messageTypeIn.equalsIgnoreCase("M")) {
	        	message = Boolean.TRUE;	        
	        } else if (messageTypeIn.equalsIgnoreCase("A")) {
	        	
	        	//Si se capturó una letra "A", el usuario solicitó un mensaje de tipo advertencia
	        	warning = Boolean.TRUE;
	        	
	        } else if (messageTypeIn.equalsIgnoreCase("E")) {
	        	
	        	//Si se capturó una letra "M", el usuario solicitó un mensaje de tipo informativo
	        	error = Boolean.TRUE;
	        }
	        
	        //Asignando los valores de tipo de salida, de acuerdo a lo capturado
	        boolean console = Boolean.FALSE;
	        boolean file = Boolean.FALSE;
	        boolean database = Boolean.FALSE;
	        
	        //Si el texto contiene una letra "C", el usuario solicitó imprimir el mensaje en la consola
	        if (outputSelectedIn.contains("C")) {
	        	console = Boolean.TRUE;
	        } 
	        
	        //Si el texto contiene una letra "A", el usuario solicitó imprimir el mensaje en un archivo
	        if (outputSelectedIn.contains("A")) {
	        	file = Boolean.TRUE;
	        }
	        
	        //Si el texto contiene una letra "B", el usuario solicitó guardar el mensaje en una base de datos 
	        if (outputSelectedIn.contains("B")) {
	        	database = Boolean.TRUE;
	        } 
	        
	        //Se recupera el bean desde el contenedor de Spring
			JobLogger jobLogger = context.getBean(JobLogger.class);
			
			//Se asignan los valores de configuración para determinar que tipos de salida se han pedido
			jobLogger.setLogToConsole(console);
			jobLogger.setLogToFile(file);
			jobLogger.setLogToDatabase(database);
			
			//Se invoca al método de impresión con los demás valores capturados
			jobLogger.writeLogMessage(messageText, message, warning, error);
			
		} catch (Exception e) {
			
			LOGGER.severe("Ha ocurrido un error inesperado en la aplicación. "
					+ "Por favor, comuniquese con el administrador del sistema.");
			
		} finally {

			context.close();
			
		}
		
	}

	/**
	 * Método para validar el texto de ingreso. Se debe validar que no sea ni nulo,
	 * ni vacío, ni blanco. Asimismo, se debe validar que cuando menos tenga al
	 * menos las letras C, A y B en su contenido. Si no cumple alguna de estas
	 * condiciones el programa notificará el error y se cerrará.
	 * 
	 * @param outputSelectedIn texto de ingreso, capturado desde la consola de
	 *                         ingreso
	 */
	private static String validateOutputSelectedInput(String outputSelectedIn) {
		
		outputSelectedIn = outputSelectedIn.toUpperCase();
		if (StringUtils.isBlank(outputSelectedIn)) {
			System.out.println("ERROR: Ha ingresado un valor inválido.");
			printEndInputValue();
			System.exit(0);
		} else if (!outputSelectedIn.contains("C") && !outputSelectedIn.contains("A") && !outputSelectedIn.contains("B")) {
			System.out.println("ERROR: El valor ingresado no contiene ninguna opción válida.");
			printEndInputValue();
			System.exit(0);
		}
		
		return outputSelectedIn;
		
	}

	/**
	 * Método para validar el texto de ingreso. Se debe validar que no sea ni nulo,
	 * ni vacío, ni blanco. Asimismo, se debe validar que lo ingresado sea una letra
	 * E, A o M, exactamente, minúsculas o mayúsculas, pero únicamente una de esas
	 * letras. Si no cumple alguna de estas condiciones el programa notificará el
	 * error y se cerrará.
	 * 
	 * @param messageTypeIn texto de ingreso, capturado desde la consola de ingreso
	 */
	private static String validateMessageTypeInput(String messageTypeIn) {
		
		messageTypeIn = messageTypeIn.toUpperCase();
		if (StringUtils.isBlank(messageTypeIn)) {
			System.out.println("ERROR: Ha ingresado un valor inválido.");
			printEndInputValue();
			System.exit(0);
		} else  if (!messageTypeIn.equalsIgnoreCase("E") 
				&& !messageTypeIn.equalsIgnoreCase("A") 
				&& !messageTypeIn.equalsIgnoreCase("M")) {
			System.out.println("ERROR: El valor ingresado no contiene ninguna opción válida.");
			printEndInputValue();
			System.exit(0);
		}
		return messageTypeIn;
	}

	/**
	 * Método para validar el texto de ingreso. Se debe validar que no sea ni nulo,
	 * ni vacío, ni blanco. Si no cumple alguna de estas condiciones el programa
	 * notificará el error y se cerrará. Asimismo, se debe validar que lo ingresado
	 * no sobrepase los 250 caracteres. Si se sobrepasa, se notificará que se
	 * recortará el mensaje en 250 caracteres.
	 * 
	 * @param messageTextIn texto de ingreso, capturado desde la consola de ingreso
	 * @return se retorna la cadena original o recortada si se sobrepasaron los 250
	 *         caracteres
	 */
	private static String validateMessageTextInput(String messageTextIn) {
		if (StringUtils.isBlank(messageTextIn)) {
			System.out.println("ERROR: Ha ingresado un valor inválido.");
			printEndInputValue();
			System.exit(0);
		} else  if (messageTextIn.length() > 250) {
			System.out.println("ADVERTENCIA: El texto ingresado supera los 250 caracteres. Se recortará el texto.");
			printEndInputValue();
			messageTextIn = messageTextIn.substring(0, 250);
		}
		return messageTextIn;
	}
	
	/**
	 * Método para imprimir señalización de fin de la zona de ingreso de valores
	 */
	private static void printEndInputValue() {
		System.out.println("");
		System.out.println("");
		System.out.println("******************** FIN DE INGRESO DE VALORES ********************");
		System.out.println("*******************************************************************");
		System.out.println("");
		System.out.println("");
	}

	/**
	 * Método para imprimir señalización de inicio de la zona de ingreso de valores
	 */
	private static void printStartInputValue() {
		System.out.println("");
		System.out.println("");
		System.out.println("**********************************************************************");
		System.out.println("******************** INICIO DE INGRESO DE VALORES ********************");
		System.out.println("");
		System.out.println("");
	}
}
