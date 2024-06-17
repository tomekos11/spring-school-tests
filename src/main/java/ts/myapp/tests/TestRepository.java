package ts.myapp.tests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ts.myapp.users.User;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT t FROM Test t WHERE t.name = :name")
    User findTestByName(@Param("name") String name);
}