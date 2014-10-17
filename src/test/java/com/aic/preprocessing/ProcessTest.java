import org.junit.* ;
import static org.junit.Assert.* ;

import com.aic.preprocessing.*;

public class ProcessTest{

	@Test
	public void removeURL() {

		//Nur ein Versuch...ich weiss dass das nicht in diese klasse gehoert ^^
		String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    
    	assertTrue("http://www.leveluplunch.com".matches(urlRegex));

	}
}