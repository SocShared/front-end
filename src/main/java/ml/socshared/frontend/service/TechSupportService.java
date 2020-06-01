package ml.socshared.frontend.service;


import ml.socshared.frontend.domain.model.tech_support.Comment;
import ml.socshared.frontend.domain.model.tech_support.FullQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface TechSupportService {

    String questionsPage(Pageable page, Model model, String token);
    String fullQuestionPage(Integer qid, Pageable pageable, Model model, String token);
    String addQuestion(FullQuestion question, Model model, String token);
    String addComment(Comment comment, Model model, String token);
    String removeComment(Integer questionId, Integer commentId, Model model, String token);
    String removeQuestion(Integer questionId, Model model, String token);

}
