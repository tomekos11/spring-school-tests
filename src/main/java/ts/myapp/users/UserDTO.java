package ts.myapp.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.tests.TestDTO;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String indeks;
    private List<TestDTO> tests;

    public static UserDTO from(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setIndeks(user.getIndeks());
        dto.setTests(TestDTO.from(user.getTests().stream().map(el -> el.getTest()).toList()));
        return dto;
    }

    // optional: method to convert List<User> to List<UserDTO>
    public static List<UserDTO> from(List<User> users) {
        return users.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }
}