package ml.socshared.frontend.controller;

import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.form.tect_support.FormAddComment;
import ml.socshared.frontend.domain.model.form.tect_support.FormCreateQuestion;
import ml.socshared.frontend.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @PostMapping("support/questions/{questionId}")
    public String addCommentToQuestion(@PathVariable Integer questionId, Pageable pageable,
                                       @ModelAttribute FormAddComment comment, Model model,
                                       @CookieValue(value="token", required = false) String token) {
        log.info("Request add comment to question " + String.valueOf(questionId));
        UUID systemUserId = UUID.fromString("7f2596b9-7177-47cd-adb8-bb693cee5343");//Todo извлечение id пользователя из токена
        service.addComment(questionId, comment, pageable, systemUserId, model, token);
        return "support_full_question_page";
    }



    @GetMapping("support/questions")
    public String pageAddQuestion(Model model,
                              @CookieValue(value="token", required = false) String token) {
        log.info("Request page for add page");
        UUID systemUserId = UUID.fromString("7f2596b9-7177-47cd-adb8-bb693cee5343");//Todo извлечение id пользователя из токена
        service.pageAddQuestion(systemUserId, model, token);
        return "support_add_question";
    }

    @PostMapping("support/questions")
    public void addQuestion(Model model,
                              @ModelAttribute FormCreateQuestion question,
                              @CookieValue(value="token", required = false) String token,
                              HttpServletResponse httpServletResponse) {
        log.info("Request add new question");
        UUID systemUserId = UUID.fromString("7f2596b9-7177-47cd-adb8-bb693cee5343");//Todo извлечение id пользователя из токена
        Integer questionId = service.addQuestion(question, systemUserId, model, token);
        httpServletResponse.setStatus(302);
        httpServletResponse.setHeader("Location", "/support/questions/" + String.valueOf(questionId) + "/");
    }
}
