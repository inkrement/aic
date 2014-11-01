package com.aic.components;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 01/11/14.
 */
public class TaggedTwitterWord extends TaggedWord{
    private TokenType type;


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

        switch(tw.toString().charAt(0)){
            case '@':
                ttw.type = TokenType.MENTIONING;
                ttw.setTag(tw.toString().substring(1));
                break;
            case '#':
                ttw.type = TokenType.TAG;
                ttw.setTag(tw.toString().substring(1));
                break;
            default:
                ttw.type = TokenType.WORD;
                ttw.setTag(tw.toString());
        }
        return ttw;
    }


}
