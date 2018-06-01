package edu.vanderbilt.vuit.adi.academic;

import edu.vanderbilt.mis.student.admissions.model.JobStatus;

import java.util.*;

public class VUAutomationConstants {

   public static enum JobProcessingStatus
   {
      IN_PROGRESS,
      ERROR,
      PARTIAL_ERROR,
      SUCCESS
   }
   
   public static enum JobName {
      KICKOFF_MORE_TAURUS_AUTOMATION
   }

   public static final Map<Object, JobProcessingStatus> JOB_STATUS_XREF_MAP;
   static
   {
      Map<Object, JobProcessingStatus> status = new HashMap<Object, JobProcessingStatus>();
      // admissions service
      status.put( JobStatus.JobStatuses.I, JobProcessingStatus.IN_PROGRESS );
      status.put( JobStatus.JobStatuses.E, JobProcessingStatus.ERROR );
      status.put( JobStatus.JobStatuses.P, JobProcessingStatus.PARTIAL_ERROR );
      status.put( JobStatus.JobStatuses.C, JobProcessingStatus.SUCCESS );

      // docuemnt service
      status.put( edu.vanderbilt.mis.student.document.model.JobStatus.JobStatuses.I, JobProcessingStatus.IN_PROGRESS );
      status.put( edu.vanderbilt.mis.student.document.model.JobStatus.JobStatuses.E, JobProcessingStatus.ERROR );
      status.put( edu.vanderbilt.mis.student.document.model.JobStatus.JobStatuses.P, JobProcessingStatus.PARTIAL_ERROR );
      status.put( edu.vanderbilt.mis.student.document.model.JobStatus.JobStatuses.C, JobProcessingStatus.SUCCESS );

      // service commons
      status.put( edu.vanderbilt.mis.student.model.JobStatus.JobStatuses.I, JobProcessingStatus.IN_PROGRESS );
      status.put( edu.vanderbilt.mis.student.model.JobStatus.JobStatuses.E, JobProcessingStatus.ERROR );
      status.put( edu.vanderbilt.mis.student.model.JobStatus.JobStatuses.P, JobProcessingStatus.PARTIAL_ERROR );
      status.put( edu.vanderbilt.mis.student.model.JobStatus.JobStatuses.C, JobProcessingStatus.SUCCESS );
      JOB_STATUS_XREF_MAP = Collections.unmodifiableMap( status );
   }

}