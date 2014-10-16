import org.junit.* ;
import static org.junit.Assert.* ;

import com.aic.preprocessing.*;

public class TokenTypeTest {

   public void test_TokenConstuctor() {

   	try{
   		Token tt = new Token("#test");
     	assertTrue(tt.getToken().equals("test")) ;
     } catch(WrongTokenFormatException e){

     }
   }

   public void test_True() {
      assertTrue(false) ;
   }
}