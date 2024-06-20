package ts.myapp.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.answers.Answer;
import ts.myapp.answers.UserAnswer;
import ts.myapp.groups.Group;
import ts.myapp.groups.GroupTest;
import ts.myapp.groups.GroupTestRepository;
import ts.myapp.questions.Question;
import ts.myapp.questions.QuestionRepository;
import ts.myapp.services.UserService;
import ts.myapp.tests.Test;
import ts.myapp.tests.TestRepository;
import ts.myapp.users.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserTestController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper;
    @Autowired
    private UserTestRepository userTestRepository;
    @Autowired
    private GroupTestRepository groupTestRepository;
    @Autowired
    private UserTestService userTestService;

    public UserTestController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @GetMapping("/group/{groupId}/test/{testId}")
    public String test(Model model, @PathVariable Long groupId, @PathVariable Long testId) throws JsonProcessingException {
        User user = userService.me();

        List<Group> groups = user.getGroups().stream().map(el -> el.getGroup()).toList();

        boolean groupExists = groups.stream().anyMatch(group -> group.getId() == groupId);

        model.addAttribute("user", user);

        if (!groupExists) {
            System.out.println("BLAD1");
            return "no_required_permissions";
        }

        GroupTest groupTest = groupTestRepository.findTest(groupId, testId);



//        if (userTest == null || groupTest == null) {
        if (groupTest == null) {
            System.out.println("BLAD2");
            return "no_required_permissions";
        }

        LocalDateTime now = LocalDateTime.now();

        if(!groupTest.getBeginDate().isBefore(now)) {
            System.out.println("blad time 1");
            model.addAttribute("error", "Jest za wcześnie na rozwiązywanie testu!");
        } else if (!groupTest.getEndDate().isAfter(now)){
            System.out.println("blad time 2");
            model.addAttribute("error", "Jest za późno na rozwiązywanie testu!");
        }

        List<Question> testQuestions = groupTest.getTest().getQuestions();

        UserAnswer lastQuestion = user.getAnswers().stream()
                .filter(answer -> answer.getIsCorrect() == null)
                .filter(answer -> testQuestions.stream().anyMatch(question -> question.equals(answer.getQuestion())))
                .findFirst().orElse(null);

        List<UserAnswer> alreadyAnsweredQuestions = user.getAnswers().stream()
                .filter(answer -> answer.getIsCorrect() != null && testQuestions.stream().anyMatch(question -> question.equals(answer.getQuestion())))
                        .toList();

        System.out.println(objectMapper.writeValueAsString(lastQuestion));

        String serializedGroupTest = objectMapper.writeValueAsString(groupTest);
        GroupTest deserializedGroupTest = objectMapper.readValue(serializedGroupTest, GroupTest.class);

        String serializedAlreadyAnsweredQuestions = objectMapper.writeValueAsString(alreadyAnsweredQuestions);
        List<UserAnswer> deserializedAlreadyAnsweredQuestions = objectMapper.readValue(serializedAlreadyAnsweredQuestions, new TypeReference<List<UserAnswer>>() {});

        String serializedUser = objectMapper.writeValueAsString(user);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        long correctAnswerAmount = deserializedAlreadyAnsweredQuestions.stream().filter(el -> el.getIsCorrect()).count();
        double correctAnswerPercentage =  (double) (correctAnswerAmount * 100 / groupTest.getTest().getQuestions().size());

        model.addAttribute("user", deserializedUser);
        model.addAttribute("groupTest", deserializedGroupTest);
        model.addAttribute("alreadyAnsweredQuestions", deserializedAlreadyAnsweredQuestions);
        model.addAttribute("correctAnswerAmount", correctAnswerAmount);
        model.addAttribute("correctAnswerPercentage", (int) correctAnswerPercentage);
        model.addAttribute("mark", userTestService.calculateGrade(correctAnswerPercentage));

        if (lastQuestion != null) {
            model.addAttribute("lastQuestion", lastQuestion);
        } else {
            System.out.println("Użytkownik nie dał jeszcze żadnej odpowiedzi");
        }

        return "test";
    }
}
