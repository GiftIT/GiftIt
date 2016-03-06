package controller;

import logic.StatisticsAnalyzer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;


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
        List<String> mlData = null;
        StringBuffer buffer = null;
        try {
            mlData = Arrays.asList(statisticsUpdater.getCategories(criteria, 5));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("vocabulary.json"), "UTF-8"));
            buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray result = new JSONArray();
        JSONObject jsonObject = new JSONObject(buffer.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = (JSONObject) jsonArray.get(i);
            if (mlData.contains(json.get("product"))) {
                jsonArray.put(json);
            }
        }
        return result.toString();
    }


}