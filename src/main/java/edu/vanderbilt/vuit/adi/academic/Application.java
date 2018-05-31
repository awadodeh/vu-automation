package edu.vanderbilt.vuit.adi.academic;

import static edu.vanderbilt.vuit.adi.academic.BatchConstants.ARTIFACT_ID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import edu.vanderbilt.vuit.adi.commons.spring.batch.SpringApplicationEnvironmentBuilder;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan( basePackages = { "edu.vanderbilt.vuit.adi.academic", "edu.vanderbilt.vuit.adi.commons.spring.batch" } )
public class Application {

   private static final Logger LOG = LoggerFactory.getLogger( Application.class );

   public static void main( String[] args ) {
      SpringApplication app = configureApplication();
      LOG.debug( "App configured. Running app" );
      app.run( args );
   }

   static SpringApplication configureApplication() {
      return new SpringApplicationEnvironmentBuilder( Application.class, ARTIFACT_ID ).application();
   }

}

