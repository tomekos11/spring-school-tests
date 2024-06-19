package ts.myapp.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ts.myapp.answers.Answer;
import ts.myapp.answers.repositories.AnswerRepository;
import ts.myapp.answers.repositories.AnswerUserAnswerRepository;
import ts.myapp.questions.Question;
import ts.myapp.services.UserService;
import ts.myapp.users.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TestController {
    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    private final ObjectMapper objectMapper;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerUserAnswerRepository answerUserAnswerRepository;

    public TestController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/tests/create")
    public ModelAndView showCreateTestForm(Model model) throws JsonProcessingException {
        User currentUser = userService.me();

        String serializedUser = objectMapper.writeValueAsString(currentUser);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);


        ModelAndView modelAndView = new ModelAndView("create-test");
        modelAndView.addObject("user", deserializedUser);

        return modelAndView;
    }

    @PostMapping("/api/tests/create")
    public Object createTest(@RequestParam("name") String name, @RequestParam("alias") String alias, Model model, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        User currentUser = userService.me();

        String serializedUser = objectMapper.writeValueAsString(currentUser);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        if (!currentUser.getRole().equals("ADMIN")){
            ModelAndView modelAndView = new ModelAndView("no_required_permissions");
            modelAndView.addObject("user", deserializedUser);
            return modelAndView;
        }

        String message;
        Test test;

        try {
            JSONObject serviceResponse = testService.createTest(name, alias);
            message = (String) serviceResponse.get("message");
            test = (Test) serviceResponse.get("test");

            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("test", test);
            redirectAttributes.addFlashAttribute("user", deserializedUser);

            return "redirect:/tests/" + test.getId();

        } catch (Exception e) {
            System.out.println("blad");
            ModelAndView modelAndView = new ModelAndView("create-test");
            modelAndView.addObject("error", "Wystąpił błąd podczas tworzenia testu: " + e.getMessage());
            modelAndView.addObject("user", deserializedUser);
            return modelAndView;
        }
    }

    @GetMapping("/tests/{id}")
    public ModelAndView showEditTestForm(@PathVariable Long id, Model model) throws JsonProcessingException {
        User currentUser = userService.me();

        String serializedUser = objectMapper.writeValueAsString(currentUser);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        Test test = testRepository.findById(id).orElse(null);

        String serializedTest = objectMapper.writeValueAsString(test);
        Test deserializedTest = objectMapper.readValue(serializedTest, Test.class);

        ModelAndView modelAndView = new ModelAndView("edit-test");

        modelAndView.addObject("user", deserializedUser);
        modelAndView.addObject("test", deserializedTest);
        modelAndView.addObject("answerMaxId", answerRepository.findMaxId());


        return modelAndView;
    }

    @Transactional
    @PatchMapping("/api/tests/{id}")
    public ModelAndView editTest(@PathVariable Long id, @RequestParam("test") String rqTest, Model model, RedirectAttributes redirectAttributes, MultipartFile image) throws JsonProcessingException {
        User user = userService.me();

        String serializedUser = objectMapper.writeValueAsString(user);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        Test test = testRepository.findById(id).orElse(null);

        if (test == null || !user.getRole().equals("ADMIN") ){
            ModelAndView modelAndView = new ModelAndView("no_required_permissions");
            modelAndView.addObject("user", deserializedUser);
            return modelAndView;
        }

        Test jsonRqTest = objectMapper.readValue(rqTest, Test.class);
//        dodaj odpowiedzi do bazy

        testService.updateAnswers(jsonRqTest);


//        test.setName(jsonRqTest.getName());
//        test.setAlias(jsonRqTest.getAlias());
//        test.setQuestions(jsonRqTest.getQuestions());
//        testRepository.save(test);

        String serializedTest = objectMapper.writeValueAsString(test);
        Test deserializedTest = objectMapper.readValue(serializedTest, Test.class);

        ModelAndView modelAndView = new ModelAndView("edit-test");
        modelAndView.addObject("message", "Zaktualizowano test");
        modelAndView.addObject("test", deserializedTest);
        modelAndView.addObject("user", deserializedUser);
        return modelAndView;
    }

    @GetMapping("/tests/manage")
    public ModelAndView showTestsList(Model model) throws JsonProcessingException {
        User currentUser = userService.me();

        String serializedUser = objectMapper.writeValueAsString(currentUser);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        List<Test> tests = testRepository.findAll();

//        String serializedTest = objectMapper.writeValueAsString(tests);
//        Test deserializedTest = objectMapper.readValue(serializedTest, Test.class);

        ModelAndView modelAndView = new ModelAndView("tests-manage");

        modelAndView.addObject("user", deserializedUser);
        modelAndView.addObject("tests", tests);


        return modelAndView;
    }

}
