import org.junit.* ;
import static org.junit.Assert.* ;

import java.util.ArrayList;
import java.util.List;

import com.aic.preprocessing.*;

public class TweetProcessTest{

	@Test
	public void removeURL() {

		List<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("http://orf.at"));
		} catch (WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

    	assertTrue(TweetProcess.removeURLs(tl).isEmpty());
	}
}