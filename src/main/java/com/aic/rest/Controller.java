package com.aic.rest;

import com.aic.classification.Classifier;
import com.aic.fetch.*;
import com.aic.components.*;

import com.aic.preprocessing.TweetProcess;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Handles the REST requests that are sent to the server.
 */
@RestController
public class Controller {

    /**
     * Register a company within the system.
     *
     * @param name
     * @param password
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestParam(value = "name") String name,
                         @RequestParam(value = "password") String password) {

    }

    /**
     * Calculates the sentiment for the given company within the period.
     *
     * @param name
     * @param password
     * @param start
     * @param end
     */
    @RequestMapping(value = "/sentiment", method = RequestMethod.GET)
    public Double sentiment(@RequestParam(value = "name") String name,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "start", required = false)
                                    @DateTimeFormat(pattern = "MMddyyyy") Date start,
                               @RequestParam(value = "end", required = false)
                                    @DateTimeFormat(pattern = "MMddyyyy") Date end) {

        //fetch tweets
        List<TwitterStatus> l = Fetch.get(name, start, end);
        Classifier c = new Classifier();

        int pos = 0;

        for(TwitterStatus ts: l){
            if(c.classify(ts.getContent()).equals(com.aic.classification.Sentiment.POSITIVE))
                pos++;
        }

        return ((double)pos)/l.size();
    }
    
    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    public List<String> tweets(@RequestParam(value = "name") String name,
                               @RequestParam(value = "start", required = false)
                                    @DateTimeFormat(pattern = "yyyyMMdd") Date start,
                               @RequestParam(value = "end", required = false)
                                    @DateTimeFormat(pattern = "yyyyMMdd") Date end) {
       Fetch f = new Fetch();

       List<String> tweets = new ArrayList<String>();

       for(TwitterStatus t: f.get(name, start, end)){
            tweets.add(t.toString());
       }

       return tweets;
    }
}
