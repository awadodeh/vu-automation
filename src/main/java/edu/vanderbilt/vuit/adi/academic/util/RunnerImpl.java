package edu.vanderbilt.vuit.adi.academic.util;

import edu.vanderbilt.vuit.adi.academic.BatchConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.Runtime;

public class RunnerImpl implements Runner {

    private String fileName;
    private String fileDirectory;
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public String kickoffLoadTest() throws IOException {

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        if( StringUtils.isNotBlank( fileName ) && StringUtils.isNotBlank( fileDirectory ) ){

            try {
                process = runtime.exec(BatchConstants.TAURUS_KICK_OFF_COMMAND + fileDirectory + fileName );
            }catch ( IOException ex ){
                log.error("unable to run the taurus command line \n" + ex.getMessage());
                throw new IOException(ex.getMessage());
            }
        }

        return process.toString();
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }
}
