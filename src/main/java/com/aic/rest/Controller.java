package com.aic.rest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    public Sentiment sentiment(@RequestParam(value = "name") String name,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "start", required = false)
                                    @DateTimeFormat(pattern = "MMddyyyy") Date start,
                               @RequestParam(value = "end", required = false)
                                    @DateTimeFormat(pattern = "MMddyyyy") Date end) {
        return new Sentiment(0);
    }
}
