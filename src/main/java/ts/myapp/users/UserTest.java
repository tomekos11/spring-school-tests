package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(name="begin_date")
    private LocalDateTime beginDate;

    @Column(name="point_amount")
    private Integer pointAmount;


//    @JsonGetter("UserTestResult")
//    public int getUserTestResult() {
//        this.getTest().getQuestions().stream().forEach(question ->
//                question.getUserAnswers());
//        return .stream().map(UserTest::getTest).toList().stream().map(Test::getId).collect(Collectors.toList());
//    }
}
