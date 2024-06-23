package ts.myapp.answers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.Group;
import ts.myapp.users.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="answers_user_answers")
public class AnswerUserAnswer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "user_answer_id")
    private UserAnswer userAnswer;

}