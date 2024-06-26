package ts.myapp.questions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.test.id = :testId")
    List<Question> findQuestionsByTestId(@Param("testId") Long testId);

    @Query("SELECT MAX(q.id) FROM Question q")
    Long findMaxId();

    @Modifying
    @Query("DELETE FROM Question q WHERE q.id = :id")
    void deleteById(@Param("id") Long id);
}