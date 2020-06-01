package ml.socshared.frontend.controller;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class TechSupportController {
    private TechSupportService service;

    @Autowired
    TechSupportController(TechSupportService service) {
        this.service = service;
    }

    @GetMapping("support")
    public String questionsPage(Pageable pageable, Model model,
                                @CookieValue(value = "token", required = false) String token) {
      log.info("request get questions page");
      service.questionsPage(pageable,model, token);
      return "support_questions_page";
    }

    @GetMapping("support/questions/{questionId}")
    public String fullQuestionPage(@PathVariable Integer questionId, Pageable pageable,
                                   Model model, @CookieValue(value = "token", required = false) String token) {
        log.info("request get page of full question " + String.valueOf(questionId));
        service.fullQuestionPage(questionId, pageable, model, token);
        return "support_full_question_page";

    }
}
