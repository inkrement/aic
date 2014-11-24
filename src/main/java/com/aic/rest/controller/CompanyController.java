package com.aic.rest.controller;

import com.aic.rest.RestException;
import com.aic.rest.domain.Company;
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
import java.util.List;

/**
 * Handles the REST requests that are sent to the server.
 */
@Controller
@RequestMapping("/")
public class CompanyController {

    private Hashtable<String, Company> companies;

    private SentimentAnalyzer sentimentAnalyzer;

    public CompanyController() {
        this.companies = new Hashtable<>();
        try {
            this.sentimentAnalyzer = new SentimentAnalyzer();
        } catch (SentimentAnalysisException e) {
            e.printStackTrace();
        }
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
        if (companies.containsKey(company.getName()))
            throw new RestException("Company already exists");

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
        Company storedCompany = companies.get(name);
        if (!storedCompany.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    /**
     * Calculates the sentiment for the given company within the period.
     *
     * @param name name of the company
     * @param password password of the company
     * @param start start date for the sentiment analysis
     * @param end end date for the sentiment analysis
     */
    @RequestMapping(value = "/sentiment", method = RequestMethod.GET)
    @ResponseBody
    public float sentiment(@RequestParam(value = "name") String name,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "startDate")
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                           @RequestParam(value = "endDate")
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) throws RestException, SentimentAnalysisException {
        if (!isAuthorized(name, password))
            throw new RestException("Authorization failed");
        System.out.println(start + " - " + end);
        return sentimentAnalyzer.averageSentiment(name, start, end);
    }
}
