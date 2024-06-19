package ts.myapp.tests;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ts.myapp.answers.Answer;
import ts.myapp.answers.repositories.AnswerRepository;
import ts.myapp.answers.repositories.AnswerUserAnswerRepository;
import ts.myapp.services.ApiResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerUserAnswerRepository answerUserAnswerRepository;

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

    public Boolean updateAnswers(Test newTest) {

        List<Answer> testAnswers = answerRepository.findAllAnswersByTestId(newTest.getId());
        List<Long> testAnswersIds = testAnswers.stream().map(el -> el.getId()).toList();

        List<Long> newTestAnswersIds = new ArrayList<>();

        newTest.getQuestions().stream()
                .forEach(question -> {
                    question.getAnswers().stream()
                            .forEach(answer -> {
                                if (testAnswersIds.contains(answer.getId())) {
//                                    aktualizowanie istniejącej odpowiedzi
                                    Answer asnwerToUpdate = testAnswers.stream().filter(el ->el.getId().equals(answer.getId())).findFirst().orElse(null);
                                    if (asnwerToUpdate != null) {
                                        asnwerToUpdate.setQuestion(question);
                                        asnwerToUpdate.setIsCorrect(answer.getIsCorrect());
                                        asnwerToUpdate.setAnswer(answer.getAnswer());
                                        answerRepository.save(asnwerToUpdate);
                                    }
                                } else {
//                                    tworzenie nowej odpowiedzi
                                    Answer newAnswer = new Answer();
                                    newAnswer.setQuestion(question);
                                    newAnswer.setIsCorrect(answer.getIsCorrect());
                                    newAnswer.setAnswer(answer.getAnswer());
                                    answerRepository.save(newAnswer);
                                }
//                                uzupelnianie listy do późniejszego usuwania odpowiedzi
                                newTestAnswersIds.add(answer.getId());
                            });
                });


        List<Long> answerIdsToDelete = testAnswersIds.stream()
                .filter(element -> !newTestAnswersIds.contains(element))
                .collect(Collectors.toList());


//        usuwanie answerów
        answerIdsToDelete.stream().forEach(idToDelete -> {
            answerUserAnswerRepository.deleteAnswerUserAnswerByAnswerId(idToDelete);
            answerRepository.deleteAnswerById(idToDelete);
        });

        return true;
    }
}
