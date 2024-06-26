package ts.myapp.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ts.myapp.groups.GroupDTO;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTestDTO {
    private Long id;
    private UserDTO user;
    private GroupDTO group;

    // optional: method to convert Test entity to TestDTO
    public static UserTestDTO from(UserTest userTest) {
        UserTestDTO dto = new UserTestDTO();
        dto.setId(userTest.getId());
        dto.setUser(UserDTO.from(userTest.getUser()));
        dto.setGroup(GroupDTO.from(userTest.getGroupTest().getGroup()));

        return dto;
    }

    // optional: method to convert List<Test> to List<TestDTO>
    public static List<UserTestDTO> from(List<UserTest> userTests) {
        return userTests.stream()
                .map(UserTestDTO::from)
                .collect(Collectors.toList());
    }
}