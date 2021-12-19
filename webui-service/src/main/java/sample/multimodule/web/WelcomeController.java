package sample.multimodule.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class WelcomeController {

    @Value("${spring.application.message:Hello World}")
    private String message = "Hello GUI Micro Service";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        // Trying to obtain 1002 account
        if (false) {
            // If there's some problem creating account, return show view with error status
            model.put("message", "Error getting account!");
            model.put("account", "");
            return "welcome/show";
        }

        // Return show view with 23 account info
        String accountInfo = "Your account number is ".concat("XXX-ZZZZ-YYYY");
        model.put("message", this.message);
        model.put("account", accountInfo);
        return "welcome/show";
    }

    @RequestMapping("foo")
    public String foo(Map<String, Object> model) {
        throw new RuntimeException("Foo");
    }

}
