package com.message.logger.log;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.message.logger.MessageLoggerSolutionApplicationTests;

public class JobLoggerIntegrationTest extends MessageLoggerSolutionApplicationTests {

	@Autowired
	private JobLogger jobLogger;
	
	@Test
	public void whenSavingMessageLogInAllOutputs_thenNoErrors() throws Exception {
		
		jobLogger.writeLogMessage("Este es un mensaje...", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
        
	}

	@Test
	public void whenSavingErrorLogInAllOutputs_thenNoErrors() throws Exception {
		
		jobLogger.writeLogMessage("Este es un mensaje de error...", Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
        
	}
	
	@Test
	public void whenSavingWarningLogInAllOutputs_thenNoErrors() throws Exception {
		
		jobLogger.writeLogMessage("Este es un mensaje de advertencia...", Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        
	}
}
