package Sample;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class SampleController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/single")
    public String single(){
        return "Single company page will go here";
    }

    @RequestMapping("/compare")
    public String compare(){
        return "Comparing companies page will go here";
    }

    @RequestMapping("/login")
    public String login(){
        return "Login page will go here";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "Logout page will go here";
    }

}