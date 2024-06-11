package ts.myapp.groups;

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
@Table(name="groups_tests")
public class GroupTest {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private int id;

        @JsonIgnore
        @ManyToOne
        private Test test;

        @JsonIgnore
        @ManyToOne
        private Group group;

        private LocalDateTime begin_date;

        private LocalDateTime end_date;

    //    hidden-relations


}
