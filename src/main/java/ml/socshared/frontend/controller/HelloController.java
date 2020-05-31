package ml.socshared.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;

@Controller
public class HelloController {


    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String printHelloWorld(Model model) {
        model.addAttribute("message", "Hello, world from spring with Thymeleaf");
        model.addAttribute("page_title", "Hello_Controller");
        model.addAttribute("fragment_name", "hello_fragment");
        return "index";
    }

}
