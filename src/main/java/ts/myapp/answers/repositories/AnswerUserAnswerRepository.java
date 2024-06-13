package ts.myapp.answers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.myapp.answers.AnswerUserAnswer;
import ts.myapp.answers.UserAnswer;

@Repository
public interface AnswerUserAnswerRepository extends JpaRepository<AnswerUserAnswer, Long> {
}