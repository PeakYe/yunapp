package pers.yf.yunapp.gateway;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "hello test zuul";
    }
}
