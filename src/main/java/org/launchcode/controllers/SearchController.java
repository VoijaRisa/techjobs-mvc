package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    @RequestMapping(value = "results", method = RequestMethod.GET)
    public String processSearch(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {

        ArrayList<HashMap<String, String>> searchResults = new ArrayList<>();

        if (searchType.equals("all")) {
            searchResults = JobData.findByValue(searchTerm);
            searchType = "WTF";
        }
        else {
            searchResults = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        Integer numResults = searchResults.size();

        model.addAttribute("searchresults", searchResults);
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchType", searchType);
        model.addAttribute("numResults", numResults + " Result(s)");

        return "search";
    }

}
