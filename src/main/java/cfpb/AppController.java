package cfpb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

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