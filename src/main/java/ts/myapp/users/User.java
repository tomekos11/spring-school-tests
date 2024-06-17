package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.answers.UserAnswer;
import ts.myapp.groups.Group;

import java.util.List;
import java.util.SequencedCollection;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    private String username;

    @Column(name="password")
    @JsonIgnore
    private String password;

    private String role;

    private String name;

    private String surname;

    @Column(name="indeks")
    private String indeks;

//    hidden-relations

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserGroup> groups;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> answers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserTest> tests;

    @JsonIgnore
    public List<Group> getGroupNames () {
        return this.getGroups().stream().map(el -> el.getGroup()).toList();
    }
}
