package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.answers.AnswerSummary;
import ts.myapp.groups.GroupTest;
import ts.myapp.tests.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_tests")
public class UserTest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_test_id")
    private GroupTest groupTest;

    @Column(name="begin_date")
    private LocalDateTime beginDate;

    @JsonIgnore
    @OneToMany(mappedBy = "userTest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AnswerSummary> answerSummaries;



    @JsonGetter("correctAnswerAmount")
    public int getCorrectAnswerAmount() {
        List<AnswerSummary> summaries = this.getAnswerSummaries();
        if (summaries == null) {
            return 0;
        }
        return (int) summaries.stream()
                .filter(answer -> answer.getIsCorrect() != null && answer.getIsCorrect())
                .count();
    }

    @JsonGetter("testPointsAmount")
    public int getTestPointsAmount() {
        List<AnswerSummary> summaries = this.getAnswerSummaries();
        if (summaries == null) {
            return 0;
        }
        return summaries.stream()
                .filter(answer -> answer != null && answer.getQuestion() != null && Boolean.TRUE.equals(answer.getIsCorrect()))
                .mapToInt(answer -> answer.getQuestion().getPointAmount())
                .sum();
    }

    @JsonGetter("allTestPointsAmount")
    public int getAllTestPointsAmount() {
        List<AnswerSummary> summaries = this.getAnswerSummaries();
        if (summaries == null) {
            return 0;
        }
        return summaries.stream().mapToInt(answer -> answer.getQuestion().getPointAmount()).sum();
    }

}
