package ts.myapp.answers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.answers.Answer;
import ts.myapp.groups.GroupTest;
import ts.myapp.questions.Question;
import ts.myapp.tests.Test;
import ts.myapp.users.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_answers")
public class UserAnswer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name="is_correct")
    private boolean isCorrect;

}
