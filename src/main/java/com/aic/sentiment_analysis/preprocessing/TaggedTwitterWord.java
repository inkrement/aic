package com.aic.sentiment_analysis.preprocessing;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 01/11/14.
 */
public class TaggedTwitterWord extends TaggedWord{
    private TokenType type;

    public static List<TaggedTwitterWord> fromTree(Tree t){
        List<TaggedTwitterWord> result = new ArrayList<>();

        // Puntigam würd mich für das killen ...
        for(HasWord ttw: t.yieldHasWord()){
            if(ttw instanceof TaggedTwitterWord)
                result.add((TaggedTwitterWord) ttw);
        }

        return result;
    }

    public static List<? extends HasWord> fromTaggedWordList(List<TaggedWord> tagged) {
        List<TaggedTwitterWord> result = new ArrayList<TaggedTwitterWord>();

        for(TaggedWord tw: tagged){
            TaggedTwitterWord ttw = TaggedTwitterWord.fromTaggedWord(tw);
            
            result.add(ttw);
        }
        return result;
    }

    private static TaggedTwitterWord fromTaggedWord(TaggedWord tw) {
        TaggedTwitterWord ttw = new TaggedTwitterWord();
        ttw.setTag(tw.tag());


        switch(tw.toString().charAt(0)){
            case '@':
                ttw.type = TokenType.MENTIONING;
                ttw.setWord(tw.word().substring(1));
                break;
            case '#':
                ttw.type = TokenType.TAG;
                ttw.setWord(tw.word().substring(1));
                break;
            default:
                ttw.type = TokenType.WORD;
                ttw.setWord(tw.word());
        }

        return ttw;
    }


}
