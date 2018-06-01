package edu.vanderbilt.vuit.adi.academic.manager;

import edu.vanderbilt.mis.student.admissions.model.JobStatus;
import edu.vanderbilt.vuit.adi.academic.dao.VUAutomationDAO;
import edu.vanderbilt.vuit.adi.academic.util.Runner;
import org.springframework.beans.factory.annotation.Autowired;

public class VUAutomationManagerImpl implements VUAutomationManager {

    @Autowired
    private VUAutomationDAO vuAutomationDAO;

    public JobStatus getJobStatus(String jobId) {
        return vuAutomationDAO.getJobStatus( jobId );
    }

    public String kickOffMoreAutomationJob(String[] args){

        String jobId = null;
        int length = args.length;

//        TODO: add another argument for automation type if we have similier process for multiple automation types
        if( length > 2 ){
            String directory = args[0];
            String fileName = args[1];


        }

/**
 *
 * TODO: add the way we want to kick off the job { bzt directory/filename }
 *
 */
        return "success";
    }

}
