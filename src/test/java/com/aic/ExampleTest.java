package com.aic;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.util.CoreMap;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Properties;

/**
 * Created by chris on 01/11/14.
 */
public class ExampleTest {

    @Test
    public void isStanfordNotAsShittyAsSuggested(){
        String text = "I am feeling very upset. wuhuu lucky ommi";
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = pipeline.process(text);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            //List sentiment = sentence.get(CoreAnnotations.TokensAnnotation.class);
            //System.out.println(sentiment + "\t" + sentence);

            Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            //System.out.println("Sentiment: " + sentiment + " String: " + sentence.toString());
        }
    }



    @Test
    public void justAsimpleParserDemo() {
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
            //TaggedTwitterWord.fromTaggedWordList(tagged)
            Tree tree = parser.apply(tagged);

            //System.out.println(tree);
            new TreePrint("penn,typedDependenciesCollapsed").printTree(tree);
        }
    }

}
