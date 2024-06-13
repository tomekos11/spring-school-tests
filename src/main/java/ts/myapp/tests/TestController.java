package ts.myapp.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.services.UserService;
import ts.myapp.users.User;

@RestController
public class TestController {
    @Autowired
    TestRepository testRepository;
    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper;

    public TestController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @GetMapping("/test/{id}")
    public String test(Model model, @PathVariable Long id) throws JsonProcessingException {
        User user = userService.me();

        Test test = testRepository.findTestById(id);

//        TODO sprawdzic czy moze wgl ten test otworzyc
//        for (group : user.getGroups()) {
//
//        }

        String serializedTest = objectMapper.writeValueAsString(test);
        Test deserializedTest = objectMapper.readValue(serializedTest, Test.class);

        model.addAttribute("user", user);
        model.addAttribute("test", deserializedTest);


        return "test";
    }
}
