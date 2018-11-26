package com.message.logger.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.message.logger.log.JobLogger;

/**
 * Clase que permite declarar métodos de tipo @Bean, de tal forma de indicar al
 * contenedor de Spring formas personalizadas de instanciar los beans en tiempo
 * de ejecución.
 * 
 * <p>
 * Generado por @author <a href="ic.susan@gmail.com">Susan Inga</a> el
 * 23/11/2018
 * </p>
 *
 */
@Configuration
public class InitializerBeans {

	/**
	 * Método para indicar la estrategia de inicialización del bean
	 * {@link JobLogger}
	 * 
	 * @return instancia de {@link JobLogger} para el contener de Spring
	 */
	@Bean
	public JobLogger jobLogger() {
		
		return new JobLogger(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, 
				Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
	}

}
