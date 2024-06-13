package ts.myapp.answers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ts.myapp.answers.repositories.AnswerRepository;
import ts.myapp.answers.repositories.AnswerUserAnswerRepository;
import ts.myapp.answers.repositories.UserAnswerRepository;
import ts.myapp.services.UserService;
import ts.myapp.users.User;

import java.util.Collections;
import java.util.List;

@RestController
public class UserAnswerController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerUserAnswerRepository answerUserAnswerRepository;

    @PatchMapping("/answer/{id}")
    public String answer(@PathVariable Long id, @RequestBody List<Long> request) throws JsonProcessingException {
        User user = userService.me();

        UserAnswer userAnswer = userAnswerRepository.findById(id).orElse(null);

        if (userAnswer == null) {
            return "Nie znaleziono odpowiedzi";
        }

        if (!userAnswer.getUser().equals(user)) {
            return "To nie twój answer";
        }

        if (userAnswer.getIsCorrect() != null || request.size() > userAnswer.getQuestion().getAnswers().size() ) {
            return "Nie oszukuj!";
        }

        List<Long> ids = userAnswer.getQuestion().getAnswers().stream()
                .map(el -> el.getId())
                .toList();

        for (Long idInRequest : request) {
            if (!ids.contains(idInRequest)){
                userAnswer.setIsCorrect(false);
                return "Za oszustwo tracisz punkt";
            }
        }


        for (Long idInRequest : request) {
            Answer answer = answerRepository.findById(idInRequest).orElse(null);
            if (answer != null) {
                AnswerUserAnswer answerUserAnswer = new AnswerUserAnswer();
                answerUserAnswer.setAnswer(answer);
                answerUserAnswer.setUserAnswer(userAnswer);
                answerUserAnswerRepository.save(answerUserAnswer);
            }
        }

        System.out.println(ids);
        System.out.println(request);

        ids = userAnswer.getQuestion().getAnswers().stream()
               .filter(el -> el.getIsCorrect())
                .map(el -> el.getId())
                .toList();

        if (ids.containsAll(request) && request.containsAll(ids)) {
            userAnswer.setIsCorrect(true);
        } else {
            userAnswer.setIsCorrect(false);
        }

        userAnswerRepository.save(userAnswer);
        return "XD";
    }

    @PostMapping("/group/{groupId}/test/{testId}")
    public String addEmptyAnswers(@PathVariable Long groupId, @PathVariable Long testId) throws JsonProcessingException {
        Boolean added = userService.addEmptyAnswers(groupId, testId);

        if(added) return "najs";
        else return "lipa";
    }

}

