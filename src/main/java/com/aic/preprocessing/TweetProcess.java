package com.aic.preprocessing;

import com.aic.components.TaggedTwitterWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Filter;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TweetProcess {
    static final private String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
    static final private String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";


    /**
     *
     * @param input
     * @return
     */
	public static List<Tree> preprocess(String input){
        List<Tree> trees = new ArrayList<Tree>();

        String preprocessed_string = removeURLs(removeRepeatedChars(input));

        final MaxentTagger tagger = new MaxentTagger(taggerPath);
        LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(preprocessed_string));

        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            Tree tree = parser.apply(TaggedTwitterWord.fromTaggedWordList(tagged));
            trees.add(filter(tree));
        }

        return trees;
	}

    /**
     * filter tree
     * @param tree
     * @return
     */
	public static Tree filter(Tree tree){
        Tree PrunedTree ;

        Filter<Tree> f = new Filter<Tree>() {

            public boolean accept(Tree t) {
                //example. removes foreign words
                return ! t.label().value().equals("FW");
            }

        };



        return tree.prune(f);
	}


	/**
	* Remove multiple character-occurences
	* TODO: Filter fuer Woerter mit doppelbuchstaben, z.B. beer
	* TODO: Doppelbuchstaben eventuell alle belassen und erst bei drei wiederholten Buchstaben anfangen (?)
	* TODO: anders. List (o.Ae) von Wörtern mit Doppelbuchstaben und diese in der Schleife ueberspringen
	* example: heyyyyyy -> hey
	*/
	public static String removeRepeatedChars(String token){

		//Find repeated occurrence of a char
		// (.) : One character
		// 1 : The same character as (.)
		// + : Repeated occurences of (.)
		final String regex = "(.)\\1{3,}";

			//Remove all repeated characters
			// $1 : Use just one of multiple occurrence of a character		
		return token.replaceAll(regex,"$1");
	}

	
	/**
	* Remove URLS
	*/
	public static String removeURLs(String token){
		final String regex = "(?:^|\\s)(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
		return token.replace(regex,"");
	}
}