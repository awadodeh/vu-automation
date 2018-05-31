package edu.vanderbilt.vuit.adi.academic;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.vanderbilt.vuit.adi.commons.test.db.JdbcDaoTestSupport;

@Configuration
@EnableAutoConfiguration
@ComponentScan( basePackages = "edu.vanderbilt.vuit.adi.academic.dao" )
public class DatabaseTestConfig {

   @Autowired
   private DataSource datasource;

   @Bean
   public JdbcDaoTestSupport jdbcDaoTestSupport() {
      return new JdbcDaoTestSupport( datasource );
   }

   @Bean
   public DataLoader dataLoader() {
      return new DataLoader( jdbcDaoTestSupport() );
   }
}
