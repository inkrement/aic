package com.aic;

import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import org.junit.Test;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;


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
            System.out.println("Sentiment: " + sentiment + " String: " + sentence.toString());
        }
    }

}
