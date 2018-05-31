package edu.vanderbilt.vuit.adi.academic.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@ImportResource( "classpath:/jobsContext.xml" )
public class BatchConfig {

   @Bean
   public JobRegistry jobRegistry() {
      return new MapJobRegistry();
   }

   @Bean
   public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor( JobRegistry jobRegistry ) {
      JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
      jobRegistryBeanPostProcessor.setJobRegistry( jobRegistry );

      return jobRegistryBeanPostProcessor;
   }

   @Bean
   public AsyncTaskExecutor taskExecutor() {
      SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
      asyncTaskExecutor.setConcurrencyLimit( 20 );
      return asyncTaskExecutor;
   }

}
