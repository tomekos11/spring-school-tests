package ts.myapp.users;

import org.springframework.stereotype.Service;

@Service
public class UserTestService {

    String calculateGrade(Double percent) {
        if (percent < 50) return "2.0";
        else if (percent < 60) return "3.0";
        else if (percent < 70) return "3.5";
        else if (percent < 80) return "4.0";
        else if (percent < 90) return "4.5";
        else return "5.0";
    }
}
