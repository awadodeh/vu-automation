package edu.vanderbilt.vuit.adi.academic.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import edu.vanderbilt.vuit.adi.academic.DataLoader;
import edu.vanderbilt.vuit.adi.academic.DatabaseTestConfig;
import edu.vanderbilt.vuit.adi.academic.BatchConstants;
import edu.vanderbilt.vuit.adi.commons.test.db.JdbcDaoTestSupport;

@SpringBootTest( classes = DatabaseTestConfig.class )
@ActiveProfiles( { "local", "test" } )
public abstract class BaseDbTestCase extends AbstractTransactionalJUnit4SpringContextTests {

   @Autowired
   protected JdbcDaoTestSupport dbTestUtil;

   @Autowired
   protected DataLoader dataLoader;

   @BeforeClass
   public static void setupConfig() {
      System.setProperty( "spring.config.name", BatchConstants.ARTIFACT_ID );
   }

   @AfterClass
   public static void removeConfig() {
      System.clearProperty( "spring.config.location" );
   }

}
