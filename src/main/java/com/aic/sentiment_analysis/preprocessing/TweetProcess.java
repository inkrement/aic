package com.aic.sentiment_analysis.preprocessing;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
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
	public static List<TaggedTwitterWord> preprocess(String input){
        List<TaggedTwitterWord> token = new ArrayList<TaggedTwitterWord>();

        String preprocessed_string = removeURLs(removeRepeatedChars(input));

        final MaxentTagger tagger = new MaxentTagger(taggerPath);
        LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(preprocessed_string));

        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            Tree tree = parser.apply(TaggedTwitterWord.fromTaggedWordList(tagged));

            //convert tree to list
            List<TaggedTwitterWord> ttwl = TaggedTwitterWord.fromTree(filter(tree));
            token.addAll(ttwl);
        }

        return token;
	}


    /**
     * filter tree
     * @param tree
     * @return
     */
	public static Tree filter(Tree tree){
        Tree PrunedTree ;

        /**
         * CC - Coordinating conjunction
         CD - Cardinal number
         DT - Determiner
         EX - Existential there
         FW - Foreign word
         IN - Preposition or subordinating conjunction
         LS - List item marker
         PDT - Predeterminer
         POS - Possessive ending
         PRP - Personal pronoun
         PRP$ - Possessive pronoun (prolog version PRP-S)
         TO - to
         WDT - Wh-determiner
         WP - Wh-pronoun
         WP$ - Possessive wh-pronoun (prolog version WP-S)
         WRB - Wh-adverb
         */

        Filter<Tree> f = new Filter<Tree>() {

            public boolean accept(Tree t) {
                //example. removes foreign words
                return notForeignWord(t) || notCardinalNumber(t) || notCoordinationConjungtion(t) ||
                        notDeterminer(t) || notExtentionalThere(t) || notListItemMarker(t) ||
                        notPersonalPronoun(t) || notPossessiveEnding(t) || notPossessivePronoun(t) ||
                        notPossessiveWhPronoun(t) || notPredeterminer(t) || notPreposition(t) ||
                        notTo(t) || notWhDeterminer(t) || notWhPronoun(t) ||
                        notWhAdverb(t);
            }
            public boolean notCoordinationConjungtion(Tree t){
                return !t.label().value().equals("CC");
            }
            public boolean notCardinalNumber(Tree t){
                return !t.label().value().equals("CD");
            }
            public boolean notDeterminer(Tree t){
                return !t.label().value().equals("DT");
            }
            public boolean notExtentionalThere(Tree t){
                return !t.label().value().equals("EX");
            }
            public boolean notPreposition(Tree t){
                return !t.label().value().equals("IN");
            }
            public boolean notListItemMarker(Tree t){
                return !t.label().value().equals("LS");
            }
            public boolean notPredeterminer(Tree t){
                return !t.label().value().equals("PDT");
            }
            public boolean notPossessiveEnding(Tree t){
                return !t.label().value().equals("POS");
            }
            public boolean notPersonalPronoun(Tree t){
                return !t.label().value().equals("PRP");
            }
            public boolean notPossessivePronoun(Tree t){
                return !t.label().value().equals("PRP$");
            }
            public boolean notTo(Tree t){
                return !t.label().value().equals("TO");
            }
            public boolean notWhDeterminer(Tree t){
                return !t.label().value().equals("WDT");
            }
            public boolean notWhPronoun(Tree t){
                return !t.label().value().equals("WP");
            }
            public boolean notPossessiveWhPronoun(Tree t){
                return !t.label().value().equals("WP$");
            }
            public boolean notWhAdverb(Tree t){
                return !t.label().value().equals("WRB");
            }
            public boolean notForeignWord(Tree t) {
                return !t.label().value().equals("FW");
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
		final String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		
		return token.replaceAll(regex, "");
	}
}