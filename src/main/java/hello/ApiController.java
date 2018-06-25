package hello;

import java.util.concurrent.atomic.AtomicLong;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/getCompany")
    public String getCompany(@RequestParam(value="name", defaultValue="Company A") String name) throws Exception {
        JsonReader call = new JsonReader();
        String result = call.sendGet(name);
        result = result.substring(1, result.length()-1);
        System.out.println(result);
        JSONObject json = new JSONObject(result);
        System.out.println(json.toString());
        return json.toString();
    }
}