package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.answers.UserAnswer;

import java.util.List;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

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

}
