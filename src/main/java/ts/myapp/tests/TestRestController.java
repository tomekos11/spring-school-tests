package ts.myapp.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.groups.Group;
import ts.myapp.groups.GroupRepository;
import ts.myapp.services.UserService;
import ts.myapp.users.User;
import ts.myapp.users.UserTest;
import ts.myapp.users.UserTestRepository;

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
}
