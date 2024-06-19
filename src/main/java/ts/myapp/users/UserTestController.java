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

    public UserTestController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @GetMapping("/group/{groupId}/test/{testId}")
    public String test(Model model, @PathVariable Long groupId, @PathVariable Long testId) throws JsonProcessingException {
        User user = userService.me();

        List<Group> groups = user.getGroups().stream().map(el -> el.getGroup()).toList();

        System.out.println(objectMapper.writeValueAsString(groups));

        boolean groupExists = groups.stream().anyMatch(group -> group.getId() == groupId);
        System.out.println(groupExists);
        if (!groupExists) {
            return "no_required_permissions";
        }

        UserTest userTest = userTestRepository.findTestById(testId);
        GroupTest groupTest = groupTestRepository.findTest(groupId, testId);



        if (userTest == null || groupTest == null) {
            System.out.println("TU");
            return "no_required_permissions";
        }

        List<Question> testQuestions = userTest.getTest().getQuestions();

        UserAnswer lastQuestion = user.getAnswers().stream()
                .filter(answer -> answer.getIsCorrect() == null)
                .filter(answer -> testQuestions.stream().anyMatch(question -> question.equals(answer.getQuestion())))
                .findFirst().orElse(null);

        List<UserAnswer> alreadyAnsweredQuestions = user.getAnswers().stream()
                .filter(answer -> answer.getIsCorrect() != null && testQuestions.stream().anyMatch(question -> question.equals(answer.getQuestion())))
                        .toList();

        System.out.println(objectMapper.writeValueAsString(lastQuestion));
//        userAnswersWithoutAnswerGiven.stream().filter()

        String serializedUserTest = objectMapper.writeValueAsString(userTest);
        UserTest deserializedUserTest = objectMapper.readValue(serializedUserTest, UserTest.class);

        String serializedGroupTest = objectMapper.writeValueAsString(groupTest);
        GroupTest deserializedGroupTest = objectMapper.readValue(serializedGroupTest, GroupTest.class);

        String serializedAlreadyAnsweredQuestions = objectMapper.writeValueAsString(alreadyAnsweredQuestions);
        List<UserAnswer> deserializedAlreadyAnsweredQuestions = objectMapper.readValue(serializedAlreadyAnsweredQuestions, new TypeReference<List<UserAnswer>>() {});

        String serializedUser = objectMapper.writeValueAsString(user);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        model.addAttribute("user", deserializedUser);
        model.addAttribute("userTest", deserializedUserTest);
        model.addAttribute("groupTest", deserializedGroupTest);
        model.addAttribute("alreadyAnsweredQuestions", deserializedAlreadyAnsweredQuestions);

        if (lastQuestion != null) {
            model.addAttribute("lastQuestion", lastQuestion);
        } else {
            System.out.println("Użytkownik nie dał jeszcze żadnej odpowiedzi");
        }

        return "test";
    }
}
