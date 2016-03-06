package controller;

import logic.StatisticsAnalyzer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;


/**
 * Created by vitaliy on 06.03.16.
 */
@RestController

public class GiftController {
    @Autowired
    StatisticsAnalyzer statisticsUpdater;


    @RequestMapping(value = "/gift", method = RequestMethod.GET)
    public
    @ResponseBody
    String getGiftJSON(@RequestParam(value = "sex") double sex,
                       @RequestParam(value = "age") double age,
                       @RequestParam(value = "country") double country) {
        double[] criteria = {sex, age, country};
        String[] result = null;
        try {
            result = statisticsUpdater.getCategories(criteria, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject.toString();

    }


}