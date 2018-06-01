package edu.vanderbilt.vuit.adi.academic.processor;

import edu.vanderbilt.vuit.adi.academic.manager.VUAutomationManager;
import edu.vanderbilt.vuit.adi.academic.model.JobStatusWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class kickoffMoreTaurusAutomationProcessor extends PollingProcessor {


    public Log log = LogFactory.getLog( this.getClass() );

    @Autowired
    private VUAutomationManager VUAutomationManager;

    /**
     * The More Load Test Automation job should be called with 2 arguments. The structure of the arguments should be:
     * args[0] -- File Name ( which is a *.yml file that contains the test cases def.
     * args[1] -- File Directory  ( which is where the its located )
     */
    @Override
    public String executePollingJob( String[] args )
    {
        return VUAutomationManager.kickOffMoreAutomationJob( args );
    }

    @Override
    public JobStatusWrapper getJobStatus(String jobId ) {

        return new JobStatusWrapper( VUAutomationManager.getJobStatus( jobId ) );
    }

}
