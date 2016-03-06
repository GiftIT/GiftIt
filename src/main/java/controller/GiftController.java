package controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebService;

/**
 * Created by vitaliy on 06.03.16.
 */
@RestController

public class GiftController {


    @RequestMapping(value = "/gift", method = RequestMethod.GET)
    public
    @ResponseBody
    String getGiftJSON(@RequestParam(value = "name") String name,
                       @RequestParam(value = "age") int age,
                       @RequestParam(value = "coutry") String country) {
        JSONObject jsonObject = new JSONObject();
        //TODO add Machine Learning


        return jsonObject.toString();
    }


}