package edu.vanderbilt.vuit.adi.academic.processor;

import static edu.vanderbilt.vuit.adi.academic.VUAutomationConstants.JobProcessingStatus;

import edu.vanderbilt.vuit.adi.academic.exception.JobProcessException;
import edu.vanderbilt.vuit.adi.academic.model.JobStatusWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public abstract class PollingProcessor implements Processor
{
   public Log log = LogFactory.getLog( this.getClass() );
   private int pollingInterval;
   private int pollingTimeout;

   public void executeProcess( String[] args ) throws JobProcessException {

      String jobName = args[0];
      
      String jobId = executePollingJob( args );
      log.info( "'" + jobName + "' Job running ID=" + jobId );
      pollingJobStatus( jobName, jobId );
   }

   public void pollingJobStatus( String jobName, String jobId ) throws JobProcessException
   {
      long startTime = new Date().getTime();
      JobStatusWrapper jobStatus = getJobStatus( jobId );
      while ( JobProcessingStatus.IN_PROGRESS == jobStatus.getProcessingStatus() )
      {
         if ( System.currentTimeMillis() - startTime >= pollingTimeout * 1000 )
         {
            log.info( "Time limit has reached, stop polling for job status." );
            break;
         }

         log.debug( "Waiting for '" + jobName + "' job to complete. ID=" + jobId );
         try
         {
            Thread.sleep( pollingInterval * 1000 );
         }
         catch ( InterruptedException e )
         {
            throw new JobProcessException( "Interupted while waiting for job to complete", jobStatus, e );
         }
         jobStatus = getJobStatus( jobId );
      }

      // only throw exception when the job is completely errored out and that applies for all jobs 
      // being invoked in the main method of this class
      if ( JobProcessingStatus.ERROR == jobStatus.getProcessingStatus() )
      {
         throw new JobProcessException( "Job completed with ERRORS", jobStatus );
      }
      else if ( JobProcessingStatus.IN_PROGRESS == jobStatus.getProcessingStatus() )
      {
         throw new JobProcessException( "Job did not finish within the expected maximum duration of [" + pollingTimeout + "s]", jobStatus );
      }
      else
      {
         log.info( "'" + jobName + "' Job completed with status [" + jobStatus.getProcessingStatus() + "]" );
      }
   }
   
   public abstract String executePollingJob( String[] args );

   public abstract JobStatusWrapper getJobStatus( String jobId );

   public int getPollingInterval()
   {
      return pollingInterval;
   }

   @Value("${jobStatus.pollInterval}")
   public void setPollingInterval( int pollingInterval )
   {
      this.pollingInterval = pollingInterval;
   }

   public int getPollingTimeout()
   {
      return pollingTimeout;
   }

   @Value("${jobStatus.pollTimeout}")
   public void setPollingTimeout( int pollingTimeout )
   {
      this.pollingTimeout = pollingTimeout;
   }
}