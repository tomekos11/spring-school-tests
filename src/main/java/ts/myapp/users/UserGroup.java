package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.Group;

import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user", "group"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_groups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
