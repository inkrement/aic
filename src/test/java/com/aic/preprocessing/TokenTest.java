import org.junit.* ;
import static org.junit.Assert.* ;

import com.aic.preprocessing.*;
import com.aic.components.*;

public class TokenTest{

	@Test
	public void parseTagTokenConstuctor() {

		try{
			Token tt = new Token("#test");
			assertTrue(tt.getValue().equals("test")) ;
		} catch(WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}
	}

	@Test
	public void parseMentioningTokenConstuctor() {

		try{
			Token tt = new Token("@somename");
			assertTrue(tt.getValue().equals("somename"));
			assertTrue(tt.getToken().equals("@somename"));
		} catch(WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}
	}

	@Test
	public void parseWordTokenConstuctor() {

		try{
			Token tt = new Token("someword");
			assertTrue(tt.getValue().equals("someword"));
			assertTrue(tt.getToken().equals("someword"));
		} catch(WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}
	}
}