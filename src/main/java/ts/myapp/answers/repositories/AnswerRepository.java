package ts.myapp.answers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ts.myapp.answers.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}