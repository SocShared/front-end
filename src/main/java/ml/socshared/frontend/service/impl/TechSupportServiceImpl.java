package ml.socshared.frontend.service.impl;

import ml.socshared.frontend.client.GatewayServiceClient;
import ml.socshared.frontend.domain.model.tech_support.Comment;
import ml.socshared.frontend.domain.model.tech_support.FullQuestion;
import ml.socshared.frontend.domain.model.tech_support.QuestionResponse;
import ml.socshared.frontend.domain.model.tech_support.ShortQuestion;
import ml.socshared.frontend.service.TechSupportService;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TechSupportServiceImpl implements TechSupportService {

    GatewayServiceClient client;

    @Autowired
    TechSupportServiceImpl(GatewayServiceClient client) {
        this.client = client;
    }

    @Override
    public String questionsPage(Pageable page, Model model, String token) {
        Page<ShortQuestion> qustions =  client.getQuestionsPage(page.getPageNumber(), page.getPageSize(), token);

        model.addAttribute("questions_list", qustions.getContent());
        return "support_questions_page";
    }

    @Override
    public String fullQuestionPage(Integer qid, Pageable pageable, Model model, String token) {
        FullQuestion q = client.getFullQuestionPage(qid, pageable.getPageNumber(), pageable.getPageSize(), token);
        model.addAttribute("question", q);
        return "support_full_question_page";
    }

    @Override
    public String addQuestion(FullQuestion question, Model model, String token) {
        return null;
    }

    @Override
    public String addComment(Comment comment, Model model, String token) {
        return null;
    }

    @Override
    public String removeComment(Integer questionId, Integer commentId, Model model, String token) {
        return null;
    }

    @Override
    public String removeQuestion(Integer questionId, Model model, String token) {
        return null;
    }
}
