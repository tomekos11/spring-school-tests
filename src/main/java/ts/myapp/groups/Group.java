package ts.myapp.groups;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.users.UserGroup;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="groups")
public class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    private String name;

    @JsonIgnore
    private String type;

    private int year;

    private String season;

//    hidden-relations

    @JsonIgnore
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserGroup> users;

    @JsonIgnore
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GroupTest> tests;

}
