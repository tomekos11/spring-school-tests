package ts.myapp.answers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ts.myapp.answers.Answer;
import ts.myapp.users.User;

import java.util.List;

@Transactional
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a JOIN a.question q JOIN q.test t WHERE t.id = :testId")
    List<Answer> findAllAnswersByTestId(@Param("testId") Long testId);

    @Modifying
    @Query("DELETE FROM Answer a WHERE a.id = :id")
    void deleteAnswerById(@Param("id") Long id);

    @Query("SELECT MAX(a.id) FROM Answer a")
    Long findMaxId();
}