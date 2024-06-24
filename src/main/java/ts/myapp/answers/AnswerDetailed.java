package ts.myapp.answers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="detailed_answers")
public class AnswerDetailed {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "answer_summary_id")
    private AnswerSummary answerSummary;

}