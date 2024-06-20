package ts.myapp.groups;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="groups_tests")
public class GroupTest {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Test test;

        @JsonIgnore
        @ManyToOne
        private Group group;

        @Column(name="begin_date")
        private LocalDateTime beginDate;

        @Column(name="end_date")
        private LocalDateTime endDate;

    //    hidden-relations


}
