package ts.myapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import ts.myapp.answers.repositories.AnswerSummary;
import ts.myapp.groups.GroupTest;
import ts.myapp.groups.GroupTestRepository;
import ts.myapp.questions.Question;
import ts.myapp.users.User;
import ts.myapp.users.UserRepository;
import ts.myapp.users.UserTest;
import ts.myapp.users.UserTestRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTestRepository userTestRepository;
    @Autowired
    private GroupTestRepository groupTestRepository;
    @Autowired
    private AnswerSummary answerSummary;

    public User me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        return user;
    }

    public boolean addEmptyAnswers(Long groupId, Long testId) {
        User user = this.me();

        UserTest userTest = userTestRepository.findByTestIdAndUserId(testId, user.getId());
        GroupTest groupTest = groupTestRepository.findTest(groupId, testId);

        System.out.println(userTest.getBeginDate());

        if (userTest == null || userTest.getBeginDate() != null || !user.getGroupsAfterPivot().contains(groupTest.getGroup())) {
            System.out.println("blad user service 1");
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        if(!groupTest.getBeginDate().isBefore(now)) {
            System.out.println("blad user service 2");
            return false;
        } else if (!groupTest.getEndDate().isAfter(now)){
            System.out.println("blad user service 3");
            return false;
        }

//        Losowanie pytań
        List<Question> questions = userTest.getTest().getQuestions();
        Collections.shuffle(questions);

        for(Question question : questions) {
            ts.myapp.answers.AnswerSummary answerSummary = new ts.myapp.answers.AnswerSummary();
            answerSummary.setQuestion(question);
            answerSummary.setUserTest(userTest);
            this.answerSummary.save(answerSummary);
        }

        userTest.setBeginDate(now);
        userTestRepository.save(userTest);

        return true;
    }
}
