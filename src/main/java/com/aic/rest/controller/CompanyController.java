package com.aic.rest.controller;

import com.aic.rest.RestException;
import com.aic.rest.domain.AggregateSentiment;
import com.aic.rest.domain.Company;
import com.aic.sentiment_analysis.ClassifierConfiguration;
import com.aic.sentiment_analysis.SentimentAnalysisException;
import com.aic.sentiment_analysis.SentimentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Hashtable;

/**
 * Handles the REST requests that are sent to the server.
 */
@Controller
@RequestMapping("/")
public class CompanyController {

	private SentimentAnalyzer sentimentAnalyzer;
    private Hashtable<String, Company> companies;

	@Autowired
    public CompanyController(SentimentAnalyzer sentimentAnalyzer) {
		this.sentimentAnalyzer = sentimentAnalyzer;
        this.companies = new Hashtable<>();
    }

    @RequestMapping(value = "/classifier", method = RequestMethod.GET)
    @ResponseBody
    public ClassifierConfiguration[] populateClassifierConfigurations() {
        return ClassifierConfiguration.values();
    }

    @RequestMapping(value = "", method=RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * Register a company within the system.
     *
     * @param company the company to save
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Company> register(@RequestBody Company company) throws RestException {
        if (companies.containsKey(company.getName())) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }

        companies.put(company.getName(), company);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    /**
     * Helper method for checking if credentials are correct.
     * Note: This authorization mechanism is just for the
     * proof-of-concept implementation and intended for
     * use in production environment.
     *
     * @param name the company name
     * @param password company password
     * @return boolean
     */
    private boolean isAuthorized(String name, String password) {
        if (!companies.containsKey(name)) {
            return false;
        }

        return true;
    }

    /**
     * Calculates the sentiment for the given company within the period.
     *
     * @param name name of the company
     * //@param password password of the company
     * @param start start date for the sentiment analysis
     * @param end end date for the sentiment analysis
     */
    @RequestMapping(value = "/sentiment", method = RequestMethod.GET)
    @ResponseBody
    public AggregateSentiment sentiment(@RequestParam(value = "name") String name,
                           //@RequestParam(value = "password") String password,
                           @RequestParam(value = "startDate")
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                           @RequestParam(value = "endDate")
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                           @RequestParam(value = "classifier")
                           ClassifierConfiguration classifier) throws RestException, SentimentAnalysisException {

        System.out.println(classifier);
        // Wrapping aggregateSentiment for Jackson
        AggregateSentiment aggregateSentiment = new AggregateSentiment(
            sentimentAnalyzer.aggregateSentiment(name, start, end, classifier)
        );

        return aggregateSentiment;
    }
}
