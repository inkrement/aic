package com.aic.sentiment_analysis.feature;

/**
 * Represents a feature which consists of
 * a tag and a word.
 */
public class Feature {

    private String word = "";
    private String tag = "";

    public void setWord(String word) {
        this.word = word;
    }
    public String getWord() {
        return word;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString(){
        return word.toString() + "_"  + tag.toString();
    }
}
