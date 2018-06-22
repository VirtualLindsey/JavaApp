package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/getCompany")
    public Company getCompany(@RequestParam(value="name", defaultValue="Company A") String name) {
        return new Company(counter.incrementAndGet(),
                name);
    }
}