package ml.socshared.frontend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.frontend.domain.tech_support.model.form.FormAddComment;
import ml.socshared.frontend.domain.tech_support.model.form.FormCreateQuestion;
import ml.socshared.frontend.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
      return "support_questions_page :: content";
    }

    @GetMapping("/support/questions/{questionId}")
    public String fullQuestionPage(@PathVariable Integer questionId, Pageable pageable,
                                   Model model,
                                   @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("request get page of full question " + String.valueOf(questionId));
        service.fullQuestionPage(questionId, pageable, model, accessToken);
        return "support_full_question_page :: content";

    }

    @PostMapping("/support/questions/{questionId}")
    public String addCommentToQuestion(@PathVariable Integer questionId, Pageable pageable,
                                       @ModelAttribute FormAddComment comment, Model model,
                                       @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request add comment to question " + String.valueOf(questionId));
        service.addComment(questionId, comment, pageable, model, accessToken);
        fullQuestionPage(questionId, pageable, model, accessToken);
        return "support_full_question_page :: content";
    }



    @GetMapping("/support/questions")
    public String pageAddQuestion(Model model,
                                  @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request page for add page");
        service.pageAddQuestion(model, accessToken);
        return "support_add_question :: content";
    }

    @PostMapping("/support/questions")
    public String addQuestion(Model model,
                              @ModelAttribute FormCreateQuestion question,
                              @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken,
                              HttpServletResponse httpServletResponse) {
        log.info("Request add new question");
        Integer questionId = service.addQuestion(question, model, accessToken);
        fullQuestionPage(questionId, PageRequest.of(0, 20), model, accessToken);
        return "support_full_question_page :: content";
    }

    @PostMapping("/support/questions/{questionId}/delete")
    public String deleteQuestionById(@PathVariable Integer questionId, Model model,
                                     @CookieValue(name = "JWT_AT", defaultValue = "") String accessToken) {
        log.info("Request remove question " + String.valueOf(questionId));
        service.removeQuestion(questionId, accessToken);
        service.questionsPage(PageRequest.of(0, 20) ,model, accessToken);
        return "support_questions_page :: content";
    }
}
