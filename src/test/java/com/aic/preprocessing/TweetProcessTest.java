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
	public void RemoveSpecialChars() {

		ArrayList<Token> tl = new ArrayList<Token>();
		Token t1 = null;

		try{
			t1 = new Token("sente+nc.e");
		} catch(Exception e){
			//TODO handle
		}

		tl.add(t1);

		String s1 = TweetProcess.replaceSpecialChars(tl);


		assertEquals(s1,"sente nc e");

	}
}