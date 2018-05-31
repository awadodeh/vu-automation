package edu.vanderbilt.vuit.adi.academic;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import edu.vanderbilt.vuit.adi.commons.test.db.JdbcDaoTestSupport;

@Configuration
@EnableAutoConfiguration
@ComponentScan( excludeFilters = { @Filter( classes = { SpringBootApplication.class }, type = FilterType.ANNOTATION ),
                                   @Filter( classes = { CommandLineRunner.class, ApplicationRunner.class }, type = FilterType.ASSIGNABLE_TYPE ),
                                   @Filter( pattern = ".*TestConfig", type= FilterType.REGEX  )} )
public class IntegrationTestConfig {

   @Autowired
   private DataSource dataSource;

   @Bean
   public JdbcDaoTestSupport jdbcDaoTestSupport() {
      return new JdbcDaoTestSupport( dataSource );
   }

   @Bean
   public JobRepository jobRepository() throws Exception {
      MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
      mapJobRepositoryFactoryBean.setTransactionManager( new ResourcelessTransactionManager() );
      return mapJobRepositoryFactoryBean.getObject();
   }

   @Bean
   public JobLauncher jobLauncher() throws Exception {
      SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
      simpleJobLauncher.setJobRepository( jobRepository() );
      return simpleJobLauncher;
   }

}