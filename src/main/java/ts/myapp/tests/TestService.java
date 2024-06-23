package ts.myapp.tests;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ts.myapp.answers.Answer;
import ts.myapp.answers.repositories.AnswerRepository;
import ts.myapp.answers.repositories.AnswerUserAnswerRepository;
import ts.myapp.questions.Question;
import ts.myapp.questions.QuestionRepository;
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
    @Autowired
    private QuestionRepository questionRepository;

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

        List<Question> testQuestions = questionRepository.findQuestionsByTestId(newTest.getId());
        List<Long> testQuestionIds = testQuestions.stream().map(el -> el.getId()).toList();


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
                                } else if (testQuestionIds.contains(question.getId())) {
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
                .toList();


//        usuwanie answerów
        answerIdsToDelete.stream().forEach(idToDelete -> {
            answerUserAnswerRepository.deleteByAnswerId(idToDelete);
            answerRepository.deleteById(idToDelete);
        });

        return true;
    }

    public Boolean updateQuestions(Test test, Test newTest, MultipartFile image) {

        List<Question> testQuestions = test.getQuestions();
        List<Long> testQuestionIds = testQuestions.stream().map(el -> el.getId()).toList();

        List<Long> newTestQuestionIds = new ArrayList<>();

        newTest.getQuestions().stream()
                .forEach(question -> {
                    System.out.println(question);
                    if (testQuestionIds.contains(question.getId())) {
                        Question questionToUpdate = testQuestions.stream().filter(el ->el.getId().equals(question.getId())).findFirst().orElse(null);
                        if (questionToUpdate != null) {
                            questionToUpdate.setContent(question.getContent());
                            questionToUpdate.setImage("TODO");
                            questionToUpdate.setPointAmount(question.getPointAmount());
                            questionRepository.save(questionToUpdate);
                        }
                    } else {
                        Question newQuestion = new Question();

                        newQuestion.setTest(test);
                        newQuestion.setAnswers(new ArrayList<>());
                        newQuestion.setContent(question.getContent());
                        newQuestion.setImage("TODO");
                        newQuestion.setPointAmount(question.getPointAmount());
                        questionRepository.save(newQuestion);

                        System.out.println("Dodaje nowe odpowiedzi");
                        question.getAnswers().forEach(el -> {
                            Answer newAnswer = new Answer();
                            newAnswer.setId(el.getId());
                            newAnswer.setQuestion(newQuestion);
                            newAnswer.setIsCorrect(el.getIsCorrect());
                            newAnswer.setAnswer(el.getAnswer());
                            answerRepository.save(newAnswer);
                        });
                    }
                    newTestQuestionIds.add(question.getId());
                });

        List<Long> questionIdsToDelete = testQuestionIds.stream()
            .filter(element -> !newTestQuestionIds.contains(element))
                    .toList();


//        usuwanie pytan
        questionIdsToDelete.stream().forEach(idToDelete -> {
            answerUserAnswerRepository.deleteByQuestionId(idToDelete);
            answerRepository.deleteByQuestionId(idToDelete);
            questionRepository.deleteById(idToDelete);
        });

        return true;
    }
}
