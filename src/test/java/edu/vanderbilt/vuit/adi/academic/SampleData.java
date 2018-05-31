package edu.vanderbilt.vuit.adi.academic;

import java.time.LocalDate;

public class SampleData {

   public static final LocalDate TEST_DATE = LocalDate.of( 2016, 7, 20 );
   public static final LocalDate AFTER_TEST_DATE = TEST_DATE.plusDays( 1 );
   public static final LocalDate BEFORE_TEST_DATE = TEST_DATE.minusDays( 1 );



}