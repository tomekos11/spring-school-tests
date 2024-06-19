package ts.myapp.answers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.myapp.answers.AnswerUserAnswer;
import ts.myapp.answers.UserAnswer;

@Repository
public interface AnswerUserAnswerRepository extends JpaRepository<AnswerUserAnswer, Long> {

    @Modifying
    @Query("DELETE FROM AnswerUserAnswer aua WHERE aua.answer.id = :answerId")
    void deleteByAnswerId(@Param("answerId") Long answerId);

    @Modifying
    @Query("DELETE FROM AnswerUserAnswer aua WHERE aua.answer.question.id = :id")
    void deleteByQuestionId(@Param("id") Long id);
}