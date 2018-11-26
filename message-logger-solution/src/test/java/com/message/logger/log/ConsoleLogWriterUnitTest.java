package com.message.logger.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.message.logger.log.impl.ConsoleLogWriter;
import com.message.logger.model.persist.LogValues;

@RunWith(SpringRunner.class)
public class ConsoleLogWriterUnitTest {

    @TestConfiguration
    static class ConsoleLogWriterUnitTestContextConfiguration {
    	
        @Bean
        public ConsoleLogWriter consoleLogWriter() {
            return new ConsoleLogWriter();
        }
        
    }
    
    @Autowired
    private ConsoleLogWriter consoleLogWriter;
    
    @Test
    public void whenWriteMessageInConsole_thenViewTextInConsole() throws Exception {
    	
    	LogValues logValues = new LogValues("Imprimiendo mensaje en consola...", 
    			Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
    	
    	consoleLogWriter.writeMessage(logValues);
    	
    }
    
    @Test
    public void whenWriteWarningInConsole_thenViewTextInConsole() throws Exception {
    	
    	LogValues logValues = new LogValues("Imprimiendo advertencia en consola...", 
    			Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
    	
    	consoleLogWriter.writeMessage(logValues);
    	
    }
    
    @Test
    public void whenWriteErrorInConsole_thenViewTextInConsole() throws Exception {
    	
    	LogValues logValues = new LogValues("Imprimiendo error en consola...", 
    			Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
    	
    	consoleLogWriter.writeMessage(logValues);
    	
    }
    
}
