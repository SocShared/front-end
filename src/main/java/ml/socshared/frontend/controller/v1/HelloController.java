package ml.socshared.frontend.controller.v1;

import ml.socshared.frontend.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import ml.socshared.frontend.api.v1.rest.HelloApi;

import java.util.HashMap;

@Controller
public class HelloController implements HelloApi {

    private TestService service;

    public HelloController(TestService service) {
        this.service = service;
    }

    @Override
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String printHelloWorld(Model model) {
        model.addAttribute("message", "Hello, world from spring with Thymeleaf");
        return "hello";
    }
    @GetMapping(value = "/feign", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testFeign() {
        return service.test();
    }

}
