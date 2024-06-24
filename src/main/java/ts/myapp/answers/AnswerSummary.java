package ts.myapp.answers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.questions.Question;
import ts.myapp.tests.Test;
import ts.myapp.users.UserTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers_summary")
public class AnswerSummary {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_test_id")
    private UserTest userTest;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;


    @Column(name="is_correct")
    private Boolean isCorrect;

    //    hidden-relations
//    @JsonIgnore
    @OneToMany(mappedBy = "answerSummary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerDetailed> detailedAnswers;

    @JsonGetter("allAnswerIds")
    public List<Long> getAllAnswerIds() {
        List<AnswerDetailed> detailedAnswers = this.getDetailedAnswers();
        if (detailedAnswers == null) {
            return new ArrayList<>();
        } else {
            return detailedAnswers.stream().map(a -> a.getAnswer().getId()).collect(Collectors.toList());
        }
    }
}
