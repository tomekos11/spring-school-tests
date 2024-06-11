package ts.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ts.myapp.users.User;
import ts.myapp.users.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Pobierz użytkownika na podstawie nazwy użytkownika
        User user = userRepository.findUserByUsername(username);

        // Sprawdź czy użytkownik istnieje i hasło się zgadza
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("zalogowano uzytkownika");
            return new UsernamePasswordAuthenticationToken(username, password, getAuthorities());
        } else {
            System.out.println("nie udalo sie zalogowac");
            // Rzucenie wyjątku AuthenticationException w przypadku niepowodzenia uwierzytelnienia
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lista uprawnień dla użytkownika
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Dodawanie uprawnień do listy
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorities;
    }
}