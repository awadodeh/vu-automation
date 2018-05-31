package edu.vanderbilt.vuit.adi.academic;

import edu.vanderbilt.vuit.adi.commons.test.db.JdbcDaoTestSupport;


public class DataLoader {

   private JdbcDaoTestSupport dbTestUtil;

   public DataLoader( JdbcDaoTestSupport dbTestUtil ) {
      this.dbTestUtil = dbTestUtil;
   }

}
