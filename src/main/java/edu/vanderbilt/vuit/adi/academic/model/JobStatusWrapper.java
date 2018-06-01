package edu.vanderbilt.vuit.adi.academic.model;


import edu.vanderbilt.vuit.adi.academic.VUAutomationConstants;
import edu.vanderbilt.vuit.adi.academic.VUAutomationConstants.JobProcessingStatus;
import static edu.vanderbilt.mis.student.commons.lang.BeanUtils.getProperty;

public class JobStatusWrapper {

   private JobProcessingStatus processingStatus;
   private Object jobStatus;
   
   public JobStatusWrapper(Object jobStatus)
   {
      setJobStatus( jobStatus );
   }

   public JobProcessingStatus getProcessingStatus()
   {
      return processingStatus;
   }

   public void setProcessingStatus( JobProcessingStatus status )
   {
      this.processingStatus = status;
   }

   public Object getJobStatus()
   {
      return jobStatus;
   }

   public void setJobStatus( Object jobStatus ) {

      if ( jobStatus != null) {

         processingStatus = VUAutomationConstants.JOB_STATUS_XREF_MAP.get( getProperty( jobStatus, "status" ) );
      }
      else {

         processingStatus = null;
      }
      this.jobStatus = jobStatus;
   }
}
