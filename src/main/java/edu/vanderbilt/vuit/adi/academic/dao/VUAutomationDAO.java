package edu.vanderbilt.vuit.adi.academic.dao;

import edu.vanderbilt.mis.student.admissions.model.JobStatus;
import org.springframework.stereotype.Component;

@Component
public interface VUAutomationDAO {

    public JobStatus getJobStatus(String jobId );
}
