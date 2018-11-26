package com.message.logger.util;

import java.io.File;

/**
 * Clase con métodos utilitarios para manejar instancias de archivos y carpetas
 * del sistema.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 * 
 */
public final class MyFileUtil {

	/**
	 * Método que permite crear una nueva carpeta en una ubicación dada. Además de
	 * crear la nueva carpeta, también se retorna el path completo de la nueva
	 * carpeta creada, incluyendo el separador de carpetas usado en el sistema
	 * operativo. Por ejemplo: F:\workspace\logger\
	 * 
	 * @param directoryPath    path de la carpeta donde se creará la nueva carpeta
	 * @param nameNewDirectory nombre de la nueva carpeta que se creará
	 * @param fileSeparator    separador de carpetas del sistema operativo
	 * @return path completo de la nueva carpeta
	 */
	public static final String createNewDirectory(String directoryPath, String nameNewDirectory, String fileSeparator) {
		
		//Armando la ruta de la nueva carpeta, usando como base la carpeta brindada
		String newDirectoryPath = directoryPath + fileSeparator + nameNewDirectory  + fileSeparator;
		
		//Si la nueva carpeta no existe, se crea con permisos de escritura
		File newFolderDir = new File(newDirectoryPath);
		if (!newFolderDir.exists()) {
			newFolderDir.mkdir();
			newFolderDir.setWritable(Boolean.TRUE);
		}
		
		//Se retorna la ruta de la nueva carpeta
		return newDirectoryPath;
		
	}

	/**
	 * Método que retorna la ruta de la carpeta anterior a la carpeta actual,
	 * indicada con parámetro.
	 * 
	 * @param currentDirectoryPath carpeta actual
	 * @return ruta de la carpeta padre (anterior) a la carpeta actual
	 */
	public static final String getParentDirectoryPathFromCurrentDirectory(String currentDirectoryPath) {
		
		File currentDirectory = new File(currentDirectoryPath);
		
		File parentDirectory = new File(currentDirectory.getParent());
		
		String parentDirectoryPath = parentDirectory.getAbsolutePath();
		
		return parentDirectoryPath;
		
	}
	
}
