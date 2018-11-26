package com.message.logger.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.message.logger.MessageLoggerSolutionApplicationTests;
import com.message.logger.common.MessageLoggerConstants;
import com.message.logger.model.persist.LogValues;

public class LogValuesDaoIntegrationTest extends MessageLoggerSolutionApplicationTests {

	@Autowired
	private LogValuesDao logValuesDao;
	
	@Test
	public void whenSaving_thenNoErrors() {
		
		LogValues message = new LogValues("Este es un mensaje...", MessageLoggerConstants.INDICATOR_MESSAGE_LOG_TYPE);
		LogValues error = new LogValues("Este es un mensaje de error...", MessageLoggerConstants.INDICATOR_ERROR_LOG_TYPE);
		LogValues warning = new LogValues("Este es un mensaje de advertencia", MessageLoggerConstants.INDICATOR_WARNING_LOG_TYPE);
		
		List<LogValues> messages = Arrays.asList(message, error, warning);
		
		logValuesDao.saveAll(messages);
		
        assertThat(messages).hasSize(3).extracting(LogValues::getDescription)
        .containsOnly(message.getDescription(), error.getDescription(), warning.getDescription());
        
	}
	
	@Test
	public void whenSaving_getSame() {
		
		LogValues message = new LogValues("Este es un mensaje...", MessageLoggerConstants.INDICATOR_MESSAGE_LOG_TYPE);
		logValuesDao.save(message);

		Optional<LogValues> recoveredMessage = logValuesDao.findById(message.getId());
		
		assertThat(message.getDescription()).isEqualTo(recoveredMessage.get().getDescription());
		
	}
	
}
