package ts.myapp.answers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerDetailed extends JpaRepository<ts.myapp.answers.AnswerDetailed, Long> {

    @Modifying
    @Query("DELETE FROM AnswerDetailed ad WHERE ad.answer.id = :answerId")
    void deleteByAnswerId(@Param("answerId") Long answerId);

    @Modifying
    @Query("DELETE FROM AnswerDetailed ad WHERE ad.answer.question.id = :id")
    void deleteByQuestionId(@Param("id") Long id);
}