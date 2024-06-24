package ts.myapp.answers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerSummary extends JpaRepository<ts.myapp.answers.AnswerSummary, Long> {
}