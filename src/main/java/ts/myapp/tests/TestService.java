package ts.myapp.tests;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ts.myapp.answers.Answer;
import ts.myapp.answers.repositories.AnswerRepository;
import ts.myapp.answers.repositories.AnswerDetailed;
import ts.myapp.questions.Question;
import ts.myapp.questions.QuestionRepository;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerDetailed answerDetailed;
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


        System.out.println(newTest);
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
            answerDetailed.deleteByAnswerId(idToDelete);
            answerRepository.deleteById(idToDelete);
        });

        return true;
    }

    public Boolean updateQuestions(Test test, Test newTest) {

        List<Question> testQuestions = test.getQuestions();
        List<Long> testQuestionIds = testQuestions.stream().map(el -> el.getId()).toList();

        List<Long> newTestQuestionIds = new ArrayList<>();

        newTest.getQuestions().stream()
                .forEach(question -> {
                    if (testQuestionIds.contains(question.getId())) {
                        Question questionToUpdate = testQuestions.stream().filter(el ->el.getId().equals(question.getId())).findFirst().orElse(null);
                        if (questionToUpdate != null) {
                            questionToUpdate.setContent(question.getContent());
                            System.out.println(isBase64(question.getImage()));
                            if (isBase64(question.getImage())) {
                                questionToUpdate.setImage(this.createImage(question.getImage(), questionToUpdate.getId()));
                            }
                            questionToUpdate.setPointAmount(question.getPointAmount());
                            questionRepository.save(questionToUpdate);
                        }
                    } else {
                        Question newQuestion = new Question();

                        newQuestion.setTest(test);
                        newQuestion.setAnswers(new ArrayList<>());
                        newQuestion.setContent(question.getContent());
                        System.out.println(isBase64(question.getImage()));
                        if (isBase64(question.getImage())) {
                            newQuestion.setImage(this.createImage(question.getImage(), questionRepository.findMaxId()));
                        } else {
                            newQuestion.setImage(null);
                        }
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
            answerDetailed.deleteByQuestionId(idToDelete);
            answerRepository.deleteByQuestionId(idToDelete);
            questionRepository.deleteById(idToDelete);
        });

        return true;
    }

    public String createImage(String base64Image, Long questionId) {
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                // Sprawdzenie typu i rozszerzenia pliku na podstawie Base64
                String[] parts = base64Image.split(",");
                String extension = "jpg"; // Domyślne rozszerzenie
                if (parts.length > 0) {
                    String header = parts[0];
                    if (header.contains("jpeg")) {
                        extension = "jpeg";
                    } else if (header.contains("png")) {
                        extension = "png";
                    } else if (header.contains("svg")) {
                        extension = "svg";
                    }
                }

                // Dekodowanie Base64 do postaci binarnej
                byte[] imageBytes = Base64.getDecoder().decode(parts[1].getBytes());

                // Generowanie unikalnej nazwy pliku
                String fileName = "question" + questionId.toString() + "-" + UUID.randomUUID() + "." + extension;

                // Zapis obrazu do pliku
                Path uploadDir = Paths.get("data/questions");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(fileName);

                // Usunięcie istniejącego pliku, jeśli taki istnieje
                deleteExistingImageFile(questionId);

                // Zapis nowego pliku
                Files.write(filePath, imageBytes);

                return filePath.toString();
            } catch (IOException e) {
                throw new RuntimeException("Error saving image", e);
            }
        }
        return "blad";
    }

    // Metoda do usuwania istniejącego pliku dla danego pytania
    private void deleteExistingImageFile(Long questionId) {
        // Twój kod do usunięcia istniejącego pliku
        try {
            Path uploadDir = Paths.get("data/questions");
            String prefix = "question" + questionId.toString();

            // Pobranie listy plików pasujących do prefixu
            List<Path> existingFiles = Files.list(uploadDir)
                    .filter(path -> path.getFileName().toString().startsWith(prefix))
                    .collect(Collectors.toList());

            // Usunięcie znalezionych plików
            for (Path file : existingFiles) {
                Files.deleteIfExists(file);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting existing image", e);
        }
    }

    public static boolean isBase64(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("^data:image/[a-zA-Z]+;base64,([\\w+/]*[=]*)*$");

        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
