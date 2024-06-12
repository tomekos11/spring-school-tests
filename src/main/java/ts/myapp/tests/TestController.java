package ts.myapp.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.groups.Group;
import ts.myapp.services.ApiResponse;
import ts.myapp.users.User;
import ts.myapp.users.UserGroup;
import ts.myapp.users.UserRepository;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("api/tests")
    public ApiResponse<List<UserGroup>> tests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        User user = userRepository.findUserByUsername(currentUserName);

        List<UserGroup> groups = user.getGroups();

//        for (UserGroup group : groups) {
//            group.setUser(null);
//            group.setGroup(null);
//        }
//
//        user.setAnswers(null);

        return new ApiResponse<>(true, groups, "Zwrócono dane użytkownika", null);
    }
}
