package edu.vanderbilt.vuit.adi.academic.tasklet;

import edu.vanderbilt.vuit.adi.academic.exception.JobProcessException;
import edu.vanderbilt.vuit.adi.academic.model.JobStatusWrapper;
import edu.vanderbilt.vuit.adi.academic.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Tasklet to execute the automation-batch jobs
 * 
 */
@Component
@Scope( "step" )
public class VUAutomationTestBatchTasklet implements Tasklet {

   private static final Logger log = LoggerFactory.getLogger( VUAutomationTestBatchTasklet.class );

   @Autowired
   private ApplicationContext applicationContext;
   
   @Value("${jobType:#{null}}")
   private String jobType;
   
   @Value("${jobSubType:#{null}}")
   private String jobSubType;

   public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext ) throws Exception {
      try {
         Processor processor = (Processor) applicationContext.getBean( jobType );
         String[] jobParams = new String[2];
         jobParams[0] = jobType;
         jobParams[1] = jobSubType;
         if ( processor == null ) {
            throw new IllegalArgumentException( "Invalid Job Name: " + jobType );
         }
         log.debug( "Starting '" + jobType + "' Job" );
         processor.executeProcess( jobParams );
         log.debug( "End of '" + jobType + "' Job" );
      }
      catch ( Exception e ) {
         exitWithError( e, jobType );
      }
      return null;
   }

   protected void exitWithError( Exception e, String jobName ) {
      String message = "Error occurred while running job " + jobName;
      if ( e instanceof JobProcessException) {
         JobProcessException pe = (JobProcessException) e;
         message = pe.getMessage();
         JobStatusWrapper jobStatusHolder = pe.getJobStatus();
         if ( jobStatusHolder != null ) {
            message += " jobStatus=" + jobStatusHolder.getJobStatus();
         }
      }
      log.error( message, e );
      System.exit( 1 );
   }

   public ApplicationContext getContext() {
      if ( applicationContext == null ) {
         applicationContext = new ClassPathXmlApplicationContext();
      }
      return applicationContext;
   }

   public void setContext( ApplicationContext applicationContext ) {
      this.applicationContext = applicationContext;
   }

}
