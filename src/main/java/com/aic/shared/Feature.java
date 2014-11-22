package com.aic.shared;

import edu.stanford.nlp.ling.CoreLabel;

public class Feature extends CoreLabel {

    private String word = "";

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
