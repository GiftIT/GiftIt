package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by VladislavGutov on 12/11/16.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String main(){
        return "index";
    }

}
