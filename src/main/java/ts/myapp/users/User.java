package ts.myapp.users;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.Group;
import ts.myapp.tests.Test;

import java.util.List;
import java.util.stream.Collectors;


@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    @JsonManagedReference("user-userGroup")
    private List<UserGroup> groups;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserTest> tests;

    @JsonIgnore
    public List<Group> getGroupsAfterPivot () {
        return this.getGroups().stream().map(el -> el.getGroup()).toList();
    }

    @JsonGetter("AllUsersTests")
    public List<Test> getAllUsersTests() {
        return this.getTests().stream().map(UserTest::getTest).collect(Collectors.toList());
    }

    @JsonGetter("AllUsersTestsIds")
    public List<Long> getAllUsersTestsIds() {
        return this.getTests().stream().map(UserTest::getTest).toList().stream().map(Test::getId).collect(Collectors.toList());
    }



}
