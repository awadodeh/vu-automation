package edu.vanderbilt.vuit.adi.academic.batch.integration;

import java.time.LocalDateTime;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import edu.vanderbilt.vuit.adi.academic.BatchConstants;
import edu.vanderbilt.vuit.adi.academic.DataLoader;
import edu.vanderbilt.vuit.adi.academic.IntegrationTestConfig;
import edu.vanderbilt.vuit.adi.commons.test.db.JdbcDaoTestSupport;

@SpringBootTest( classes = IntegrationTestConfig.class )
@ActiveProfiles( { "local", "test" } )
@DirtiesContext
public abstract class BaseIntegrationTest {

   @Autowired
   protected JobRegistry jobRegistry;

   @Autowired
   protected JdbcDaoTestSupport dbTestUtil;

   @Autowired
   protected JobLauncher jobLauncher;

   @Autowired
   protected DataLoader dataLoader;

   @BeforeClass
   public static void setUpClass() {
      System.setProperty( "spring.config.name", BatchConstants.ARTIFACT_ID );
   }

   @AfterClass
   public static void tearDownClass() {
      System.clearProperty( "spring.config.location" );
   }

   protected JobParametersBuilder getJobParamsBuilder() {
      JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
      jobParametersBuilder.addString( "someDate", LocalDateTime.now().toString() );
      return jobParametersBuilder;
   }

   protected JobExecution setupAndExecuteJob( String jobName ) throws Exception {
      Job job = jobRegistry.getJob( jobName );
      Assert.assertThat( job, CoreMatchers.notNullValue() );

      JobParametersBuilder jobParametersBuilder = getJobParamsBuilder();

      JobExecution jobExecution = jobLauncher.run( job, jobParametersBuilder.toJobParameters() );
      while ( jobExecution.getStatus().isRunning() ) {
         Thread.sleep( 1000 );
      }
      Assert.assertThat( jobExecution.getStatus(), CoreMatchers.is( BatchStatus.COMPLETED ) );

      return jobExecution;
   }
}
