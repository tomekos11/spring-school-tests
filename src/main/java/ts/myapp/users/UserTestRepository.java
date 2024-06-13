package ts.myapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.myapp.groups.GroupTest;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    @Query("SELECT t FROM UserTest t WHERE t.id = :id")
    UserTest findTestById(@Param("id") Long id);

    UserTest findTestByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}