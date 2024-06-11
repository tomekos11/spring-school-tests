package ts.myapp.tests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.GroupTest;
import ts.myapp.questions.Question;
import ts.myapp.users.UserGroup;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tests")
public class Test {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;

    private String alias;

//    hidden-relations

    @JsonIgnore
    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupTest> tests;

    @JsonIgnore
    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions;

}
