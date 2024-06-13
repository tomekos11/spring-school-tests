package ts.myapp.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.answers.Answer;
import ts.myapp.answers.UserAnswer;
import ts.myapp.groups.GroupTest;
import ts.myapp.tests.Test;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="questions")
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String question;

    @Column(name="point_amount")
    private Integer pointAmount;

    private String image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

//    hidden-relations

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers;

    @JsonIgnore
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers;

}
