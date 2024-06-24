package ts.myapp.views;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ts.myapp.groups.Group;
import ts.myapp.services.UserService;
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

    private final ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    public HomeController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @GetMapping("/")
    public String homePage(Model model) throws JsonProcessingException {

        User currentUser = userService.me();

        model.addAttribute("user", currentUser);

        currentUser.getAllUsersTests().forEach(userTest -> {

        });

        return "index";
    }


}