package edu.vanderbilt.vuit.adi.academic.manager;

import edu.vanderbilt.mis.student.admissions.model.JobStatus;
import org.springframework.stereotype.Service;

@Service
public interface VUAutomationManager {

    JobStatus getJobStatus(String jobId);

    String kickOffMoreAutomationJob(String[] args);

}
