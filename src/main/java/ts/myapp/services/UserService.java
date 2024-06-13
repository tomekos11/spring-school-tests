package ts.myapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import ts.myapp.users.User;
import ts.myapp.users.UserRepository;

@Controller
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userRepository.findUserByUsername(currentUserName);

        return user;
    }
}
