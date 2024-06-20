package ts.myapp.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ts.myapp.groups.Group;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users_groups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @JsonBackReference("user-userGroup")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user = null;


    @JsonBackReference("group-userGroup")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group = null;


}
