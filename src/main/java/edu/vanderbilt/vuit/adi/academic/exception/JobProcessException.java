package edu.vanderbilt.vuit.adi.academic.exception;

import edu.vanderbilt.vuit.adi.academic.model.JobStatusWrapper;

public class JobProcessException extends Exception
{
   private JobStatusWrapper jobStatus;

   public JobProcessException()
   {
      super();
   }

   public JobProcessException(String message, Throwable cause)
   {
      super( message, cause );
   }

   public JobProcessException(String message)
   {
      super( message );
   }
   
   public JobProcessException(Throwable cause)
   {
      super( cause );
   }

   public JobProcessException(JobStatusWrapper jobStatus)
   {
      super();
      this.jobStatus = jobStatus;
   }

   public JobProcessException(String message, JobStatusWrapper jobStatus)
   {
      super(message);
      this.jobStatus = jobStatus;
   }
   
   public JobProcessException(String message, JobStatusWrapper jobStatus, Throwable t)
   {
      super(message, t);
      this.jobStatus = jobStatus;
   }

   public JobStatusWrapper getJobStatus()
   {
      return jobStatus;
   }
}
