package edu.vanderbilt.vuit.adi.academic.dao;

import edu.vanderbilt.mis.student.admissions.model.JobStatus;

public class VUAutomationDAOImpl implements VUAutomationDAO {

    public JobStatus getJobStatus(String jobId) {
//        TODO: implement the job status method. Hardcoded now.
        return new JobStatus("test");
    }
}
