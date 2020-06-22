package ml.socshared.frontend.service;


import ml.socshared.frontend.domain.tech_support.model.form.FormAddComment;
import ml.socshared.frontend.domain.tech_support.model.form.FormCreateQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.UUID;

public interface TechSupportService {

    String questionsPage(Pageable page, Model model, String token);
    String fullQuestionPage(Integer qid, Pageable pageable, Model model, String token);
    String pageAddQuestion(Model model, String token);
    Integer addQuestion(FormCreateQuestion question, Model model, String token);
    String addComment(Integer questionId, FormAddComment formComment, Pageable pageable, Model model, String token);
    String removeQuestion(Integer questionId, String token);

}
