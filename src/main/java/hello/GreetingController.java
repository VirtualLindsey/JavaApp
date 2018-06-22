package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/single")
    public String single(@RequestParam(name="name", required=false, defaultValue="single") String name, Model model) {
        model.addAttribute("name", name);
        return "single";
    }

    @GetMapping("/compare")
    public String compare(@RequestParam(name="name", required=false, defaultValue="compare") String name, Model model) {
        model.addAttribute("name", name);
        return "compare";
    }

}