package ts.myapp.answers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.GroupTest;
import ts.myapp.questions.Question;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers")
public class Answer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name="question_id")
    @ManyToOne
    private Question question;


    private String answer;

    @Column(name="is_correct")
    private Boolean isCorrect;

    //    hidden-relations
    @JsonIgnore
    @OneToMany(mappedBy = "answer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AnswerUserAnswer> userAnswers = new ArrayList<>();


}
