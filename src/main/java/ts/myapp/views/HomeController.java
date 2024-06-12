package ts.myapp.views;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ts.myapp.groups.Group;
import ts.myapp.tests.TestController;
import ts.myapp.users.UserController;
import ts.myapp.users.User;
import ts.myapp.users.UserGroup;
import ts.myapp.users.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserController userController;
    @Autowired
    private TestController testController;
    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public HomeController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @GetMapping("/")
    public String homePage(Model model) throws JsonProcessingException {
        // Wywołaj metodę me() z kontrolera UserController, aby pobrać dane użytkownika
        User currentUser = userRepository.findUserByUsername("admin");
//        User currentUser = userController.me();

        String serializedUser = objectMapper.writeValueAsString(currentUser);
        User userr = objectMapper.readValue(serializedUser, User.class);

        String serializedTests = objectMapper.writeValueAsString(testController.tests().getData());
        List<UserGroup> testss = objectMapper.readValue(serializedTests, new TypeReference<List<UserGroup>>() {});


        // Przekaż dane użytkownika do widoku Thymeleafa
        model.addAttribute("user", userr);
        model.addAttribute("tests", testss);

        return "index"; // Zwraca nazwę pliku HTML Thymeleaf (bez rozszerzenia)
    }


}