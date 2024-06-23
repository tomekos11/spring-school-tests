package ts.myapp.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ts.myapp.groups.Group;
import ts.myapp.groups.GroupRepository;
import ts.myapp.services.ApiResponse;
import ts.myapp.services.UserService;
import ts.myapp.users.User;
import ts.myapp.users.UserTest;
import ts.myapp.users.UserTestRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserTestRepository userTestRepository;

    private final ObjectMapper objectMapper;

    public TestRestController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/api/addTest/{groupId}/{testId}")
    public String addTestForAllUsers(@PathVariable Long groupId, @PathVariable Long testId) {
        User currentUser = userService.me();

        if (!currentUser.getRole().equals("ADMIN")) return "Blad";

        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) return "Blad";

        Test test = testRepository.findById(testId).orElse(null);
        if(test == null) return "Blad";

        group.getAllUsersFromThisGroup().forEach(user -> {
            if(!user.getAllUsersTestsIds().contains(test.getId())) {
                UserTest newUserTest = new UserTest();
                newUserTest.setUser(user);
                newUserTest.setBeginDate(null);
                newUserTest.setPointAmount(0);
                newUserTest.setTest(test);
                userTestRepository.save(newUserTest);
            }
        });
        return "Git";
    }

    @Transactional
    @DeleteMapping("/api/tests/{id}")
    public ApiResponse<ResponseMessage> deleteTest(@PathVariable Long id) throws JsonProcessingException, JSONException {
        User user = userService.me();

        String serializedUser = objectMapper.writeValueAsString(user);
        User deserializedUser = objectMapper.readValue(serializedUser, User.class);

        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessage("Użytkownicy mają przypisany ten test. Najpierw upewnij się, że rekordy user_tests są usunięte", "warning"));
        responseMessages.add(new ResponseMessage("Poprawnie usunięto test", "success"));

        Test test = testRepository.findById(id).orElse(null);

        if (test == null || !user.getRole().equals("ADMIN")) {
            return new ApiResponse<>(false, null, "Brak wymaganych uprawnień lub test nie istnieje", "Unauthorized or Test not found");
        }

        Integer messageId;
        if (test.getUsers() == null || test.getUsers().isEmpty()) {
            testRepository.delete(test);
            messageId = 1;
            return new ApiResponse<>(true, responseMessages.get(messageId), "", null);
        } else {
            messageId = 0;
            return new ApiResponse<>(false, responseMessages.get(messageId), "", null);
        }

    }

    @AllArgsConstructor
    class ResponseMessage {
        @Getter
        private String message;
        @Getter
        private String type;
    }
}
