package com.aic.sentiment_analysis.preprocessing;

import com.aic.sentiment_analysis.components.TaggedTwitterWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import org.junit.Test;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TweetProcessTest{

	@Test
	public void removeValidURL() {
        System.out.println("http://orf.at: " + TweetProcess.removeURLs("http://orf.at"));
    	assertTrue(TweetProcess.removeURLs("http://orf.at").isEmpty());
        assertEquals(TweetProcess.removeURLs("hey http://orf.at"), "hey ");
        assertEquals(TweetProcess.removeURLs("hey du"), "hey du");
	}


	@Test
	public void removeRepeatedChars(){
        System.out.println(TweetProcess.removeRepeatedChars("thissss") + "(thissss)");
        System.out.println(TweetProcess.removeRepeatedChars("thisss") + "(thisss)");
        System.out.println(TweetProcess.removeRepeatedChars("thiss") + "(thiss)");
        System.out.println(TweetProcess.removeRepeatedChars("this") + "(this)");

		assertEquals(TweetProcess.removeRepeatedChars("this"), "this");
        assertEquals(TweetProcess.removeRepeatedChars("thisssss"), "this");
        assertEquals(TweetProcess.removeRepeatedChars("thiis"), "thiis");
        assertEquals(TweetProcess.removeRepeatedChars("thisss"), "thisss");
	}



    @Test
    public void parseTweet() {
        final String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        //String modelPath = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";

        String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

        String text = "My dog likes to shake his stuffed chickadee toy. some #tags";

        final MaxentTagger tagger = new MaxentTagger(taggerPath);
        //ShiftReduceParser model = ShiftReduceParser.loadModel(modelPath);
        LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));

        for (List<HasWord> sentence : tokenizer) {

            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            Tree tree = parser.apply(TaggedTwitterWord.fromTaggedWordList(tagged));

            //System.out.println(tree);
            //new TreePrint("penn,typedDependenciesCollapsed").printTree(tree);
        }
    }

    @Test
    public void treeToList() {
        final String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        //String modelPath = "edu/stanford/nlp/models/srparser/englishSR.ser.gz";

        String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

        String text = "My dog likes to shake his stuffed chickadee toy";

        final MaxentTagger tagger = new MaxentTagger(taggerPath);
        //ShiftReduceParser model = ShiftReduceParser.loadModel(modelPath);
        LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));

        Iterator<List<HasWord>> i = tokenizer.iterator();
        //assert elements
        assertTrue(i.hasNext());

        List<TaggedTwitterWord> taggedwords = null;

        System.out.println("start iterate sentences: " + tokenizer.toString());

        while (i.hasNext()) {
            List<HasWord> sentence = i.next();
            System.out.println("Sentence: " + sentence);

            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            Tree tree = parser.apply(TaggedTwitterWord.fromTaggedWordList(tagged));

            taggedwords = TaggedTwitterWord.fromTree(tree);

            System.out.println("list: " + taggedwords);
        }

        assertEquals(taggedwords.size(), 9);


    }

}