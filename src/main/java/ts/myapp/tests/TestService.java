package ts.myapp.tests;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.services.ApiResponse;
import org.json.JSONObject;


@RestController
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public JSONObject createTest(String name, String alias) throws JSONException {

        Test test = new Test();
        test.setName(name);
        test.setAlias(alias);
        test.setGroupTests(null);
        test.setQuestions(null);
        test.setUsers(null);

        testRepository.save(test);
        System.out.println("Nazwa: " + name + ", Alias: " + alias);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test", test);
        jsonObject.put("message", "Test został pomyślnie utworzony");

        System.out.println(test);

        return jsonObject;
    }
}
