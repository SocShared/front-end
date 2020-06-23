package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.tech_support.model.form.FormAddComment;
import ml.socshared.frontend.domain.tech_support.model.form.FormCreateQuestion;
import ml.socshared.frontend.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TechSupportController {

    private final TechSupportService service;


    @GetMapping("/support")
    public String questionsPage(Pageable pageable, Model model,
                                @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
      log.info("request get questions page");
      service.questionsPage(pageable,model, accessToken);
      return "support_questions_page";
    }

    @GetMapping("/support/questions/{questionId}")
    public String fullQuestionPage(@PathVariable Integer questionId, Pageable pageable,
                                   Model model,
                                   @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("request get page of full question " + String.valueOf(questionId));
        service.fullQuestionPage(questionId, pageable, model, accessToken);
        return "support_full_question_page";

    }

    @PostMapping("/support/questions/{questionId}")
    public String addCommentToQuestion(@PathVariable Integer questionId, Pageable pageable,
                                       @ModelAttribute FormAddComment comment, Model model,
                                       @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request add comment to question " + String.valueOf(questionId));
        service.addComment(questionId, comment, pageable, model, accessToken);
        return "support_full_question_page";
    }



    @GetMapping("/support/questions")
    public String pageAddQuestion(Model model,
                                  @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request page for add page");
        service.pageAddQuestion(model, accessToken);
        return "support_add_question";
    }

    @PostMapping("/support/questions")
    public void addQuestion(Model model,
                              @ModelAttribute FormCreateQuestion question,
                              @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                              HttpServletResponse httpServletResponse) {
        log.info("Request add new question");
        Integer questionId = service.addQuestion(question, model, accessToken);
        httpServletResponse.setStatus(302);
        httpServletResponse.setHeader("Location", "/support/questions/" + String.valueOf(questionId) + "/");
    }

    @PostMapping("/support/questions/{questionId}/delete")
    public String deleteQuestionById(@PathVariable Integer questionId,
                                     @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request remove question " + String.valueOf(questionId));
        service.removeQuestion(questionId, accessToken);
        return "redirect:/support";
    }
}
