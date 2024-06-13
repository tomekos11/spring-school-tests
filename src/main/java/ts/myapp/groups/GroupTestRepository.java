package ts.myapp.groups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTestRepository extends JpaRepository<GroupTest, Long> {
    @Query("SELECT t FROM GroupTest t WHERE t.group.id = :groupId AND t.test.id = :testId")
    GroupTest findTest(@Param("groupId") Long groupId, @Param("testId") Long testId);
}