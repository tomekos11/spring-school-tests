package ts.myapp.groups;

import lombok.Data;
import ts.myapp.tests.TestDTO;
import ts.myapp.users.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private List<UserDTO> users;
//    private List<TestDTO> tests;
    private List<GroupTestDTO> groupTests;
    private String type;
    private Integer year;
    private String season;
    // getters and setters
    // constructors

    // optional: method to convert Group entity to GroupDTO
    public static GroupDTO from(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setType(group.getType());
        dto.setYear(group.getYear());
        dto.setSeason(group.getSeason());
        // optionally map users as well
        dto.setGroupTests(GroupTestDTO.from(group.getTests()));
        dto.setUsers(UserDTO.from(group.getUsers().stream().map(el -> el.getUser()).toList()));
        return dto;
    }

    // optional: method to convert List<Group> to List<GroupDTO>
    public static List<GroupDTO> from(List<Group> groups) {
        return groups.stream()
                .map(GroupDTO::from)
                .collect(Collectors.toList());
    }
}
