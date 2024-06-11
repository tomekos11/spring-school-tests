package ts.myapp.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.services.ApiResponse;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("api/me")
    public ApiResponse<User> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();


        User user = userRepository.findUserByUsername(currentUserName);

        return new ApiResponse<>(true, user, "Zwrócono dane użytkownika", null);
    }
}
