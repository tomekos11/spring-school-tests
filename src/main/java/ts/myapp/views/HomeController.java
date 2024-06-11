package ts.myapp.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ts.myapp.users.UserController;
import ts.myapp.users.User;

@Controller
public class HomeController {

    @Autowired
    private UserController userController;

    @GetMapping("/")
    public String homePage(Model model) {
        // Wywołaj metodę me() z kontrolera UserController, aby pobrać dane użytkownika
        User currentUser = userController.me().getData();

        // Przekaż dane użytkownika do widoku Thymeleafa
        model.addAttribute("user", currentUser);

        return "index"; // Zwraca nazwę pliku HTML Thymeleaf (bez rozszerzenia)
    }


}