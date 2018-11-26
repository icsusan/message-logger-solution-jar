package com.message.logger.model.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.Level;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.message.logger.common.MessageLoggerConstants;
import com.message.logger.model.persist.LogValues;

@RunWith(JUnit4.class)
public class LogValuesUnitTest {

	@Test
	public void whenCreateLogValuesMessageObject_thenHaveCorrectAttributesAssigned() {
		
		LogValues logValues = new LogValues("Generando un mensaje...", 
				Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
		
		assertThat(logValues.getLogType()).isEqualTo(1);
		assertThat(logValues.getLevel()).isEqualTo(Level.INFO);
		assertThat(logValues.getDescriptionLogType()).isEqualTo(MessageLoggerConstants.DESCRIPTION_MESSAGE_LOG_TYPE);
		
	}
	
	@Test
	public void whenCreateLogValuesErrorObject_thenHaveCorrectAttributesAssigned() {
		
		LogValues logValues = new LogValues("Generando un mensaje de error...", 
				Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
		
		assertThat(logValues.getLogType()).isEqualTo(2);
		assertThat(logValues.getLevel()).isEqualTo(Level.SEVERE);
		assertThat(logValues.getDescriptionLogType()).isEqualTo(MessageLoggerConstants.DESCRIPTION_ERROR_LOG_TYPE);
		
	}
	
	@Test
	public void whenCreateLogValuesWarningObject_thenHaveCorrectAttributesAssigned() {
		
		LogValues logValues = new LogValues("Generando un mensaje de advertencia...", 
				Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
		
		assertThat(logValues.getLogType()).isEqualTo(3);
		assertThat(logValues.getLevel()).isEqualTo(Level.WARNING);
		assertThat(logValues.getDescriptionLogType()).isEqualTo(MessageLoggerConstants.DESCRIPTION_WARNING_LOG_TYPE);
		
	}
	
}
