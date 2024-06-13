package ts.myapp.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.services.ApiResponse;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/logout")
    public ApiResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return new ApiResponse<>(false, null, "Nie jesteś zalogowany.", null);
        }

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);


        return new ApiResponse<>(true, "Wylogowano pomyślnie", "Użytkownik został wylogowany.", null);
    }
}
