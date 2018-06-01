package edu.vanderbilt.vuit.adi.academic.config;

import edu.vanderbilt.vuit.adi.academic.processor.kickoffMoreTaurusAutomationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:vuAutomation.properties")
public class AcadAutomationConfig {

   @Bean( name = { "KICKOFF_MORE_TAURUS_AUTOMATION" } )
   public kickoffMoreTaurusAutomationProcessor kickoffMoreTaurusAutomationProcessor() {

      return kickoffMoreTaurusAutomationProcessor();
   }

}
