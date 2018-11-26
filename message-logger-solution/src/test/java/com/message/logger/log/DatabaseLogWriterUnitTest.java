package com.message.logger.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.message.logger.dao.LogValuesDao;
import com.message.logger.log.impl.DatabaseLogWriter;
import com.message.logger.model.persist.LogValues;

@RunWith(SpringRunner.class)
public class DatabaseLogWriterUnitTest {

    @TestConfiguration
    static class DatabaseLogWriterUnitTestContextConfiguration {
    	
        @Bean
        public DatabaseLogWriter databaseLogWriter() {
            return new DatabaseLogWriter();
        }
        
    }
    
    @Autowired
    private DatabaseLogWriter databaseLogWriter;
    
    @MockBean
    private LogValuesDao logValuesDao;
    
    @Test
    public void whenSaveMessageInBD_thenNoErrors() throws Exception {
    	
    	LogValues logValues = new LogValues("Registrando mensaje en base de datos...", 
    			Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
    	
    	databaseLogWriter.writeMessage(logValues);
    	
    }
    
    @Test
    public void whenSaveWarningInBD_thenNoErrors() throws Exception {
    	
    	LogValues logValues = new LogValues("Registrando advertencia en base de datos...", 
    			Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
    	
    	databaseLogWriter.writeMessage(logValues);
    	
    }
    
    @Test
    public void whenSaveErrorInBD_thenNoErrors() throws Exception {
    	
    	LogValues logValues = new LogValues("Registrando error en base de datos...", 
    			Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
    	
    	databaseLogWriter.writeMessage(logValues);
    	
    }
    
}
