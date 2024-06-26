package ts.myapp.groups;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ts.myapp.tests.Test;
import ts.myapp.users.User;
import ts.myapp.users.UserGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="groups")
public class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;

    private String type;

    private Integer year;

    private String season;

//    hidden-relations

    @JsonManagedReference("group-userGroup")
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserGroup> users = new ArrayList<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupTest> tests = new ArrayList<>();

    @JsonGetter("allUsersFromThisGroup")
    public List<User> getAllUsersFromThisGroup() {
        return this.getUsers().stream().map(UserGroup::getUser).collect(Collectors.toList());
    }

    @JsonGetter("allTestsFromThisGroup")
    public List<Test> getAllTestsFromThisGroup() {
        return this.getTests().stream().map(GroupTest::getTest).collect(Collectors.toList());
    }

    @JsonGetter("allNoResitTestsFromThisGroup")
    public List<Test> getAllNoResitTestsFromThisGroup() {
        return this.getTests().stream()
                .filter(test -> !test.getResit())
                .map(resitTest -> resitTest.getTest())
                .collect(Collectors.toList());
    }

    @JsonGetter("allResitTestsFromThisGroup")
    public List<Test> getAllResitTestsFromThisGroup() {
        return this.getTests().stream()
                .filter(test -> test.getResit())
                .map(resitTest -> resitTest.getTest())
                .collect(Collectors.toList());
    }
}
