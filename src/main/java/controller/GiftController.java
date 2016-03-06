package controller;

import logic.ClassificationProcessor;
import logic.StatisticsAnalyzer;
import logic.StatisticsUpdater;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    String getGiftJSON(@RequestParam(value = "sex") int sex,
                       @RequestParam(value = "age") int age,
                       @RequestParam(value = "country") int country) {
        JSONObject jsonObject = new JSONObject();

        //TODO add Machine Learning


        return jsonObject.toString();
    }


}