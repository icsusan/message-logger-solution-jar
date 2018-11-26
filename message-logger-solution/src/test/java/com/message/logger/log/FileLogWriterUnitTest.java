package com.message.logger.log;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.message.logger.log.impl.FileLogWriter;
import com.message.logger.model.persist.LogValues;

@RunWith(SpringRunner.class)
public class FileLogWriterUnitTest {

    @TestConfiguration
    static class FileLogWriterUnitTestContextConfiguration {
    	
        @Bean
        public FileLogWriter fileLogWriter() {
            return new FileLogWriter();
        }
        
        @Bean
        public Environment environment() {
            return new MockEnvironment();
        }
        
    }
    
    @Autowired
    private FileLogWriter fileLogWriter;
    
    @Autowired
    protected Environment environment;
    
    @PostConstruct
    public void postConstruct(){
    	
    	((MockEnvironment)environment).setProperty("message.logger.log.directory.name", "message-logger-log-output-test");
    	((MockEnvironment)environment).setProperty("message.logger.log.file.name", "job_logger.log");

    }
    
    @Test
    public void whenWriteMessageInFile_thenViewTextInFile() throws Exception {
    	
    	LogValues logValues = new LogValues("Imprimiendo mensaje en archivo...", 
    			Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
    	
    	fileLogWriter.writeMessage(logValues);
    	
    }
    
    @Test
    public void whenWriteWarningInFile_thenViewTextInFile() throws Exception {
    	
    	LogValues logValues = new LogValues("Imprimiendo advertencia en archivo...", 
    			Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
    	
    	fileLogWriter.writeMessage(logValues);
    	
    }
    
    @Test
    public void whenWriteErrorInFile_thenViewTextInFile() throws Exception {
    	
    	LogValues logValues = new LogValues("Imprimiendo error en archivo...", 
    			Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
    	
    	fileLogWriter.writeMessage(logValues);
    	
    }

}
