package com.aic.rest.controller;

import com.aic.rest.RestException;
import com.aic.rest.domain.Company;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    public CompanyController() {
        this.companies = new Hashtable<>();
    }

    @RequestMapping(value = "", method=RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * Register a company within the system.
     *
     * TODO: throw exception if company already exists.
     *
     * @param name
     * @param password
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void register(@RequestParam(value = "name") String name,
                         @RequestParam(value = "password") String password) {
        Company company = new Company(name, password);
        companies.put(name, company);
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
    @ResponseBody
    public Double sentiment(@RequestParam(value = "name") String name,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "start", required = false)
                            @DateTimeFormat(pattern = "MMddyyyy") Date start,
                            @RequestParam(value = "end", required = false)
                            @DateTimeFormat(pattern = "MMddyyyy") Date end) throws RestException {
        if (!companies.containsKey(name)) {
            throw new RestException("Company not registered!");
        }
        if (!companies.get(name).getPassword().equals(password)) {
            throw new RestException("Wrong password!");
        }

        return 0.0;
    }
    
    @RequestMapping(value = "/tweets", method = RequestMethod.GET)
    @ResponseBody
    public List<String> tweets(@RequestParam(value = "name") String name,
                               @RequestParam(value = "start", required = false)
                                    @DateTimeFormat(pattern = "yyyyMMdd") Date start,
                               @RequestParam(value = "end", required = false)
                                    @DateTimeFormat(pattern = "yyyyMMdd") Date end) {

        return null;
    }
}
