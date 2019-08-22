package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

import static org.launchcode.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results

    @RequestMapping(value = "results")
    public String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {

        model.addAttribute("columns", columnChoices);

        Model noResults = model.addAttribute("title", "There are no results, please try another search.");


        if (searchType.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findByValue(searchTerm);

            if (jobs.isEmpty()) {
                model.addAttribute(noResults);

            } else {
                model.addAttribute("title", "Showing " + jobs.size() + " " + searchTerm + " Results From All Categories:");
                model.addAttribute("jobs", jobs);

                return "search";
            }

        } else {
            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);

            if (jobs.isEmpty()) {
                model.addAttribute(noResults);

            } else {
                model.addAttribute("title", "Showing " + jobs.size() + " " +  searchTerm + "  results from the " + searchType + " Column:");
                model.addAttribute("jobs", jobs);

                return "search";
            }

        }

        return "search";
    }
}