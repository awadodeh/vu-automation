package edu.vanderbilt.vuit.adi.academic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

import edu.vanderbilt.vuit.adi.academic.Application;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith( PowerMockRunner.class )
@PrepareForTest( Application.class )
public class TestApplication {

   @Spy
   private SpringApplication app = new SpringApplication();

   @Test
   public void callMain() throws Exception {
      String[] args = new String[]{ "testJob", "some date" };

      PowerMockito.spy( Application.class );
      PowerMockito.when( Application.class, "configureApplication" ).thenReturn( app );
      doReturn( null ).when( app ).run( args );

      Application.main( args );

      verify( app ).run( args );
   }

}
