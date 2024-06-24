package ts.myapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ts.myapp.groups.GroupTest;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    @Query("SELECT t FROM UserTest t WHERE t.id = :id")
    UserTest findTestById(@Param("id") Long id);

    UserTest findByTestIdAndUserId(@Param("id") Long testId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserTest ut WHERE ut.user.id = :userId AND ut.test.id = :testId")
    void  deleteUserTestByUserIdAndTestId(@Param("userId") Long userId, @Param("testId") Long testId);
}