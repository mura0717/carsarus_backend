package dat3.cars.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class HomeApi {
    @GetMapping
    public RedirectView home() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
