package ts.myapp.answers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ts.myapp.answers.repositories.AnswerRepository;
import ts.myapp.answers.repositories.AnswerSummary;
import ts.myapp.groups.Group;
import ts.myapp.groups.GroupTest;
import ts.myapp.groups.GroupTestRepository;
import ts.myapp.services.UserService;
import ts.myapp.users.User;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AnswerSummaryController {
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerSummary answerSummary;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ts.myapp.answers.repositories.AnswerDetailed answerDetailed;
    @Autowired
    private GroupTestRepository groupTestRepository;

    @PatchMapping("/group/{groupId}/test/{testId}/answer/{answerId}")
    public String answer(@PathVariable Long groupId, @PathVariable Long testId, @PathVariable Long answerId, @RequestBody List<Long> request) throws JsonProcessingException {
        User user = userService.me();

        List<Group> groups = user.getGroups().stream().map(el -> el.getGroup()).toList();
        boolean groupExists = groups.stream().anyMatch(group -> group.getId() == groupId);
        if(!groupExists) return "Użytkownik nie należy do tej grupy";

        GroupTest groupTest = groupTestRepository.findTest(groupId, testId);

        if (groupTest == null) {
            return "Nie znaleziono tego testu dla tej grupy";
        }

        LocalDateTime now = LocalDateTime.now();

        if(!groupTest.getBeginDate().isBefore(now)) {
            return "Za wcześnie";
        } else if (!groupTest.getEndDate().isAfter(now)){
            return "Za późno";
        }

        ts.myapp.answers.AnswerSummary answerSummary = this.answerSummary.findById(answerId).orElse(null);

        if (answerSummary == null) {
            return "Nie znaleziono odpowiedzi";
        }

        if (!answerSummary.getUserTest().getUser().equals(user)) {
            return "To nie twój answer";
        }

        if (answerSummary.getIsCorrect() != null || request.size() > answerSummary.getQuestion().getAnswers().size() ) {
            return "Nie oszukuj!";
        }

        List<Long> ids = answerSummary.getQuestion().getAnswers().stream()
                .map(el -> el.getId())
                .toList();

        for (Long idInRequest : request) {
            if (!ids.contains(idInRequest)){
                answerSummary.setIsCorrect(false);
                return "Za oszustwo tracisz punkt";
            }
        }


        for (Long idInRequest : request) {
            Answer answer = answerRepository.findById(idInRequest).orElse(null);
            if (answer != null) {
                AnswerDetailed answerDetailed = new AnswerDetailed();
                answerDetailed.setAnswer(answer);
                answerDetailed.setAnswerSummary(answerSummary);
                this.answerDetailed.save(answerDetailed);
            }
        }

        System.out.println(ids);
        System.out.println(request);

        ids = answerSummary.getQuestion().getAnswers().stream()
               .filter(el -> el.getIsCorrect())
                .map(el -> el.getId())
                .toList();

        if (ids.containsAll(request) && request.containsAll(ids)) {
            answerSummary.setIsCorrect(true);
        } else {
            answerSummary.setIsCorrect(false);
        }

        this.answerSummary.save(answerSummary);
        return "XD";
    }

    @PostMapping("/group/{groupId}/test/{testId}")
    public String addEmptyAnswers(@PathVariable Long groupId, @PathVariable Long testId) throws JsonProcessingException {
        Boolean added = userService.addEmptyAnswers(groupId, testId);

        if(added) return "najs";
        else return "lipa";
    }

}

