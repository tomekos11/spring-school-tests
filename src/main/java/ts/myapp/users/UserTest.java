package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.tests.Test;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_tests")
public class UserTest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Test test;

    @JsonIgnore
    @ManyToOne
    private User user;

    @Column(name="begin_date")
    private LocalDateTime beginDate;

    @Column(name="point_amount")
    private Integer pointAmount;


}
