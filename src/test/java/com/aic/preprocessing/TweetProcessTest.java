import org.junit.* ;
import static org.junit.Assert.* ;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.aic.preprocessing.*;

public class TweetProcessTest{

	@Test
	public void removeValidURL() {

		List<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("http://orf.at"));
		} catch (WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

    	assertTrue(TweetProcess.removeURLs(tl).isEmpty());
	}

	@Test
	public void notRemoveInvalidURL() {

		List<Token> tl = new ArrayList<Token>();

		try{
			tl.add(new Token("someword"));
		} catch (WrongTokenFormatException e){
			fail("should not throw WrongTokenFormatException");
		}

    	assertFalse(TweetProcess.removeURLs(tl).isEmpty());
	}

	@Test
	public void tokenizeSentence() {

		List<Token> tl = TweetProcess.tokenizer("some sentance");

    	assertEquals(tl.size(), 2);
	}

	@Test
	public void RemoveSingleSpecialChar() {

		//TODO T1est bissl verschoenern

		ArrayList<Token> tl = new ArrayList<Token>();
		
		try{
			tl.add(new Token("he_y"));
			tl.add(new Token("he:y"));
			tl.add(new Token("he.y"));
			tl.add(new Token("he+y"));
			tl.add(new Token("he,y"));
			tl.add(new Token("he!y"));
			tl.add(new Token("he$y"));
			tl.add(new Token("he%y"));
			tl.add(new Token("he^y"));
			tl.add(new Token("he&y"));
			tl.add(new Token("he*y"));
			tl.add(new Token("he(y"));
			tl.add(new Token("he)y"));
			tl.add(new Token("he;y"));
			tl.add(new Token("he/y"));
			tl.add(new Token("he|y"));
			tl.add(new Token("he<y"));
			tl.add(new Token("he>y"));
			tl.add(new Token("he\"y"));
			tl.add(new Token("he\'y"));
			tl.add(new Token("he$y"));
			tl.add(new Token("he}y"));

			
		} catch(WrongTokenFormatException e){
			//TODO handle
		}

		List<Token> result = TweetProcess.replaceSpecialChars(tl);

		Iterator<Token> iterator = result.iterator();

		while(iterator.hasNext()){
			assertEquals("he y", iterator.next().getValue());
		}

	}
}