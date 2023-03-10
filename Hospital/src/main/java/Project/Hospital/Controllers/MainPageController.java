package Project.Hospital.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("/index")
    public String loadMainPage() {
        return "/hospital/index";
    }
}
