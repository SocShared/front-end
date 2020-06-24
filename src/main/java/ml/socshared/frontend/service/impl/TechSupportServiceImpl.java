package ml.socshared.frontend.service.impl;

import lombok.RequiredArgsConstructor;
import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.BreadcrumbElement;
import ml.socshared.frontend.domain.model.Breadcrumbs;
import ml.socshared.frontend.domain.tech_support.QuestionsPage;
import ml.socshared.frontend.domain.tech_support.model.form.FormAddComment;
import ml.socshared.frontend.domain.tech_support.model.form.FormCreateQuestion;
import ml.socshared.frontend.domain.tech_support.Comment;
import ml.socshared.frontend.domain.tech_support.FullQuestion;
import ml.socshared.frontend.domain.tech_support.ShortQuestion;
import ml.socshared.frontend.domain.tech_support.request.QuestionCreateRequest;
import ml.socshared.frontend.service.TechSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TechSupportServiceImpl implements TechSupportService {

    private final GatewayServiceClient client;

    @Override
    public String questionsPage(Pageable page, Model model, String token) {
        QuestionsPage qustions =  client.getQuestionsPage(page.getPageNumber(), page.getPageSize(), "Bearer " + token);

        model.addAttribute("questions_page", qustions);
        model.addAttribute("bread", new Breadcrumbs(Collections.emptyList(), "Техническая поддержка"));
        return "support_questions_page";
    }

    @Override
    public String fullQuestionPage(Integer qid, Pageable pageable, Model model, String token) {
        FullQuestion q = client.getFullQuestionPage(qid, pageable.getPageNumber(), pageable.getPageSize(), "Bearer " + token);
        model.addAttribute("question", q);
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("support", "Техническая поддержка")),
                q.getTitle()));
       model.addAttribute("formAddComment", new FormAddComment());
        return "support_full_question_page";
    }

    @Override
    public String pageAddQuestion(Model model, String token) {
        model.addAttribute("formAddQuestion", new FormCreateQuestion());
        model.addAttribute("bread", new Breadcrumbs(Arrays.asList(
                new BreadcrumbElement("support", "Техническая поддержка")),
                "Создание нового вопроса"));
        return "support_add_question";
    }

    @Override
    public Integer addQuestion(FormCreateQuestion question, Model model, String token) {
        QuestionCreateRequest qr = new QuestionCreateRequest();
        qr.setTitle(question.getTitle());
        qr.setText(question.getText());
        return client.addQuestion(qr, "Bearer " + token);
    }

    @Override
    public String addComment(Integer questionId, FormAddComment formComment, Pageable pageable, Model model, String token) {
        Comment comment = new Comment();
        comment.setText(formComment.getText());
        client.addCommentToQuestion(questionId, comment, "Bearer " + token);
        return "support_full_question_page";
    }


    @Override
    public String removeQuestion(Integer questionId, String token) {
        client.deleteQuestionById(questionId, "Bearer " + token);
        return "ok";
    }
}
